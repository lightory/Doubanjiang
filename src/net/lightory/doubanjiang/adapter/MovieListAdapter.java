package net.lightory.doubanjiang.adapter;

import java.util.ArrayList;

import net.lightory.doubanjiang.R;
import net.lightory.doubanjiang.api.ApiManager;
import net.lightory.doubanjiang.api.MovieSearchApi;
import net.lightory.doubanjiang.data.Movie;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MovieListAdapter extends AbsListAdapter<Movie> {
    
    public MovieListAdapter(Context context, ArrayList<Movie> objects, int resource) {
        super(context, objects, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, null);
        }
        
        Movie movie = this.getItem(position);
        
        TextView titleTextView = (TextView) convertView.findViewById(R.id.cell_title);
        titleTextView.setText(movie.getTitle());
        
        ImageView imageView = (ImageView) convertView.findViewById(R.id.cell_image);
        imageView.setImageBitmap(null);
        this.imageLoader.displayImage(movie.getImages().get("small"), imageView);
       
        return convertView;
    }
    
    @SuppressLint("HandlerLeak")
    @Override
    public void loadData(String q) {
        final Toast toast = Toast.makeText(this.context, "开始搜索", Toast.LENGTH_LONG);
        toast.show();
        
        isLoading = true;
        MovieSearchApi api = new MovieSearchApi();
        api.setQ(q);
        api.setOffset(0);
        api.setLimit(20);
        api.setHandler(new Handler() {
            public void handleMessage(Message msg) {
                toast.setText("搜索完毕");
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                   
                Movie[] movies = (Movie[]) msg.obj;
                ArrayList<Movie> arrayList = new ArrayList<Movie>();
                for (Movie movie : movies) {
                    arrayList.add(movie);
                }
                setObjects(arrayList);
                
                notifyDataSetChanged();
                isLoading = false;
                isEnd = false;
            }
        });
        ApiManager.getInstance().execute(api);
    }
    
    @SuppressLint("HandlerLeak")
    @Override
    public void loadMoreData(String q) {
        if (isLoading || isEnd) return;
        
        isLoading = true;
        MovieSearchApi api = new MovieSearchApi();
        api.setQ(q);
        api.setOffset(getCount());
        api.setLimit(20);
        api.setHandler(new Handler() {
            public void handleMessage(Message msg) {
                Movie[] movies = (Movie[]) msg.obj;
                for (Movie movie : movies) {
                    getObjects().add(movie);
                }
                
                notifyDataSetChanged();
                isLoading = false;
                if (movies.length == 0) isEnd = true;
            }
        });
        ApiManager.getInstance().execute(api);
    }

}

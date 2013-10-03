package net.lightory.doubanjiang.adapter;

import java.util.ArrayList;

import net.lightory.doubanjiang.R;
import net.lightory.doubanjiang.data.Movie;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

}

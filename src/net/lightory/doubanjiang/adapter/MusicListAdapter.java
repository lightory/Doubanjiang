package net.lightory.doubanjiang.adapter;

import java.util.ArrayList;

import net.lightory.doubanjiang.R;
import net.lightory.doubanjiang.api.ApiManager;
import net.lightory.doubanjiang.api.MusicSearchApi;
import net.lightory.doubanjiang.data.Music;
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

public class MusicListAdapter extends AbsListAdapter<Music> {
    
    public MusicListAdapter(Context context, ArrayList<Music> objects, int resource) {
        super(context, objects, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, null);
        }
        
        Music music = this.getItem(position);
        
        TextView titleTextView = (TextView) convertView.findViewById(R.id.cell_title);
        titleTextView.setText(music.getTitle());
        
        ImageView imageView = (ImageView) convertView.findViewById(R.id.cell_image);
        imageView.setImageBitmap(null);
        this.imageLoader.displayImage(music.getImage(), imageView);
       
        return convertView;
    }
    
    @SuppressLint("HandlerLeak")
    @Override
    public void loadData(String q) {
        final Toast toast = Toast.makeText(this.context, "开始搜索", Toast.LENGTH_LONG);
        toast.show();
        
        isLoading = true;
        MusicSearchApi api = new MusicSearchApi();
        api.setQ(q);
        api.setOffset(0);
        api.setLimit(20);
        api.setHandler(new Handler() {
            public void handleMessage(Message msg) {
                toast.setText("搜索完毕");
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                   
                Music[] musics = (Music[]) msg.obj;
                ArrayList<Music> arrayList = new ArrayList<Music>();
                for (Music music : musics) {
                    arrayList.add(music);
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
        MusicSearchApi api = new MusicSearchApi();
        api.setQ(q);
        api.setOffset(getCount());
        api.setLimit(20);
        api.setHandler(new Handler() {
            public void handleMessage(Message msg) {
                Music[] musics = (Music[]) msg.obj;
                for (Music music : musics) {
                    getObjects().add(music);
                }
                
                notifyDataSetChanged();
                isLoading = false;
                if (musics.length == 0) isEnd = true;
            }
        });
        ApiManager.getInstance().execute(api);
    }

}

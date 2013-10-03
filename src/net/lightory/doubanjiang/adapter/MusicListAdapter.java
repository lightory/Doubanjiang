package net.lightory.doubanjiang.adapter;

import java.util.ArrayList;

import net.lightory.doubanjiang.R;
import net.lightory.doubanjiang.data.Music;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

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

}

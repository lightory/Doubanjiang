package net.lightory.doubanjiang.adapter;

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

public class MusicListAdapter extends BaseAdapter {
    
    private Context context;
    private Music[] musics;
    private int resource;
    
    private ImageLoader imageLoader;
    
    public MusicListAdapter(Context context, Music[] musics, int resource) {
        this.context = context;
        this.musics = musics;
        this.resource = resource;
        
        this.imageLoader = ImageLoader.getInstance();
        final DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().build();
        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context.getApplicationContext())
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(2).threadPriority(Thread.MIN_PRIORITY).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.FIFO).defaultDisplayImageOptions(defaultOptions).build();
        this.imageLoader.init(config);
    }

    @Override
    public int getCount() {
        return this.musics.length;
    }

    @Override
    public Music getItem(int position) {
        return this.musics[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, null);
        }
        
        Music music = this.musics[position];
        
        TextView titleTextView = (TextView) convertView.findViewById(R.id.cell_title);
        titleTextView.setText(music.getTitle());
        
        ImageView imageView = (ImageView) convertView.findViewById(R.id.cell_image);
        imageView.setImageBitmap(null);
        this.imageLoader.displayImage(music.getImage(), imageView);
       
        return convertView;
    }

    public void setMusics(Music[] musics) {
        this.musics = musics;
    }
}

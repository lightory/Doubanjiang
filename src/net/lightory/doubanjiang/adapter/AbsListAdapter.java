package net.lightory.doubanjiang.adapter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class AbsListAdapter<T> extends BaseAdapter {
    
    protected Context context;
    protected ArrayList<T> objects;
    protected int resource;
    
    protected ImageLoader imageLoader;
    
    public AbsListAdapter(Context context, ArrayList<T> objects, int resource) {
        this.context = context;
        this.objects = objects;
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
        return this.objects.size();
    }

    @Override
    public T getItem(int position) {
        return this.objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    public ArrayList<T> getObjects() {
        return this.objects;
    }

    public void setObjects(ArrayList<T> objects) {
        this.objects = objects;
    }
    
}

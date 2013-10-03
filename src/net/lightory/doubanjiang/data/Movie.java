package net.lightory.doubanjiang.data;

import java.util.LinkedHashMap;

import android.content.Intent;
import android.net.Uri;

public class Movie implements IntentViewable {
    
    private String alt;
    private String title;
    private LinkedHashMap<String, String> images;
    
    public String getAlt() {
        return alt;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setAlt(String alt) {
        this.alt = alt;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public LinkedHashMap<String, String> getImages() {
        return images;
    }
    
    public void setImages(LinkedHashMap<String, String> images) {
        this.images = images;
    }
    
    @Override
    public Intent getViewIntent() {
        Uri uri = Uri.parse(this.getAlt());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        return intent;
    }
    
}

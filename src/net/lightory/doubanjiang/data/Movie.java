package net.lightory.doubanjiang.data;

import java.util.LinkedHashMap;

import android.content.Intent;
import android.net.Uri;

public class Movie implements IntentViewable {
    
    private String alt;
    private String id;
    private String title;
    private String year;
    private LinkedHashMap<String, String> images;
    
    public String getAlt() {
        return alt;
    }
    
    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getYear() {
        return year;
    }
    
    public void setAlt(String alt) {
        this.alt = alt;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setYear(String year) {
        this.year = year;
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

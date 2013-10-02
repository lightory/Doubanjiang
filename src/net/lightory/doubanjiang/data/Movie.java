package net.lightory.doubanjiang.data;

import java.util.HashMap;

public class Movie {
    private String alt;
    private String id;
    private String title;
    private String year;
    private HashMap<String, String> images;
    
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
    public HashMap<String, String> getImages() {
        return images;
    }
    public void setImages(HashMap<String, String> images) {
        this.images = images;
    }
}

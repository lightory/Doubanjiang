package net.lightory.doubanjiang.data;

import java.util.ArrayList;

final public class Book {
    private ArrayList<String> author;
    private String id;
    private String image;
    private String price;
    private String pubdate;
    private String publisher;
    private String summary;
    private String title;
    private String url;

    public ArrayList<String> getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getPubdate() {
        return pubdate;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setAuthor(ArrayList<String> author) {
        this.author = author;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}

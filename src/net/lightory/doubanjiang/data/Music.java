package net.lightory.doubanjiang.data;

import android.content.Intent;
import android.net.Uri;

public class Music implements IntentViewable {
    private String alt;
    private String image;
    private String title;
    
    public String getAlt() {
        return alt;
    }
    
    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Intent getViewIntent() {
        Uri uri = Uri.parse(this.getAlt());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        return intent;
    }
    
}

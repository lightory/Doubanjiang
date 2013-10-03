package net.lightory.doubanjiang.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.lightory.doubanjiang.data.Music;

public class MusicSearchApi extends AbsSearchApi {

    @Override
    protected String getUrl() {
        try {
            return AbsApi.BASE_API_URL + "music/search/?q=" + URLEncoder.encode(this.q, "UTF-8") + "&start=" + this.offset + "&count=" + this.limit;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return AbsApi.BASE_API_URL + "music/search/?q=" + this.q + "&start=" + this.offset + "&count=" + this.limit;
        }
    }

    @Override
    protected Class<?> getMappedClass() {
       return Music[].class;
    }

    @Override
    protected String getMappedTopNodeName() {
        return "musics";
    }
    
}

package net.lightory.doubanjiang.api;

import net.lightory.doubanjiang.data.Music;

public class MusicSearchApi extends BaseApi {
    
    private String q;
    
    public MusicSearchApi(String q) {
        this.q = q;
    }

    @Override
    protected String getUrl() {
        return BaseApi.BASE_API_URL + "music/search/?q=" + this.q;
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

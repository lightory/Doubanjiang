package net.lightory.doubanjiang.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.lightory.doubanjiang.data.Movie;

public class MovieSearchApi extends BaseApi {
    private String q;
    
    public MovieSearchApi(String q) {
        this.q = q;
    }

    @Override
    protected String getUrl() {
        try {
            return BaseApi.BASE_API_URL + "movie/search/?q=" + URLEncoder.encode(this.q, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return BaseApi.BASE_API_URL + "movie/search/?q=" + this.q;
        }
    }

    @Override
    protected Class<?> getMappedClass() {
        return Movie[].class;
    }

    @Override
    protected String getMappedTopNodeName() {
        return "subjects";
    }

}

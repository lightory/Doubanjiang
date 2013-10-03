package net.lightory.doubanjiang.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.lightory.doubanjiang.data.Movie;

public class MovieSearchApi extends AbsSearchApi {

    @Override
    protected String getUrl() {
        try {
            return AbsApi.BASE_API_URL + "movie/search/?q=" + URLEncoder.encode(this.q, "UTF-8") + "&start=" + this.offset + "&count=" + this.limit;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return AbsApi.BASE_API_URL + "movie/search/?q=" + this.q + "&start=" + this.offset + "&count=" + this.limit;
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

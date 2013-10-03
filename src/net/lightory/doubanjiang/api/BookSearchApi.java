package net.lightory.doubanjiang.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.lightory.doubanjiang.data.Book;

public class BookSearchApi extends AbsSearchApi {
    
    @Override
    protected String getUrl() {
        try {
            return AbsApi.BASE_API_URL + "book/search/?q=" + URLEncoder.encode(this.q, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return AbsApi.BASE_API_URL + "book/search/?q=" + this.q;
        }
    }

    @Override
    protected Class<Book[]> getMappedClass() {
        return Book[].class;
    }
    
    @Override
    protected String getMappedTopNodeName() {
        return "books";
    }
       
}

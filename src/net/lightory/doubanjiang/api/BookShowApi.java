package net.lightory.doubanjiang.api;

import net.lightory.doubanjiang.data.Book;

public class BookShowApi extends BaseApi {
    private String bookId;
    
    public BookShowApi(String bookId) {
        this.bookId = bookId;
    }
    
    @Override
    protected String getUrl() {
        return BaseApi.BASE_API_URL + "book/" + this.bookId;
    }
    
    @Override 
    protected Class<Book> getMappedClass() {
        return Book.class;
    }
    
    @Override
    protected String getMappedTopNodeName() {
        return null;
    }
}

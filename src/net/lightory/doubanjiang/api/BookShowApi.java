package net.lightory.doubanjiang.api;

public class BookShowApi extends BaseApi {
    private String bookId;
    
    public BookShowApi(String bookId) {
        this.bookId = bookId;
    }
    
    @Override
    public String getUrl() {
        return BaseApi.BASE_API_URL + "book/" + this.bookId;
    }
}

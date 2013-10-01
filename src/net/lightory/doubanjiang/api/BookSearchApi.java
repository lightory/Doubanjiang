package net.lightory.doubanjiang.api;

public class BookSearchApi extends BaseApi {
    private String q;
    
    public BookSearchApi(String q) {
        this.q = q;
    }

    @Override
    String getUrl() {
        return BaseApi.BASE_API_URL + "book/search/?q=" + this.q;
    }

}

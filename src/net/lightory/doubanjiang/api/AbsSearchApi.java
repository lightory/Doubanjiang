package net.lightory.doubanjiang.api;

public abstract class AbsSearchApi extends AbsListApi {

    protected String q;
    
    public AbsSearchApi() {
        
    }
    
    public AbsSearchApi(String q) {
        this.q = q;
    }

    public void setQ(String q) {
        this.q = q;
    }
    
}

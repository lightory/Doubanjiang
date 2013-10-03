package net.lightory.doubanjiang.api;

public abstract class AbsSearchApi extends AbsApi {

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

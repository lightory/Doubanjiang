package net.lightory.doubanjiang.api;

public abstract class AbsListApi extends AbsApi {
    
    protected Integer offset = 0;
    protected Integer limit = 20;
    
    public void setOffset(Integer offset) {
        this.offset = offset;
    }
    
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}

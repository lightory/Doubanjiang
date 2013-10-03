package net.lightory.doubanjiang.api;

import android.os.Handler;
import android.os.Message;

import java.lang.Runnable;

import net.lightory.doubanjiang.util.MappingUtil;
import net.lightory.doubanjiang.util.NetworkUtil;

abstract public class AbsApi implements Runnable {
    final static public String BASE_API_URL = "https://api.douban.com/v2/";
    
    private Handler handler;
    private String response;
    private Object mappedResponse;
    
    abstract protected String getUrl();
    abstract protected Class<?> getMappedClass();
    abstract protected String getMappedTopNodeName();
    
    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        
        try {
            System.out.println("Api Requsest: " + this.getUrl());
            this.response = NetworkUtil.request(this.getUrl());
            
            if (null == this.getMappedTopNodeName()) {
                this.mappedResponse = MappingUtil.parseObjectFromJSONString(this.response, this.getMappedClass());
            } else {
                final int indexOfTopNodeName = this.response.indexOf(this.getMappedTopNodeName());
                final int offset = indexOfTopNodeName + this.getMappedTopNodeName().length() + "\":".length();
                this.mappedResponse = MappingUtil.parseObjectFromJSONString(this.response.substring(offset, this.response.length()-1), this.getMappedClass());
            }
            
            Message message = this.handler.obtainMessage(1, this.mappedResponse);
            message.sendToTarget();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {}
    }
    
    public void setHandler(Handler handler) {
        this.handler = handler;
    }
    
    public String getResponse() {
        return response;
    }
}

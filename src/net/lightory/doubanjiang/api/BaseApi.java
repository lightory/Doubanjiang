package net.lightory.doubanjiang.api;

import android.os.Handler;
import android.os.Message;

import java.lang.Runnable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

abstract public class BaseApi implements Runnable {
    final static public String BASE_API_URL = "https://api.douban.com/v2/";
    
    static private ObjectMapper objectMapper;
    
    private String response;
    private Object mappedResponse;
    private Handler handler;
    
    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        
        try {
            this.response = downloadUrl(this.getUrl());
            
            if (null == this.getMappedTopNodeName()) {
                this.mappedResponse = BaseApi.getObjectMapper().readValue(this.response, this.getMappedClass());
            } else {
                final int indexOfTopNodeName = this.response.indexOf(this.getMappedTopNodeName());
                final int offset = indexOfTopNodeName + this.getMappedTopNodeName().length() + "\":".length();
                this.mappedResponse = BaseApi.getObjectMapper().readValue(this.response.substring(offset, this.response.length()-1), this.getMappedClass());
            }
            
            Message message = this.handler.obtainMessage(1, this.mappedResponse);
            message.sendToTarget();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {}
    }
    
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();

            String contentAsString = toString(is);
            return contentAsString;
        } finally {
            if (is != null) is.close();
        }
    }
    
    public static String toString(final InputStream inputStream) throws IOException {
        byte[] bytes = new byte[1024];
        StringBuilder builder = new StringBuilder();
        int numRead = 0;
        while ((numRead = inputStream.read(bytes)) >= 0) {
            builder.append(new String(bytes, 0, numRead));
        }
        return builder.toString();
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
    
    public String getResponse() {
        return response;
    }
    
    abstract protected String getUrl();
    abstract protected Class<?> getMappedClass();
    abstract protected String getMappedTopNodeName();

    private static ObjectMapper getObjectMapper() {
        if (null == BaseApi.objectMapper) {
            BaseApi.objectMapper = new ObjectMapper();
            BaseApi.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return BaseApi.objectMapper;
    }
}

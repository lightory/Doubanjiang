package net.lightory.doubanjiang.api;

import android.os.Handler;
import android.os.Message;

import java.lang.Runnable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

abstract public class BaseApi implements Runnable {
    final static public String BASE_API_URL = "https://api.douban.com/v2/";
    
    private String response;
    private Handler handler;
    
    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        
        try {
            this.response = downloadUrl(this.getUrl());
            Message message = this.handler.obtainMessage(1, this.response);
            message.sendToTarget();
        } catch (IOException e) {
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

            // Convert the InputStream into a string
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
    
    abstract String getUrl();
}

package net.lightory.doubanjiang.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtil {
    
    static public String request(String urlString) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();

            return IOUtil.toString(is);
        } finally {
            if (is != null) is.close();
        }
    }

    private NetworkUtil() {
        
    }
}

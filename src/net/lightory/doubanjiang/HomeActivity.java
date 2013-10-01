package net.lightory.doubanjiang;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import net.lightory.doubanjiang.api.ApiManager;
import net.lightory.doubanjiang.api.BookSearchApi;

@SuppressLint("HandlerLeak")
public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        BookSearchApi api = new BookSearchApi("代表作与被代表作");
        api.setHandler(new Handler() {
            public void handleMessage(Message msg) {
                String response = (String) msg.obj;
                Log.i("HomeActivity", response);
            }
        });
        ApiManager.getInstance().execute(api);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
}

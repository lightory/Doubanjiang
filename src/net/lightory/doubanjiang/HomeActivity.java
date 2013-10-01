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
import net.lightory.doubanjiang.api.BookShowApi;
import net.lightory.doubanjiang.data.Book;

@SuppressLint("HandlerLeak")
public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        BookShowApi bookShowApi = new BookShowApi("25660221");
        bookShowApi.setHandler(new Handler() {
            public void handleMessage(Message msg) {
                Book book = (Book) msg.obj;
                Log.i("HomeActivity", book.getTitle());
            }
        });
        ApiManager.getInstance().execute(bookShowApi);
    
        BookSearchApi bookSearchApi = new BookSearchApi("1Q84");
        bookSearchApi.setHandler(new Handler() {
            public void handleMessage(Message msg) {
                Book[] books = (Book[]) msg.obj;
                System.out.println(books[0].getTitle());
                System.out.println(books.length);
            }
        });
        ApiManager.getInstance().execute(bookSearchApi);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
}

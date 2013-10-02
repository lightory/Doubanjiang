package net.lightory.doubanjiang;

import net.lightory.doubanjiang.adapter.BookListAdapter;
import net.lightory.doubanjiang.api.ApiManager;
import net.lightory.doubanjiang.api.BaseApi;
import net.lightory.doubanjiang.api.BookSearchApi;
import net.lightory.doubanjiang.api.MovieSearchApi;
import net.lightory.doubanjiang.api.MusicSearchApi;
import net.lightory.doubanjiang.data.Book;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.Service;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class HomeActivity extends Activity {

    private Spinner spinner;
    private EditText editText;
    private ListView listView;
    
    private Book[] books;
    
    private BookListAdapter bookListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                 R.array.search_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.getSpinner().setAdapter(adapter);
        
        new Handler().post(new Runnable(){
            public void run() {
                InputMethodManager imm = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
                //imm.toggleSoftInputFromWindow(getEditText().getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                imm.showSoftInput(getEditText(), InputMethodManager.SHOW_FORCED);
                getEditText().requestFocus();
            }
        });
        
        this.bookListAdapter = new BookListAdapter(this, books, R.layout.book_element);
    }
    
    public void onSearchButtonClick(View view) {
        BaseApi api = null;
        final String searchType = this.getSpinner().getSelectedItem().toString();
        String q = this.getEditText().getText().toString();
        if (searchType.equals("书籍")) {
            api = new BookSearchApi(q);
        } else if (searchType.equals("电影")) {
            api = new MovieSearchApi(q);
        } else {
            api = new MusicSearchApi(q);
        }
        api.setHandler(new Handler() {
            public void handleMessage(Message msg) {
                Object[] objects = (Object[]) msg.obj;
                System.out.println(objects);
                
                if (objects.getClass().equals(Book[].class)) {
                    books = (Book[]) objects;
                    bookListAdapter.setBooks(books);
                    getListView().setAdapter(bookListAdapter);
                }
            }
        });
        ApiManager.getInstance().execute(api);
        
        InputMethodManager imm = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getEditText().getWindowToken(), 0); 
    }
    
    private Spinner getSpinner() {
        if (null == this.spinner) {
            this.spinner = (Spinner) findViewById(R.id.search_type_spinner);
        }
        return this.spinner;
    }
    
    private EditText getEditText() {
        if (null == this.editText) {
            this.editText = (EditText) findViewById(R.id.search_keyword);
        }
        return this.editText;
    }
    
    private ListView getListView() {
        if (null == this.listView) {
            this.listView = (ListView) findViewById(R.id.list_view);
        }
        return this.listView;
    }
}

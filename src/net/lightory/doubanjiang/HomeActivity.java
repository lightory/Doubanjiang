package net.lightory.doubanjiang;

import net.lightory.doubanjiang.api.ApiManager;
import net.lightory.doubanjiang.api.BookSearchApi;
import net.lightory.doubanjiang.data.Book;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class HomeActivity extends Activity {
    
    static private final class HandlerSearch extends Handler {
        public void handleMessage(Message msg) {
            Book[] books = (Book[]) msg.obj;
            System.out.println(books[0].getTitle());
        }
    }

    private Spinner spinner;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                 R.array.search_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.getSpinner().setAdapter(adapter);
    }
    
    public void onSearchButtonClick(View view) {
        BookSearchApi bookSearchApi = new BookSearchApi(this.getEditText().getText().toString());
        bookSearchApi.setHandler(new HandlerSearch());
        ApiManager.getInstance().execute(bookSearchApi);
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
}

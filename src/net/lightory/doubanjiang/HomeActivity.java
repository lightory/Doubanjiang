package net.lightory.doubanjiang;

import net.lightory.doubanjiang.adapter.AbsListAdapter;
import net.lightory.doubanjiang.adapter.BookListAdapter;
import net.lightory.doubanjiang.adapter.MovieListAdapter;
import net.lightory.doubanjiang.adapter.MusicListAdapter;
import net.lightory.doubanjiang.data.IntentViewable;
import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class HomeActivity extends Activity {
    
    final static int TYPE_BOOK  = 1001;
    final static int TYPE_MOVIE = 1002;
    final static int TYPE_MUSIC = 1003;
    
    private Spinner spinner;
    private EditText editText;
    private ListView listView;
    
    private Integer type;
    
    private BookListAdapter bookListAdapter;
    private MovieListAdapter movieListAdapter;
    private MusicListAdapter musicListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        getSpinner();
        getListView();
    }
    
    public void onSearchButtonClick(View view) {
        final String searchType = getSpinner().getSelectedItem().toString();
        if (searchType.equals("书籍")) this.type = TYPE_BOOK;
        else if (searchType.equals("电影")) this.type = TYPE_MOVIE;
        else this.type = TYPE_MUSIC;
        
        getListView().setAdapter(this.getListAdapter());
        this.getListAdapter().loadData(this.getEditText().getText().toString());
        
        InputMethodManager imm = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getEditText().getWindowToken(), 0); 
    }
    
    private Spinner getSpinner() {
        if (null == this.spinner) {
            this.spinner = (Spinner) findViewById(R.id.search_type_spinner);
            
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.search_types, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.spinner.setAdapter(adapter);
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
            this.listView.setOnItemClickListener(new OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                    Object object = adapterView.getAdapter().getItem(position);
                    if (object instanceof IntentViewable) {
                        IntentViewable intentViewable = (IntentViewable) object;
                        startActivity(intentViewable.getViewIntent());
                    }
                }
            });
            this.listView.setOnScrollListener(new OnScrollListener() {
                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if ((firstVisibleItem + visibleItemCount - 1) < 10) return;
                    if ((firstVisibleItem + visibleItemCount - 1) < (totalItemCount - 5)) return;
                    getListAdapter().loadMoreData(getEditText().getText().toString());
                }

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    // TODO Auto-generated method stub
                }
            });
        }
        return this.listView;
    }
    
    private AbsListAdapter<? extends Object> getListAdapter() {
        switch (this.type) {
            case TYPE_BOOK: {
                if (null == this.bookListAdapter) {
                    this.bookListAdapter = new BookListAdapter(this, null, R.layout.cell);
                }
                return this.bookListAdapter;
            }
            case TYPE_MOVIE: {
                if (null == this.movieListAdapter) {
                    this.movieListAdapter = new MovieListAdapter(this, null, R.layout.cell);
                }
                return this.movieListAdapter;
            }
            default: {
                if (null == this.musicListAdapter) {
                    this.musicListAdapter = new MusicListAdapter(this, null, R.layout.cell);
                }
                return this.musicListAdapter;
            }
        }
    }
}

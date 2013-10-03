package net.lightory.doubanjiang;

import net.lightory.doubanjiang.adapter.BookListAdapter;
import net.lightory.doubanjiang.adapter.MovieListAdapter;
import net.lightory.doubanjiang.adapter.MusicListAdapter;
import net.lightory.doubanjiang.api.AbsSearchApi;
import net.lightory.doubanjiang.api.ApiManager;
import net.lightory.doubanjiang.api.BookSearchApi;
import net.lightory.doubanjiang.api.MovieSearchApi;
import net.lightory.doubanjiang.api.MusicSearchApi;
import net.lightory.doubanjiang.data.Book;
import net.lightory.doubanjiang.data.IntentViewable;
import net.lightory.doubanjiang.data.Movie;
import net.lightory.doubanjiang.data.Music;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class HomeActivity extends Activity {
    
    private Spinner spinner;
    private EditText editText;
    private ListView listView;
    
    private AbsSearchApi searchApi;
    private Book[] books;
    private Movie[] movies;
    private Music[] musics;
    
    private BookListAdapter bookListAdapter;
    private MovieListAdapter movieListAdapter;
    private MusicListAdapter musicListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        getSpinner();
        getListView();
        
        this.bookListAdapter = new BookListAdapter(this, books, R.layout.cell);
        this.movieListAdapter = new MovieListAdapter(this, movies, R.layout.cell);
        this.musicListAdapter = new MusicListAdapter(this, musics, R.layout.cell);
    }
    
    @SuppressLint("HandlerLeak")
    public void onSearchButtonClick(View view) {
        final Toast toast = Toast.makeText(getApplicationContext(), "开始搜索", Toast.LENGTH_LONG);
        toast.show();
        
        final String searchType = getSpinner().getSelectedItem().toString();
        if (searchType.equals("书籍")) searchApi = new BookSearchApi();
        else if (searchType.equals("电影")) searchApi = new MovieSearchApi();
        else searchApi = new MusicSearchApi();
        
        searchApi.setQ(this.getEditText().getText().toString());
        searchApi.setHandler(new Handler() {
            public void handleMessage(Message msg) {
                Object[] objects = (Object[]) msg.obj;
                System.out.println(objects);
                
                toast.setText("搜索完毕");
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                
                if (objects.getClass().equals(Book[].class)) {
                    books = (Book[]) objects;
                    bookListAdapter.setBooks(books);
                    getListView().setAdapter(bookListAdapter);
                } else if (objects.getClass().equals(Movie[].class)) {
                    movies = (Movie[]) objects;
                    movieListAdapter.setMovies(movies);
                    getListView().setAdapter(movieListAdapter);
                } else if (objects.getClass().equals(Music[].class)) {
                    musics = (Music[]) objects;
                    musicListAdapter.setMusics(musics);
                    getListView().setAdapter(musicListAdapter);
                }
            }
        });
        ApiManager.getInstance().execute(searchApi);
        
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
        }
        return this.listView;
    }
}

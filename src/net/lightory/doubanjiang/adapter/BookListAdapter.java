package net.lightory.doubanjiang.adapter;

import java.util.ArrayList;

import net.lightory.doubanjiang.R;
import net.lightory.doubanjiang.api.ApiManager;
import net.lightory.doubanjiang.api.BookSearchApi;
import net.lightory.doubanjiang.data.Book;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookListAdapter extends AbsListAdapter<Book> {

    public BookListAdapter(Context context, ArrayList<Book> objects, int resource) {
        super(context, objects, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, null);
        }
        
        Book book = this.getItem(position);
        
        TextView titleTextView = (TextView) convertView.findViewById(R.id.cell_title);
        titleTextView.setText(book.getTitle());
        
        ImageView imageView = (ImageView) convertView.findViewById(R.id.cell_image);
        imageView.setImageBitmap(null);
        this.imageLoader.displayImage(book.getImage(), imageView);
       
        return convertView;
    }
    
    @SuppressLint("HandlerLeak")
    @Override
    public void loadData(String q) {
        final Toast toast = Toast.makeText(this.context, "开始搜索", Toast.LENGTH_LONG);
        toast.show();
        
        isLoading = true;
        BookSearchApi api = new BookSearchApi();
        api.setQ(q);
        api.setOffset(0);
        api.setLimit(20);
        api.setHandler(new Handler() {
            public void handleMessage(Message msg) {
                toast.setText("搜索完毕");
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                   
                Book[] books = (Book[]) msg.obj;
                ArrayList<Book> arrayList = new ArrayList<Book>();
                for (Book book : books) {
                    arrayList.add(book);
                }
                setObjects(arrayList);
                
                notifyDataSetChanged();
                isLoading = false;
                isEnd = false;
            }
        });
        ApiManager.getInstance().execute(api);
    }
    
    @SuppressLint("HandlerLeak")
    @Override
    public void loadMoreData(String q) {
        if (isLoading || isEnd) return;
        
        isLoading = true;
        BookSearchApi api = new BookSearchApi();
        api.setQ(q);
        api.setOffset(getCount());
        api.setLimit(20);
        api.setHandler(new Handler() {
            public void handleMessage(Message msg) {
                Book[] books = (Book[]) msg.obj;
                for (Book book : books) {
                    getObjects().add(book);
                }
                
                notifyDataSetChanged();
                isLoading = false;
                if (books.length == 0) isEnd = true;
            }
        });
        ApiManager.getInstance().execute(api);
    }
}

package net.lightory.doubanjiang.adapter;

import java.util.ArrayList;

import net.lightory.doubanjiang.R;
import net.lightory.doubanjiang.data.Book;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
}

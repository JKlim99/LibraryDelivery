package com.mdp.librarydelivery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class AdminBookList extends ArrayAdapter<BookModel> {


    // constructor for our list view adapter.
    public AdminBookList(@NonNull Context context, ArrayList<BookModel> BookModelArrayList) {
        super(context, 0, BookModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.booklist_items, parent, false);
        }

        BookModel bookModel = getItem(position);
        TextView bookNameView = listitemView.findViewById(R.id.textView);
        ImageView bookImageView = listitemView.findViewById(R.id.imageView);
        bookNameView.setText(bookModel.getBook_name());

        Picasso.get().load(bookModel.getImage()).into(bookImageView);

        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                Context context = v.getContext();
                Intent i = new Intent(context, UserBookDetails.class);
                i.putExtra("id", bookModel.getId());
                context.startActivity(i);
            }

            public void addBook(View view) {
                Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                Context context = view.getContext();
                Intent i = new Intent(context, Manage_books.class);
                context.startActivity(i);
            }

        });
        return listitemView;
    }


}


package com.mdp.librarydelivery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;


public class BookLoansList extends ArrayAdapter<BookLoanModel> {
    public BookLoansList(@NonNull Context context, ArrayList<BookLoanModel> BookLoanModelArrayList) {
        super(context, 0, BookLoanModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_view_users, parent, false);
        }

        BookLoanModel bookLoanModel = getItem(position);
        TextView bookidView = listitemView.findViewById(R.id.textView2);
        bookidView.setText(bookLoanModel.getBook_id());

        TextView useridView = listitemView.findViewById(R.id.textView1);
        useridView.setText(bookLoanModel.getBook_id());



        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                Context context = v.getContext();
                Intent i = new Intent(context, BookLoanDetails.class);
                i.putExtra("loan_id", bookLoanModel.getBook_id() );
                context.startActivity(i);
            }
        });
        return listitemView;
    }




}
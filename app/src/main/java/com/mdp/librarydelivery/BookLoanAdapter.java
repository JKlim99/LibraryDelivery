package com.mdp.librarydelivery;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookLoanAdapter extends ArrayAdapter<BookLoanModel> {
    private View listitemView;
    private static final String TAG = "";

    // constructor for our list view adapter.
    public BookLoanAdapter(@NonNull Context context, ArrayList<BookLoanModel> BookLoanModelArrayList) {
        super(context, 0, BookLoanModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.bookloan_items, parent, false);
        }

        BookLoanModel bookLoanModel = getItem(position);

        TextView loanedDateView = listitemView.findViewById(R.id.loanedDateText);
        TextView returnDateView = listitemView.findViewById(R.id.returnDateText);
        TextView statusView = listitemView.findViewById(R.id.statusText);
        loanedDateView.setText("Loaned at: " + bookLoanModel.getRequest_date());
        returnDateView.setText("Return before: " + bookLoanModel.getReturn_date());
        statusView.setText("Status: " + bookLoanModel.getStatus());
        TextView bookNameView = listitemView.findViewById(R.id.textView);
        ImageView bookImageView = listitemView.findViewById(R.id.imageView);
        bookNameView.setText(bookLoanModel.getBook_name());
        Picasso.get().load(bookLoanModel.getImage()).into(bookImageView);

        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Loading completed", Toast.LENGTH_SHORT).show();
                Context context = v.getContext();
                Intent i = new Intent(context, UserBookLoanDetails.class);
                i.putExtra("id", bookLoanModel.getId());
                context.startActivity(i);
            }
        });

        return listitemView;
    }
}
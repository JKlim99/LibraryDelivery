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
        Log.d(TAG, "test: " + bookLoanModel.getBook_id());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("books").document(bookLoanModel.getBook_id());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        BookModel bookModel = document.toObject(BookModel.class);
                        bookModel.setId(document.getId());
                        TextView bookNameView = listitemView.findViewById(R.id.textView);
                        TextView loanedDateView = listitemView.findViewById(R.id.loanedDateText);
                        TextView returnDateView = listitemView.findViewById(R.id.returnDateText);
                        ImageView bookImageView = listitemView.findViewById(R.id.imageView);
                        bookNameView.setText(bookModel.getBook_name());
                        loanedDateView.setText("Loaned at: " + bookLoanModel.getRequest_date());
                        returnDateView.setText("Return before: " + bookLoanModel.getReturn_date());


                        Picasso.get().load(bookModel.getImage()).into(bookImageView);
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                Context context = v.getContext();
                Intent i = new Intent(context, UserBookLoanDetails.class);
                i.putExtra("id", bookLoanModel.getId());
                context.startActivity(i);
            }
        });
        return listitemView;
    }
}
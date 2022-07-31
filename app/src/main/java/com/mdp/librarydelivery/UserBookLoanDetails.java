package com.mdp.librarydelivery;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class UserBookLoanDetails extends AppCompatActivity {
    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle param = getIntent().getExtras();
        String id = "";
        if(param != null) {
            id = param.getString("id");
        }
        Log.d(TAG, id);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("bookLoan").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        BookLoanModel bookLoanModel = document.toObject(BookLoanModel.class);
                        bookLoanModel.setId(document.getId());
                        DocumentReference docRef = db.collection("books").document(bookLoanModel.getBook_id());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        BookModel bookModel = document.toObject(BookModel.class);
                                        bookModel.setId(document.getId());
                                        TextView bookNameView = findViewById(R.id.bookNameText);
                                        bookNameView.setText(bookModel.getBook_name());
                                        TextView isbnView = findViewById(R.id.isbnText);
                                        isbnView.setText(bookModel.getIsbn());
                                        TextView loanedDateView = findViewById(R.id.loanedDateText);
                                        loanedDateView.setText(bookLoanModel.getRequest_date());
                                        TextView categoryView = findViewById(R.id.categoryText);
                                        categoryView.setText(bookModel.getCategory());
                                        TextView returnDateView = findViewById(R.id.returnDateText);
                                        returnDateView.setText(bookLoanModel.getReturn_date());

                                        ImageView bookImageView = findViewById(R.id.imageView);

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
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_loan_details);
    }
}
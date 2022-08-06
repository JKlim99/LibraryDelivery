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


public class BookLoanDetails extends AppCompatActivity {

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle param = getIntent().getExtras();
        String id = "";
        if(param != null) {
            id = param.getString("id");
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("delivery").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override

            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        BookLoanModel bookLoanModel = document.toObject(BookLoanModel.class);
                        bookLoanModel.setLoan_id(document.getId());
                        TextView bookNameView = findViewById(R.id.loan_idText);
                        bookNameView.setText(bookLoanModel.getLoan_id());
                        TextView isbnView = findViewById(R.id.book_idText);
                        isbnView.setText(bookLoanModel.getBook_id());
                        TextView authorView = findViewById(R.id.request_dateText);
                        authorView.setText(bookLoanModel.getRequest_date());
                        TextView categoryView = findViewById(R.id.return_dateText);
                        categoryView.setText(bookLoanModel.getReturn_date());
                        TextView descriptionView = findViewById(R.id.statusText);
                        descriptionView.setText(bookLoanModel.getStatus());


                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_loan_details);
    }
}
package com.mdp.librarydelivery;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
                                        TextView statusView = findViewById(R.id.statusText);
                                        statusView.setText(bookModel.getStatus());
                                        TextView returnDateView = findViewById(R.id.returnDateText);
                                        returnDateView.setText(bookLoanModel.getReturn_date());
                                        TextView fineAmountView = findViewById(R.id.fineText);
                                        fineAmountView.setText("RM"+bookLoanModel.getFine_amount());
                                        TextView bookidView = findViewById(R.id.bookIdText);
                                        bookidView.setText(bookModel.getId());
                                        TextView idView = findViewById(R.id.idLoanText);
                                        idView.setText(bookLoanModel.getId());
                                        if(bookLoanModel.getStatus().equals("returned")){
                                            Button button = findViewById(R.id.button);
                                            button.setVisibility(View.INVISIBLE);
                                        }

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

    public void returnBook(View view){
        Session session = new Session(this);
        TextView idView = findViewById(R.id.idLoanText);
        TextView idBookView = findViewById(R.id.bookIdText);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> docData = new HashMap<>();
        docData.put("status", "available");
        db.collection("books").document(idBookView.getText().toString())
                .update(docData);

        Map<String, Object> docData2 = new HashMap<>();
        docData2.put("status", "returned");
        db.collection("bookLoan").document(idView.getText().toString())
                .update(docData);

        db.collection("users")
                .document(session.getid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserModel userModel = documentSnapshot.toObject(UserModel.class);
                        Map<String, Object> docData3 = new HashMap<>();
                        docData3.put("deliverer_id", "8qCTDCZ6gVnZzbQxlwVO");
                        docData3.put("deliverer_name", "jason");
                        docData3.put("delivery_address", userModel.getAddress());
                        docData3.put("delivery_date", new Date());
                        docData3.put("email", userModel.getEmail());
                        docData3.put("loan_id", idView.getText().toString());
                        docData3.put("phone_no", userModel.getPhone_number());
                        docData3.put("receiver_id", session.getid());
                        docData3.put("receiver_name", userModel.getUsername());
                        docData3.put("status", "Pending");
                        docData3.put("type", "Return");
                        db.collection("delivery")
                                .add(docData3);
                    }
                });

        finish();
        startActivity(getIntent());

    }
}
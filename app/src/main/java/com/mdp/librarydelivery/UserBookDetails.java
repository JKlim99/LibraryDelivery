package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserBookDetails extends AppCompatActivity {
    private static final String TAG = "";
    String loan_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle param = getIntent().getExtras();
        String id = "";
        if(param != null) {
            id = param.getString("id");
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("books").document(id);
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
                        TextView authorView = findViewById(R.id.loanedDateText);
                        authorView.setText(bookModel.getAuthor());
                        TextView categoryView = findViewById(R.id.categoryText);
                        categoryView.setText(bookModel.getCategory());
                        TextView descriptionView = findViewById(R.id.returnDateText);
                        descriptionView.setText(bookModel.getDescription());
                        TextView idView = findViewById(R.id.idText);
                        idView.setText(bookModel.getId());

                        ImageView bookImageView = findViewById(R.id.imageView);
                        if(bookModel.getStatus().equals("loaned")){
                            Button button = findViewById(R.id.button);
                            button.setVisibility(View.INVISIBLE);
                        }

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_details);
    }

    public void loanBook(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Loan Book Confirmation");
        builder.setMessage("Are you sure you want to loan this book?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Session session = new Session(UserBookDetails.this);
                        TextView idView = findViewById(R.id.idText);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, Object> docData = new HashMap<>();
                        docData.put("status", "loaned");
                        db.collection("books").document(idView.getText().toString())
                                .update(docData);
                        Map<String, Object> docData2 = new HashMap<>();
                        docData2.put("book_id", idView.getText().toString());
                        docData2.put("status", "loaned");
                        docData2.put("fine_amount", "RM0.00");
                        docData2.put("user_id", session.getid());

                        Calendar calender = Calendar.getInstance();

                        docData2.put("request_date", new Date());
                        docData2.put("return_date", addDay(new Date(), 7));


                        db.collection("bookLoan")
                                .add(docData2)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                        loan_id = documentReference.getId();
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
                                                        docData3.put("loan_id", loan_id);
                                                        docData3.put("phone_no", userModel.getPhone_number());
                                                        docData3.put("receiver_id", session.getid());
                                                        docData3.put("receiver_name", userModel.getUsername());
                                                        docData3.put("status", "Pending");
                                                        docData3.put("type", "Loan");
                                                        db.collection("delivery")
                                                                .add(docData3);
                                                    }
                                                });
                                    }
                                });
                        finish();
                        startActivity(getIntent());
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public static Date addDay(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        return cal.getTime();
    }
}
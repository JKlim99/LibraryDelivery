package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminLoanList extends AppCompatActivity {
    private static final String TAG = "";
    ArrayList<BookLoanModel> BookLoanModelArrayList;
    ListView bookLoanListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_loan_list);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("bookLoan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            BookLoanModelArrayList = new ArrayList<>();
                            bookLoanListView = findViewById(R.id.BookLoanListView);
                            int count = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                count++;
                            }

                            int i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BookLoanModel bookLoanModel = document.toObject(BookLoanModel.class);
                                bookLoanModel.setId(document.getId());
                                DocumentReference docRef = db.collection("books").document(bookLoanModel.getBook_id());
                                int finalI = i;
                                int finalCount = count;
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                BookModel bookModel = document.toObject(BookModel.class);
                                                bookModel.setId(document.getId());
                                                bookLoanModel.setBook_name(bookModel.getBook_name());
                                                bookLoanModel.setImage(bookModel.getImage());
                                                BookLoanModelArrayList.add(bookLoanModel);

                                                if(finalI == finalCount -1){
                                                    // after that we are passing our array list to our adapter class.
                                                    AdminBookLoanAdapter adapter = new AdminBookLoanAdapter(AdminLoanList.this, BookLoanModelArrayList);

                                                    // after passing this array list to our adapter
                                                    // class we are setting our adapter to our list view.
                                                    bookLoanListView.setAdapter(adapter);
                                                }
                                            } else {
                                                Log.d(TAG, "No such document");
                                            }
                                        } else {
                                            Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                i++;
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
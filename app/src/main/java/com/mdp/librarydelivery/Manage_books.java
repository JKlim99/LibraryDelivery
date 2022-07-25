package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mdp.librarydelivery.R;

import java.util.HashMap;
import java.util.Map;

public class Manage_books extends AppCompatActivity {

    private static final String TAG = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_books);

    }


    @SuppressWarnings("unused")
    // Sample code on inserting data to Firestore
    public void addBook(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // add book
        Map<String, Object> book = new HashMap<>();
        book.put("author", "Zain");
        book.put("book_name", "We got this brother");
        book.put("publisher", "qusi");

        // Add a new document with a generated ID
        db.collection("books")
                .add(book)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        System.out.println("a book has been added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}

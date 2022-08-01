package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mdp.librarydelivery.R;

import java.util.HashMap;
import java.util.Map;

public class Manage_books extends AppCompatActivity {

    private static final String TAG = "";

    EditText bookName, autherName, publisher, publishingDate, ISBn, description, image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_books);
        EditText nameEditText = (EditText) findViewById(R.id.name);
    }


    @SuppressWarnings("unused")
    // Sample code on inserting data to Firestore
    public void submitbuttonHandler(View view) {
        bookName = (EditText) findViewById(R.id.name);
        autherName = (EditText) findViewById(R.id.auther_name);
        publisher = (EditText) findViewById(R.id.publisher);
        publishingDate = (EditText) findViewById(R.id.publish_date);
        ISBn = (EditText) findViewById(R.id.ISBn);
        description = (EditText) findViewById(R.id.description);
        image = (EditText) findViewById(R.id.image);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // add book
        Map<String, Object> book = new HashMap<>();
        book.put("book_name", bookName);
        book.put("auther_name", autherName);
        book.put("publisher", publisher );
        book.put("publishing_date", publishingDate);
        book.put("ISBn", ISBn);
        book.put("description", description);
        book.put("image", image);

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

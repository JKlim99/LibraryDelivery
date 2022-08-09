package com.mdp.librarydelivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdminBookDetails extends AppCompatActivity {

    private static final String TAG = "";
    private ArrayList<BookModel> BookModelArrayList;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle param = getIntent().getExtras();
        String id = "";
        if (param != null) {
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
                        TextView authorView = findViewById(R.id.authorText);
                        authorView.setText(bookModel.getAuthor());
                        TextView categoryView = findViewById(R.id.categoryText);
                        categoryView.setText(bookModel.getCategory());
                        TextView descriptionView = findViewById(R.id.descriptionText);
                        descriptionView.setText(bookModel.getDescription());

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
        Button deleteBookBtn = findViewById(R.id.button1);

        deleteBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("books").document("id")
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_details);


// suppose to help in update TT
//        Button updateBookBtn = findViewById(R.id.button1);
//
//        updateBookBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // after clicking of the item of recycler view.
//                // we are passing our course object to the new activity.
//                BookModel bookModel = BookModelArrayList.get(getAdapterPosition());
//
//                // below line is creating a new intent.
//                Intent i = new Intent(context, EditBook.class);
//
//                // below line is for putting our course object to our next activity.
//                i.putExtra("book", bookModel);
//
//                // after passing the data we are starting our activity.
//                context.startActivity(i);
//            }
//        });



    }}




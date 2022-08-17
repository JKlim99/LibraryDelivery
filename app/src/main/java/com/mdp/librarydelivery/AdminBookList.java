package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mdp.librarydelivery.databinding.FragmentBooklistBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class AdminBookList extends AppCompatActivity {
    private static final String TAG = "";
    ArrayList<BookModel> BookModelArrayList;
    ListView bookListView;

    private FragmentBooklistBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("books")
                .whereIn("status", Arrays.asList("available", "loaned"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            BookModelArrayList = new ArrayList<>();
                            bookListView = findViewById(R.id.BookListView);

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BookModel bookModel = document.toObject(BookModel.class);
                                bookModel.setId(document.getId());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                BookModelArrayList.add(bookModel);

                            }
                            // after that we are passing our array list to our adapter class.
                            AdminBookListAdapter adapter = new AdminBookListAdapter(AdminBookList.this, BookModelArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            bookListView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_book_list);
        FloatingActionButton fab = findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent page = new Intent(AdminBookList.this, AdminBookCreate.class);
                startActivity(page);
            }
        });
    }
}
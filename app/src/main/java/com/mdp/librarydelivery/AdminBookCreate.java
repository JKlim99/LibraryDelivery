package com.mdp.librarydelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminBookCreate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_book_create);
    }

    public void create(View view){
        EditText bookNameView = findViewById(R.id.bookNameText);
        EditText imageView = findViewById(R.id.imageText);
        EditText isbnView = findViewById(R.id.isbnText);
        EditText authorView = findViewById(R.id.loanedDateText);
        EditText categoryView = findViewById(R.id.categoryText);
        EditText descriptionView = findViewById(R.id.returnDateText);
        if(imageView.getText().toString().equals("")){
            imageView.setText("-");
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> docData = new HashMap<>();
        docData.put("book_name", bookNameView.getText().toString());
        docData.put("image", imageView.getText().toString());
        docData.put("ISBN", isbnView.getText().toString());
        docData.put("author", authorView.getText().toString());
        docData.put("category", categoryView.getText().toString());
        docData.put("description", descriptionView.getText().toString());
        docData.put("status", "available");
        db.collection("books")
                .add(docData);

        Toast.makeText(getApplicationContext(), "Book Created", Toast.LENGTH_SHORT).show();
        finish();
        Intent page = new Intent(AdminBookCreate.this, AdminBookList.class);
        startActivity(page);
    }
}
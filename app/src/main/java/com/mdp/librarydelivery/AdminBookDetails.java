package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AdminBookDetails extends AppCompatActivity {
    private static final String TAG = "";

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
                        EditText bookNameView = findViewById(R.id.bookNameText);
                        bookNameView.setText(bookModel.getBook_name());
                        EditText imageView = findViewById(R.id.imageText);
                        imageView.setText(bookModel.getImage());
                        EditText isbnView = findViewById(R.id.isbnText);
                        isbnView.setText(bookModel.getIsbn());
                        EditText authorView = findViewById(R.id.loanedDateText);
                        authorView.setText(bookModel.getAuthor());
                        EditText categoryView = findViewById(R.id.categoryText);
                        categoryView.setText(bookModel.getCategory());
                        EditText descriptionView = findViewById(R.id.returnDateText);
                        descriptionView.setText(bookModel.getDescription());
                        TextView idView = findViewById(R.id.idText);
                        idView.setText(bookModel.getId());

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_book_details);
    }

    public void update(View view){
        EditText bookNameView = findViewById(R.id.bookNameText);
        EditText imageView = findViewById(R.id.imageText);
        EditText isbnView = findViewById(R.id.isbnText);
        EditText authorView = findViewById(R.id.loanedDateText);
        EditText categoryView = findViewById(R.id.categoryText);
        EditText descriptionView = findViewById(R.id.returnDateText);
        TextView idView = findViewById(R.id.idText);

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
        db.collection("books").document(idView.getText().toString())
                .update(docData);

        Toast.makeText(getApplicationContext(), "Book Updated", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }

    public void delete(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure you want to delete the book?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView idView = findViewById(R.id.idText);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> docData = new HashMap<>();
                docData.put("status", "Deleted");
                db.collection("books").document(idView.getText().toString())
                        .update(docData);
                finish();
                Toast.makeText(getApplicationContext(), "Book Deleted", Toast.LENGTH_SHORT).show();
                Intent page = new Intent(AdminBookDetails.this, AdminBookList.class);
                startActivity(page);
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
}
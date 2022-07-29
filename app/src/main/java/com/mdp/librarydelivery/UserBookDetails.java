package com.mdp.librarydelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class UserBookDetails extends AppCompatActivity {
    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle param = getIntent().getExtras();
        String id = "";
        if(param != null) {
            id = param.getString("id");
        }
        Log.d(TAG, id);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_details);
    }
}
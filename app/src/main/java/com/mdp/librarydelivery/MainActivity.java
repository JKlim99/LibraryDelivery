package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //checkin();
        checkinAdmin();
    }
    public void checkin() {
        Intent intent = new Intent(this, UserBookDetails.class);
        startActivity(intent);}

    public void checkinAdmin() {
        Intent intent = new Intent(this, view_users.class);
        startActivity(intent);}



}
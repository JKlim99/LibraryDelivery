package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
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
package com.mdp.librarydelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public void logout(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Logout Confirmation");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AdminHome.this, "Logout successfully", Toast.LENGTH_SHORT).show();
                Intent login = new Intent(AdminHome.this, Login.class);
                startActivity(login);
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

    public void books(View view){
        Intent page = new Intent(AdminHome.this, AdminBookList.class);
        startActivity(page);
    }

    public void users(View view){
        Intent page = new Intent(AdminHome.this, AdminUserList.class);
        startActivity(page);
    }

    public void loans(View view){
        Intent page = new Intent(AdminHome.this, AdminLoanList.class);
        startActivity(page);
    }

    public void delivery(View view){
        Intent page = new Intent(AdminHome.this, AdminDeliveryList.class);
        startActivity(page);
    }
}
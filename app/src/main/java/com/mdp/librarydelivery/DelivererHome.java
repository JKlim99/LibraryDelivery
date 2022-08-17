package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DelivererHome extends AppCompatActivity {
    private static final String TAG = "";
    ArrayList<DeliveryModel> DeliveryModelArrayList;
    ListView deliveryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Session session = new Session(this);
        db.collection("delivery")
                .whereEqualTo("deliverer_id", session.getid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            DeliveryModelArrayList = new ArrayList<>();
                            deliveryListView = findViewById(R.id.DeliveryListView);

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DeliveryModel deliveryModel = document.toObject(DeliveryModel.class);
                                deliveryModel.setId(document.getId());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                DeliveryModelArrayList.add(deliveryModel);
                            }
                            // after that we are passing our array list to our adapter class.
                            DelivererDeliveryAdapter adapter = new DelivererDeliveryAdapter(DelivererHome.this, DeliveryModelArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            deliveryListView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverer_home);
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
                        Toast.makeText(DelivererHome.this, "Logout successfully", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(DelivererHome.this, Login.class);
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
}
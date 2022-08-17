package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminDeliveryList extends AppCompatActivity {
    private static final String TAG = "";
    ArrayList<DeliveryModel> DeliveryModelArrayList;
    ListView deliveryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delivery_list);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("delivery")
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
                            AdminDeliveryAdapter adapter = new AdminDeliveryAdapter(AdminDeliveryList.this, DeliveryModelArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            deliveryListView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
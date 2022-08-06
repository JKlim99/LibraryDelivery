package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeliveryDetails extends AppCompatActivity {

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle param = getIntent().getExtras();
        String id = "";
        if(param != null) {
            id = param.getString("id");
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("delivery").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override


            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DeliveryModel deliveryModel = document.toObject(DeliveryModel.class);
                        deliveryModel.setId(document.getId());
                        TextView deliverer_nameText = findViewById(R.id.deliverer_nameText);
                        deliverer_nameText.setText(deliveryModel.getDeliverer_name());
                        //
                        TextView delivery_addressText = findViewById(R.id.delivery_addressText);
                        delivery_addressText.setText(deliveryModel.getDelivery_address());
                        //
                        TextView delivery_dateText = findViewById(R.id.delivery_dateText);
                        delivery_dateText.setText(deliveryModel.getDelivery_date());
                        //
                        TextView emailText = findViewById(R.id.emailText);
                        emailText.setText(deliveryModel.getEmail());
                        //
                        TextView loan_idText = findViewById(R.id.loan_idText);
                        loan_idText.setText(deliveryModel.getLoan_id());
                        //
                        TextView receiver_idText = findViewById(R.id.receiver_idText);
                        receiver_idText.setText(deliveryModel.getReceiver_id());
                        //
                        TextView receiver_nameText = findViewById(R.id.receiver_nameText);
                        receiver_nameText.setText(deliveryModel.getReceiver_name());
                        //
                        TextView statusText = findViewById(R.id.statusText);
                        statusText.setText(deliveryModel.getStatus());
                        //
                        TextView phone_noText = findViewById(R.id.phone_noText);
                        phone_noText.setText(deliveryModel.getPhone_no()  );

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    }
                    else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);
    }
}
package com.mdp.librarydelivery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDeliveryDetails extends AppCompatActivity {
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
                        TextView idView = findViewById(R.id.idLabel);
                        idView.setText("Delivery #"+deliveryModel.getId());
                        TextView idText = findViewById(R.id.idText);
                        idText.setText(deliveryModel.getId());
                        TextView receiverNameView = findViewById(R.id.receiverNameText);
                        receiverNameView.setText(deliveryModel.getReceiver_name());
                        TextView phoneView = findViewById(R.id.phoneText);
                        phoneView.setText(deliveryModel.getPhone_no());
                        TextView addressView = findViewById(R.id.addressText);
                        addressView.setText(deliveryModel.getDelivery_address());
                        TextView delivererNameView = findViewById(R.id.delivererText);
                        delivererNameView.setText(deliveryModel.getDeliverer_name());
                        TextView statusView = findViewById(R.id.statusText);
                        statusView.setText(deliveryModel.getStatus());
                        if(deliveryModel.getStatus().equals("Delivered")){
                            Button button = findViewById(R.id.button);
                            button.setVisibility(View.VISIBLE);
                        }

                        DocumentReference docRef = db.collection("bookLoan").document(deliveryModel.getLoan_id());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        BookLoanModel bookLoanModel = document.toObject(BookLoanModel.class);
                                        bookLoanModel.setId(document.getId());
                                        DocumentReference docRef = db.collection("books").document(bookLoanModel.getBook_id());
                                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
                                                        BookModel bookModel = document.toObject(BookModel.class);
                                                        bookModel.setId(document.getId());
                                                        TextView itemIdView = findViewById(R.id.itemIdLabel);
                                                        itemIdView.setText("ID: "+bookModel.getId());
                                                        TextView itemNameView = findViewById(R.id.textView);
                                                        itemNameView.setText("ID: "+bookModel.getBook_name());
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
                                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });

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
        setContentView(R.layout.activity_user_delivery_details);
    }

    public void verifyDelivery(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Verify Delivery Confirmation");
        builder.setMessage("Are you sure you want to verify the delivery?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView idView = findViewById(R.id.idText);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, Object> docData = new HashMap<>();
                        docData.put("status", "Verified");
                        db.collection("delivery").document(idView.getText().toString())
                                .update(docData);
                        finish();
                        startActivity(getIntent());
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
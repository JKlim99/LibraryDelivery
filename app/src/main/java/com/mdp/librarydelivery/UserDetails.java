package com.mdp.librarydelivery;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class UserDetails extends AppCompatActivity {

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle param = getIntent().getExtras();
        String id = "";
        if (param != null) {
            id = param.getString("id");
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override

            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserModel userModel = document.toObject(UserModel.class);
                        userModel.setId(document.getId());
                        //
                        TextView idView = findViewById(R.id.idText);
                        idView.setText(userModel.getId());
                        //
                        TextView usernameView = findViewById(R.id.usernameText);
                        usernameView.setText(userModel.getUsername());
                        //
                        TextView user_typeView = findViewById(R.id.user_typeText);
                        user_typeView.setText(userModel.getUser_type());
                        //
                        TextView phone_numberView = findViewById(R.id.phone_numberText);
                        phone_numberView.setText(userModel.getPhone_number());
                        //
                        TextView emailView = findViewById(R.id.emailText);
                        emailView.setText(userModel.getEmail());
                        //
                        TextView passwordView = findViewById(R.id.passwordText);
                        passwordView.setText(userModel.getPassword());
                        //
                        TextView addressView = findViewById(R.id.addressText);
                        passwordView.setText(userModel.getAddress());

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
        setContentView(R.layout.activity_user_details);

    }}
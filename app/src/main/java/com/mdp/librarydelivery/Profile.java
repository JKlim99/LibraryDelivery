package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProgressBar loading = (ProgressBar)findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);

        Session session = new Session(getApplicationContext());
        EditText usernameText   = (EditText)findViewById(R.id.usernameText);
        EditText emailText   = (EditText)findViewById(R.id.emailText);
        EditText phoneText   = (EditText)findViewById(R.id.phoneText);
        EditText addressText   = (EditText)findViewById(R.id.addressText);
        EditText passwordText   = (EditText)findViewById(R.id.textPassword);
        EditText confirmPasswordText   = (EditText)findViewById(R.id.textConfirmPassword);
        TextView warning = (TextView)findViewById(R.id.warning);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
        .document(session.getid())
        .get()
        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserModel userModel = documentSnapshot.toObject(UserModel.class);
                usernameText.setText(userModel.getUsername());
                emailText.setText(userModel.getEmail());
                phoneText.setText(userModel.getPhone_number());
                addressText.setText(userModel.getAddress());
                passwordText.setText(userModel.getPassword());

                loading.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void updateProfile(View view) {
        ProgressBar loading = (ProgressBar)findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);

        Session session = new Session(getApplicationContext());
        EditText usernameText   = (EditText)findViewById(R.id.usernameText);
        EditText emailText   = (EditText)findViewById(R.id.emailText);
        EditText phoneText   = (EditText)findViewById(R.id.phoneText);
        EditText addressText   = (EditText)findViewById(R.id.addressText);
        EditText passwordText   = (EditText)findViewById(R.id.textPassword);
        EditText confirmPasswordText   = (EditText)findViewById(R.id.textConfirmPassword);
        TextView warning = (TextView)findViewById(R.id.warning);

        if(!passwordText.getText().toString().equals(confirmPasswordText.getText().toString())){
            warning.setVisibility(View.VISIBLE);
            return;
        }

        Map<String, Object> docData = new HashMap<>();
        docData.put("username", usernameText.getText().toString());
        docData.put("email", emailText.getText().toString());
        docData.put("phone_number", phoneText.getText().toString());
        docData.put("address", addressText.getText().toString());
        docData.put("password", passwordText.getText().toString());
        docData.put("user_type", "User");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(session.getid())
        .set(docData)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error writing document", e);
            }
        });
        warning.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.INVISIBLE);
        confirmPasswordText.setText("");
        Toast.makeText(getApplicationContext(), "Profile updated", Toast.LENGTH_SHORT).show();
    }
}
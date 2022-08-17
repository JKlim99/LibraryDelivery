package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {
    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Spinner spinner = (Spinner) findViewById(R.id.userTypeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void loginLogic(View view) {
        ProgressBar loading = (ProgressBar)findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);

        EditText usernameText   = (EditText)findViewById(R.id.usernameText);
        EditText passwordText   = (EditText)findViewById(R.id.textPassword);
        Spinner userTypeSpinner = (Spinner)findViewById(R.id.userTypeSpinner);
        TextView warning = (TextView)findViewById(R.id.warning);
        String usertype = userTypeSpinner.getSelectedItem().toString();

        Log.d(TAG, usernameText.getText().toString() + passwordText.getText().toString() + usertype);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
        .whereEqualTo("username", usernameText.getText().toString())
        .whereEqualTo("password", passwordText.getText().toString())
        .whereEqualTo("user_type", usertype)
        .whereEqualTo("status", "active")
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Integer found = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();
                        found += 1;
                        warning.setVisibility(View.INVISIBLE);
                        usernameText.setText("");
                        passwordText.setText("");
                        Log.d(TAG, document.getId() + " => " + document.getData());

                        UserModel userModel = document.toObject(UserModel.class);
                        userModel.setId(document.getId());
                        //set session
                        Session session = new Session(getApplicationContext());
                        session.setusername(userModel.getUsername());
                        session.setid(userModel.getId());

                        if(usertype.equals("User")){
                            Intent home = new Intent(Login.this, UserHome.class);
                            startActivity(home);
                        }
                        else if(usertype.equals("Deliverer")){
                            Intent home = new Intent(Login.this, DelivererHome.class);
                            startActivity(home);
                        }
                        else{
                            Intent home = new Intent(Login.this, AdminHome.class);
                            startActivity(home);
                        }
                    }
                    if(found == 0) {
                        warning.setVisibility(View.VISIBLE);
                        Log.d(TAG, "User not found");
                    }
                    loading.setVisibility(View.INVISIBLE);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
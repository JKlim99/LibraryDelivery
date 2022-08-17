package com.mdp.librarydelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminUserCreate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_create);

        Spinner spinner = findViewById(R.id.userTypeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void create(View view){
        EditText usernameText   = findViewById(R.id.usernameText);
        EditText emailText   = findViewById(R.id.emailText);
        EditText phoneText   = findViewById(R.id.phoneText);
        EditText addressText   = findViewById(R.id.addressText);
        EditText passwordText   = findViewById(R.id.textPassword);
        Spinner userTypeSpinner = (Spinner)findViewById(R.id.userTypeSpinner);
        String usertype = userTypeSpinner.getSelectedItem().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> docData = new HashMap<>();
        docData.put("username", usernameText.getText().toString());
        docData.put("email", emailText.getText().toString());
        docData.put("phone_number", phoneText.getText().toString());
        docData.put("address", addressText.getText().toString());
        docData.put("password", passwordText.getText().toString());
        docData.put("user_type", usertype);
        docData.put("status", "active");
        db.collection("users")
                .add(docData);

        Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
        finish();
        Intent page = new Intent(AdminUserCreate.this, AdminUserList.class);
        startActivity(page);
    }
}
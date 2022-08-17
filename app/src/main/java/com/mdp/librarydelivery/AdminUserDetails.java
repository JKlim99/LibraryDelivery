package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminUserDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle param = getIntent().getExtras();
        String id = "";
        if(param != null) {
            id = param.getString("id");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_details);

        EditText usernameText   = findViewById(R.id.usernameText);
        EditText emailText   = findViewById(R.id.emailText);
        EditText phoneText   = findViewById(R.id.phoneText);
        EditText addressText   = findViewById(R.id.addressText);
        EditText passwordText   = findViewById(R.id.textPassword);
        TextView idView = findViewById(R.id.idText);

        Spinner spinner = findViewById(R.id.userTypeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserModel userModel = documentSnapshot.toObject(UserModel.class);
                        userModel.setId(documentSnapshot.getId());
                        usernameText.setText(userModel.getUsername());
                        emailText.setText(userModel.getEmail());
                        phoneText.setText(userModel.getPhone_number());
                        addressText.setText(userModel.getAddress());
                        passwordText.setText(userModel.getPassword());
                        idView.setText(userModel.getId());
                        int i = 0;
                        if(userModel.getUser_type().toString().equals("Deliverer")){
                            i = 1;
                        }
                        else if(userModel.getUser_type().toString().equals("Admin")){
                            i = 2;
                        }
                        spinner.setSelection(i);
                    }
                });
    }

    public void update(View view) {

        EditText usernameText   = findViewById(R.id.usernameText);
        EditText emailText   = findViewById(R.id.emailText);
        EditText phoneText   = findViewById(R.id.phoneText);
        EditText addressText   = findViewById(R.id.addressText);
        EditText passwordText   = findViewById(R.id.textPassword);
        TextView idView = findViewById(R.id.idText);
        Spinner userTypeSpinner = (Spinner)findViewById(R.id.userTypeSpinner);
        String usertype = userTypeSpinner.getSelectedItem().toString();

        Map<String, Object> docData = new HashMap<>();
        docData.put("username", usernameText.getText().toString());
        docData.put("email", emailText.getText().toString());
        docData.put("phone_number", phoneText.getText().toString());
        docData.put("address", addressText.getText().toString());
        docData.put("password", passwordText.getText().toString());
        docData.put("user_type", usertype);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(idView.getText().toString())
                .update(docData);
        Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }

    public void delete(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure you want to delete the user?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView idView = findViewById(R.id.idText);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> docData = new HashMap<>();
                docData.put("status", "Deleted");
                db.collection("users").document(idView.getText().toString())
                        .update(docData);
                finish();
                Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_SHORT).show();
                Intent page = new Intent(AdminUserDetails.this, AdminUserList.class);
                startActivity(page);
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
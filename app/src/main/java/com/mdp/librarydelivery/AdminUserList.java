package com.mdp.librarydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class AdminUserList extends AppCompatActivity {
    private static final String TAG = "";
    ArrayList<UserModel> UserModelArrayList;
    ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereNotEqualTo("status", "Deleted")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            UserModelArrayList = new ArrayList<>();
                            userListView = findViewById(R.id.UserListView);

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                UserModel userModel = document.toObject(UserModel.class);
                                userModel.setId(document.getId());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                UserModelArrayList.add(userModel);

                            }
                            // after that we are passing our array list to our adapter class.
                            AdminUserListAdapter adapter = new AdminUserListAdapter(AdminUserList.this, UserModelArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            userListView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);
        FloatingActionButton fab = findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent page = new Intent(AdminUserList.this, AdminUserCreate.class);
                startActivity(page);
            }
        });
    }
}
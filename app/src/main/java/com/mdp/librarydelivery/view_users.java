package com.mdp.librarydelivery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class view_users extends ArrayAdapter<UserModel> {

    // constructor for our list view adapter.
    public view_users(@NonNull Context context, ArrayList<UserModel> UserModelArrayList) {
        super(context, 0, UserModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_view_users, parent, false);
        }

        UserModel userModel = getItem(position);
        TextView userNameView = listitemView.findViewById(R.id.textView2);
        userNameView.setText(userModel.getUsername());



        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                Context context = v.getContext();
                Intent i = new Intent(context, UserDetails.class);
                i.putExtra("id", userModel.getId());
                context.startActivity(i);
            }
        });
        return listitemView;
    }
}
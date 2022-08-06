package com.mdp.librarydelivery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;

public class View_delivery_list extends ArrayAdapter<DeliveryModel> {

    public View_delivery_list(@NonNull Context context, ArrayList<DeliveryModel> DeliveryModelArrayList) {
        super(context, 0, DeliveryModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_view_delivery_list, parent, false);
        }

        DeliveryModel deliveryModel = getItem(position);
        TextView bookNameView = listitemView.findViewById(R.id.textView);
        bookNameView.setText(deliveryModel.getId());


        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                Context context = v.getContext();
                Intent i = new Intent(context, DeliveryDetails.class);
                i.putExtra("id", deliveryModel.getId());
                context.startActivity(i);
            }
        });
        return listitemView;
    }

}
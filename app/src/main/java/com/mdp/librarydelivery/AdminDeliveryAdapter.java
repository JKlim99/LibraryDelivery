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

public class AdminDeliveryAdapter extends ArrayAdapter<DeliveryModel> {

    // constructor for our list view adapter.
    public AdminDeliveryAdapter(@NonNull Context context, ArrayList<DeliveryModel> DeliveryModelArrayList) {
        super(context, 0, DeliveryModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.delivery_items, parent, false);
        }

        DeliveryModel deliveryModel = getItem(position);
        TextView bookNameView = listitemView.findViewById(R.id.textView);
        TextView delivererView = listitemView.findViewById(R.id.delivererText);
        TextView statusView = listitemView.findViewById(R.id.statusText);
        bookNameView.setText("Delivery #"+deliveryModel.getId()+" ("+deliveryModel.getType()+")");
        if(!deliveryModel.getDeliverer_name().equals("")) {
            delivererView.setText("Deliverer: " + deliveryModel.getDeliverer_name());
        }
        statusView.setText("Status: "+deliveryModel.getStatus());

        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Loading completed", Toast.LENGTH_SHORT).show();
                Context context = v.getContext();
                Intent i = new Intent(context, AdminDeliveryDetails.class);
                i.putExtra("id", deliveryModel.getId());
                context.startActivity(i);
            }
        });
        return listitemView;
    }
}
package com.mdp.librarydelivery.ui.delivery;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mdp.librarydelivery.BookLoanAdapter;
import com.mdp.librarydelivery.BookLoanModel;
import com.mdp.librarydelivery.DeliveryAdapter;
import com.mdp.librarydelivery.DeliveryModel;
import com.mdp.librarydelivery.R;
import com.mdp.librarydelivery.Session;
import com.mdp.librarydelivery.databinding.FragmentDeliveryBinding;

import java.util.ArrayList;

public class DeliveryFragment extends Fragment {
    private static final String TAG = "";
    ArrayList<DeliveryModel> DeliveryModelArrayList;
    ListView deliveryListView;

    private FragmentDeliveryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDeliveryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Session session = new Session(root.getContext());
        db.collection("delivery")
                .whereEqualTo("receiver_id", session.getid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            DeliveryModelArrayList = new ArrayList<>();
                            deliveryListView = root.findViewById(R.id.DeliveryListView);

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DeliveryModel deliveryModel = document.toObject(DeliveryModel.class);
                                deliveryModel.setId(document.getId());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                DeliveryModelArrayList.add(deliveryModel);

                            }
                            // after that we are passing our array list to our adapter class.
                            Context context = root.getContext();
                            DeliveryAdapter adapter = new DeliveryAdapter(context, DeliveryModelArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            deliveryListView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
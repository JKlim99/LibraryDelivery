package com.mdp.librarydelivery.ui.loanedbooks;

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
import com.mdp.librarydelivery.R;
import com.mdp.librarydelivery.databinding.FragmentLoanedbooksBinding;

import java.util.ArrayList;

public class LoanedBooksFragment extends Fragment {
    private static final String TAG = "";
    ArrayList<BookLoanModel> BookLoanModelArrayList;
    ListView bookLoanListView;

    private FragmentLoanedbooksBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLoanedbooksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("bookLoan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            BookLoanModelArrayList = new ArrayList<>();
                            bookLoanListView = root.findViewById(R.id.BookLoanListView);

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BookLoanModel bookLoanModel = document.toObject(BookLoanModel.class);
                                bookLoanModel.setId(document.getId());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                BookLoanModelArrayList.add(bookLoanModel);

                            }
                            // after that we are passing our array list to our adapter class.
                            Context context = root.getContext();
                            BookLoanAdapter adapter = new BookLoanAdapter(context, BookLoanModelArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            bookLoanListView.setAdapter(adapter);
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
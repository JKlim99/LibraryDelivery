package com.mdp.librarydelivery.ui.booklist;

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
import com.mdp.librarydelivery.BookListAdapter;
import com.mdp.librarydelivery.BookModel;
import com.mdp.librarydelivery.R;
import com.mdp.librarydelivery.databinding.FragmentBooklistBinding;

import java.util.ArrayList;

public class BookListFragment extends Fragment {
    private static final String TAG = "";
    ArrayList<BookModel> BookModelArrayList;
    ListView bookListView;

    private FragmentBooklistBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBooklistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("books")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            BookModelArrayList = new ArrayList<>();
                            bookListView = root.findViewById(R.id.BookListView);

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BookModel bookModel = document.toObject(BookModel.class);
                                bookModel.setId(document.getId());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                BookModelArrayList.add(bookModel);

                            }
                            // after that we are passing our array list to our adapter class.
                            Context context = root.getContext();
                            BookListAdapter adapter = new BookListAdapter(context, BookModelArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            bookListView.setAdapter(adapter);
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
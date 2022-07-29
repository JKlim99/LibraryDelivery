package com.mdp.librarydelivery.ui.loanedbooks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mdp.librarydelivery.databinding.FragmentLoanedbooksBinding;

public class LoanedBooksFragment extends Fragment {

    private FragmentLoanedbooksBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LoanedBooksViewModel loanedBooksViewModel =
                new ViewModelProvider(this).get(LoanedBooksViewModel.class);

        binding = FragmentLoanedbooksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        loanedBooksViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.mdp.librarydelivery.ui.delivery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mdp.librarydelivery.databinding.FragmentDeliveryBinding;

public class DeliveryFragment extends Fragment {

    private FragmentDeliveryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DeliveryViewModel deliveryViewModel =
                new ViewModelProvider(this).get(DeliveryViewModel.class);

        binding = FragmentDeliveryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        deliveryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
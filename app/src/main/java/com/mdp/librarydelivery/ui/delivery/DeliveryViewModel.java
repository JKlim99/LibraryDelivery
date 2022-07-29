package com.mdp.librarydelivery.ui.delivery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DeliveryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DeliveryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is delivery list");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
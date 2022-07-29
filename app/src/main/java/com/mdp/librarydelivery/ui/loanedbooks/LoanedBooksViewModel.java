package com.mdp.librarydelivery.ui.loanedbooks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoanedBooksViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LoanedBooksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is loaned book list");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
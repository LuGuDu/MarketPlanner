package com.lugudu.marketplanner.ui.shoplists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShopListsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ShopListsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Listas de compra");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
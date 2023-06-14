package com.lugudu.marketplanner.ui.catalogos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CatalogosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CatalogosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Catalogos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
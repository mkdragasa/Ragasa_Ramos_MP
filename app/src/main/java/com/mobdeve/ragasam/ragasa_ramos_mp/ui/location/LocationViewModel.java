package com.mobdeve.ragasam.ragasa_ramos_mp.ui.location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LocationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Location fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
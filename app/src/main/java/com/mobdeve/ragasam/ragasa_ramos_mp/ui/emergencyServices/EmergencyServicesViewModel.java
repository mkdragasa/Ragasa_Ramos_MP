package com.mobdeve.ragasam.ragasa_ramos_mp.ui.emergencyServices;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EmergencyServicesViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public EmergencyServicesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Emergency Services fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
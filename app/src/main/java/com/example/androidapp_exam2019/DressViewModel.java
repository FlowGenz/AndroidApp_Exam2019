package com.example.androidapp_exam2019;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DressViewModel extends ViewModel {
    private MutableLiveData<String> dressId;

    public LiveData<String> getDressId() {
        if (dressId == null) {
            dressId = new MutableLiveData<>();
        }

        return dressId;
    }

    public void setdressId(String id) {
        dressId.setValue(id);
    }
}

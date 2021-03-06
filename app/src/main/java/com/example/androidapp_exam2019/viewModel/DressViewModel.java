package com.example.androidapp_exam2019.viewModel;

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

    public void setDressId(String id) {
        if (dressId == null) {
            dressId = new MutableLiveData<>();
        }
        dressId.setValue(id);
    }
}

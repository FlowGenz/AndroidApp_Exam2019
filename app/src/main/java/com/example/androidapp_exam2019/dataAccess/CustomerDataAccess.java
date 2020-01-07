package com.example.androidapp_exam2019.dataAccess;

import android.content.SharedPreferences;

import com.example.androidapp_exam2019.model.Customer;

public interface CustomerDataAccess {

	void userLogin(String username, SharedPreferences.Editor editor);

    void registerUser(Customer customer);

}

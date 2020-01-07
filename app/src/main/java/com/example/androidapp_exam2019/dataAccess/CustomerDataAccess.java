package com.example.androidapp_exam2019.dataAccess;

import com.example.androidapp_exam2019.model.Customer;

public interface CustomerDataAccess {

    Customer getCustomer(String username);

    void postCustomer(Customer customer);

    Customer putCustomer(String username, Customer customer);
}

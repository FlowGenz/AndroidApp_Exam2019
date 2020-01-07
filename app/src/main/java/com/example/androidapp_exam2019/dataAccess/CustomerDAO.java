package com.example.androidapp_exam2019.dataAccess;

import com.example.androidapp_exam2019.dataAccess.retrofit.CustomerRetrofit;
import com.example.androidapp_exam2019.dataAccess.retrofit.RetrofitSingleton;

import retrofit2.Retrofit;

public class CustomerDAO
 {

    private CustomerRetrofit dataAccess;
    private Retrofit retrofit;

    public CustomerDAO() {
       retrofit = RetrofitSingleton.getClient();
       dataAccess = retrofit.create(CustomerRetrofit.class);
    }

    /*public void Customer(String username) {

    }

    public Call<Void> postCustomer(Customer customer) {

    }

    public Call<Customer> putCustomer(String username, Customer customer) {

    }*/
}

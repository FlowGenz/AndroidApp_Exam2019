package com.example.androidapp_exam2019;

import com.example.androidapp_exam2019.model.Customer;
import com.example.androidapp_exam2019.model.Dress;
import com.example.androidapp_exam2019.model.Favorite;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IDressApi {
    @GET("dress")
    Call<ArrayList<Dress>> getAllDresses();

    @GET("favorite")
    Call<ArrayList<Favorite>> getAllFavorites();

    @GET("customer")
    Call<ArrayList<Customer>> getAllCustomers();
}

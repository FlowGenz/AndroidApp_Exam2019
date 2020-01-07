package com.example.androidapp_exam2019.dataAccess.retrofit;

import com.example.androidapp_exam2019.model.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface OrderRetrofit {
    @GET("dressOrder/{username}")
    Call<Order> getOrderOfUser();

    @POST("dressOrder")
    Call<Order> postOrder(@Body Order order);

    @PUT("dressOrder")
    Call<Order> putOrder(@Body Order order);
}

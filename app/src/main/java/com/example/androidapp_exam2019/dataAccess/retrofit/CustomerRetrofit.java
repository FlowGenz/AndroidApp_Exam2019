package com.example.androidapp_exam2019.dataAccess.retrofit;

import com.example.androidapp_exam2019.model.Customer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CustomerRetrofit {

    @GET("customer/{username}")
    Call<Customer> getCustomer(@Path("username") String username);

    @POST("customer")
    Call<Void> postCustomer(@Body Customer customer);

    @PUT("customer/{username}")
    Call<Customer> putCustomer(@Path("username") String username, @Body Customer customer);

}

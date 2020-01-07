package com.example.androidapp_exam2019.dataAccess.retrofit;

import com.example.androidapp_exam2019.dataAccess.Jwt;
import com.example.androidapp_exam2019.model.LoginUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JwtRetrofit {
    @POST("jwt")
    Call<Jwt> getJwtToken(@Body LoginUser login);
}

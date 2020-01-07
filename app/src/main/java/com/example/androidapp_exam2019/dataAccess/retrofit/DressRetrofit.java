package com.example.androidapp_exam2019.dataAccess.retrofit;

import com.example.androidapp_exam2019.model.Dress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DressRetrofit {

    @GET("dress")
    Call<ArrayList<Dress>> getAllDresses();

    @GET("dress/{id}")
    Call<Dress> getDress(@Path("id") String id);
}

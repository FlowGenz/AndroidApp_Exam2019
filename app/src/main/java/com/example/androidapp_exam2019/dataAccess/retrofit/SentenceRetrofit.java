package com.example.androidapp_exam2019.dataAccess.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SentenceRetrofit {
    @GET("sentence")
    Call<String> getSentence();
}

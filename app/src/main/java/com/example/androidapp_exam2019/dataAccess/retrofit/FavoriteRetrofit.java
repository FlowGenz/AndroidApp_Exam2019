package com.example.androidapp_exam2019.dataAccess.retrofit;

import com.example.androidapp_exam2019.model.Favorite;
import com.example.androidapp_exam2019.model.FavoriteDress;
import com.example.androidapp_exam2019.model.post.FavoritePost;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FavoriteRetrofit {
    @GET("favorite/{username}")
    Call<ArrayList<Favorite>> getFavoritesOfUser(@Path("username") String username);

    @GET("favorite/{username}/{dressId}")
    Call<FavoriteDress> isFavorite(@Path("username") String username, @Path("dressId") String dressId);

    @POST("favorite")
    Call<Void> postFavorite(@Body FavoritePost favorite);

    @DELETE("favorite/{id}")
    Call<Void> deleteFavorite(@Path("id") String id);
}

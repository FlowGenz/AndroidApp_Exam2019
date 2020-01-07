package com.example.androidapp_exam2019.dataAccess;

import com.example.androidapp_exam2019.model.Customer;
import com.example.androidapp_exam2019.model.Dress;
import com.example.androidapp_exam2019.model.Favorite;
import com.example.androidapp_exam2019.model.FavoriteDress;
import com.example.androidapp_exam2019.model.LoginUser;
import com.example.androidapp_exam2019.model.Order;
import com.example.androidapp_exam2019.model.post.FavoritePost;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IDressApi {

    //JwT
    @POST("jwt")
    Call<Jwt> getJwtToken(@Body LoginUser login);

    //***CUSTOMER
    /*@GET("customer/{username}")
    Call<Customer> getCustomer(@Path("username") String username);

    @POST("customer")
    Call<Void> postCustomer(@Body Customer customer);

    @PUT("customer/{username}")
    Call<Customer> putCustomer(@Path("username") String username, @Body Customer customer);

    //***DRESS
    @GET("dress")
    Call<ArrayList<Dress>> getAllDresses();

    @GET("dress/{id}")
    Call<Dress> getDress(@Path("id") String id);*/

    //***ORDER
    @GET("dressOrder/{username}")
    Call<Order> getOrderOfUser();

    @POST("dressOrder")
    Call<Order> postOrder(@Body Order order);

    @PUT("dressOrder")
    Call<Order> putOrder(@Body Order order);

    //***FAVORITE
    @GET("favorite/{username}")
    Call<ArrayList<Favorite>> getFavoritesOfUser(@Path("username") String username);

    @GET("favorite/{username}/{dressId}")
    Call<FavoriteDress> isFavorite(@Path("username") String username, @Path("dressId") String dressId);

    @POST("favorite")
    Call<Void> postFavorite(@Body FavoritePost favorite);

    @DELETE("favorite/{id}")
    Call<Void> deleteFavorite(@Path("id") String id);

    //***SENTENCE
    @GET("sentence")
    Call<String> getSentence();
}

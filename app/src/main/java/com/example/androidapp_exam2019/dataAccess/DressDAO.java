package com.example.androidapp_exam2019.dataAccess;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidapp_exam2019.R;
import com.example.androidapp_exam2019.dataAccess.retrofit.DressRetrofit;
import com.example.androidapp_exam2019.dataAccess.retrofit.RetrofitSingleton;
import com.example.androidapp_exam2019.model.Dress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.security.AccessController.getContext;

public class DressDAO implements DressDataAccess {

    private DressRetrofit dataAccess;
    private Retrofit retrofit;

    public DressDAO() {
        retrofit = RetrofitSingleton.getClient();
        dataAccess = retrofit.create(DressRetrofit.class);

    }

    @Override
    public ArrayList<Dress> getAllDresses() {
        Call<ArrayList<Dress>> call = dataAccess.getAllDresses();
        call.enqueue(new Callback<ArrayList<Dress>>() {
            @Override
            public void onResponse(Call<ArrayList<Dress>> call, Response<ArrayList<Dress>> response) {
                if (!response.isSuccessful()) {
                    if (getContext() != null)
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                    return;
                }
                adapter.setDresses(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Dress>> call, Throwable t) {
                if (getContext() != null)
                    Toast.makeText(getContext(), getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public Dress getDress(String id) {
        Call<Dress> call = dataAccess.getDress(id);

        call.enqueue(new Callback<Dress>() {
            @Override
            public void onResponse(Call<Dress> call, Response<Dress> response) {
                if (!response.isSuccessful()) {
                    if (getContext() != null)
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                    return;
                }
                dress = response.body();
                articleName.setText(response.body().getDressName());
                if (response.body().isAvailable()) {
                    articleAvailability.setText(getString(R.string.dressAvailable));
                    articleAvailability.setTextColor(getResources().getColor(R.color.available));
                } else {
                    articleAvailability.setText(getString(R.string.dressNotAvailable));
                    articleAvailability.setTextColor(getResources().getColor(R.color.notAvailable));
                }
                articlePrice.setText(response.body().getPrice().toString() + " €");
                articleDescription.setText(response.body().getDescription());
                articleSize.setText((response.body().getSize()));
                if (response.body().getDateBeginAvailable() != null)
                    articleDateBegin.setText(response.body().getDateBeginAvailable().toString());
                if (response.body().getDateEndAvailable() != null)
                    articleDateEnd.setText(response.body().getDateEndAvailable().toString());
                Glide.with(view).load(response.body().getUrlImage()).into(articlePicture);
            }

            @Override
            public void onFailure(Call<Dress> call, Throwable t) {
                if (getContext() != null)
                    Toast.makeText(getContext(), getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
            }
        });
    }
}

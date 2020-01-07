package com.example.androidapp_exam2019.dataAccess;

import android.app.Activity;
import android.widget.Toast;

import com.example.androidapp_exam2019.R;
import com.example.androidapp_exam2019.dataAccess.retrofit.DressRetrofit;
import com.example.androidapp_exam2019.dataAccess.retrofit.RetrofitSingleton;
import com.example.androidapp_exam2019.model.Dress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DressDAO implements DressDataAccess {

    private DressRetrofit dataAccess;
    private Retrofit retrofit;
    private Activity activity;
    private ArrayList<Dress> dresses;
    private Dress dress;

    public DressDAO(Activity activity) {
        retrofit = RetrofitSingleton.getClient();
        dataAccess = retrofit.create(DressRetrofit.class);
        this.activity = activity;
    }

    @Override
    public ArrayList<Dress> getAllDresses() {

        dresses = new ArrayList<>();
        Call<ArrayList<Dress>> call = dataAccess.getAllDresses();
        call.enqueue(new Callback<ArrayList<Dress>>() {
            @Override
            public void onResponse(Call<ArrayList<Dress>> call, Response<ArrayList<Dress>> response) {
                if (!response.isSuccessful()) {
                    if (activity.getApplicationContext() != null)
                        Toast.makeText(activity.getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                    return;
                }
                dresses = response.body();
            }

            @Override
            public void onFailure(Call<ArrayList<Dress>> call, Throwable t) {
                if (activity.getApplicationContext() != null)
                    Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
            }
        });
        return dresses;
    }

    @Override
    public Dress getDress(String id) {

        dress = new Dress();
        Call<Dress> call = dataAccess.getDress(id);
        call.enqueue(new Callback<Dress>() {
            @Override
            public void onResponse(Call<Dress> call, Response<Dress> response) {
                if (!response.isSuccessful()) {
                    if (activity.getApplicationContext() != null)
                        Toast.makeText(activity.getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                    return;
                }
                dress = response.body();
            }

            @Override
            public void onFailure(Call<Dress> call, Throwable t) {
                if (activity.getApplicationContext() != null)
                    Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
            }
        });
        return dress;
    }
}

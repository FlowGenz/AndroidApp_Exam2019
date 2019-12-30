package com.example.androidapp_exam2019;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp_exam2019.dataAccess.IDressApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.homeSentenceOfTheDay) public TextView homeSentenceOfTheDay;
    Retrofit retrofit;
    IDressApi dressApi;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        retrofit = RetrofitSingleton.getClient();
        dressApi = retrofit.create(IDressApi.class);

        Call<String> call = dressApi.getSentence();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    if (getContext() != null)
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                    return;
                }
                homeSentenceOfTheDay.setText(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (getContext() != null)
                    Toast.makeText(getContext(), getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}

package com.example.androidapp_exam2019;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp_exam2019.constants.AppSharedPreferences;
import com.example.androidapp_exam2019.dataAccess.IDressApi;
import com.example.androidapp_exam2019.dialogs.LogOutDialog;
import com.example.androidapp_exam2019.model.Customer;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.profileFNameId) public TextView profileFName;
    @BindView(R.id.profileLNameId) public TextView profileLName;
    @BindView(R.id.profileLoyaltyPointsid) public TextView profileLoyaltyPoints;
    @BindView(R.id.profileCouponNumberId) public TextView profileCouponNumberId;
    @BindView(R.id.profileLogoutButtonId) public ImageButton profileLogoutButton;
    Retrofit retrofit;
    IDressApi dressApi;



    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(AppSharedPreferences.SHARED_PREFERENCES, MODE_PRIVATE);

        retrofit = RetrofitSingleton.getClient();
        dressApi = retrofit.create(IDressApi.class);
        Call<Customer> call = dressApi.getCustomer(sharedPreferences.getString(AppSharedPreferences.USERNAME, ""));

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (!response.isSuccessful()) {
                    if (getContext() != null)
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                    return;
                } else {
                    profileFName.setText(response.body().getFirstName());
                    profileLName.setText(response.body().getLastName());
                    profileLoyaltyPoints.setText(String.valueOf(response.body().getLoyaltyPoints()));
                    profileCouponNumberId.setText((String.valueOf(response.body().getLoyaltyPoints()%100)));
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                if (getContext() != null)
                    Toast.makeText(getContext(), getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
            }
        });

        profileLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogoutDialog();
            }
        });

        return view;
    }

    private void openLogoutDialog() {
        LogOutDialog dialog = new LogOutDialog();
        dialog.show(getActivity().getSupportFragmentManager(), "LogoutDialog");
    }

}

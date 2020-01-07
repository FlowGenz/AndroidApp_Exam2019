package com.example.androidapp_exam2019.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp_exam2019.R;
import com.example.androidapp_exam2019.dataAccess.retrofit.RetrofitSingleton;
import com.example.androidapp_exam2019.constants.AppSharedPreferences;
import com.example.androidapp_exam2019.dataAccess.ConnectionStateProvider;
import com.example.androidapp_exam2019.dataAccess.IDressApi;
import com.example.androidapp_exam2019.dataAccess.Jwt;
import com.example.androidapp_exam2019.model.Customer;
import com.example.androidapp_exam2019.model.LoginUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConnectionActivity extends AppCompatActivity {

    @BindView(R.id.connectionLogoId) public ImageView connectionLogo;
    @BindView(R.id.connectionUsernameId) public EditText connectionUsername;
    @BindView(R.id.connectionPasswordId) public EditText connectionPassword;
    @BindView(R.id.connectionLoginId) public Button connectionLogin;
    @BindView(R.id.connectionSignInId) public TextView connectionSignIn;

    private Retrofit retrofit;
    private IDressApi dressApi;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        ButterKnife.bind(this);
        retrofit = RetrofitSingleton.getClient();
        dressApi = retrofit.create(IDressApi.class);

        if (!ConnectionStateProvider.isOnline(getApplicationContext())) {
            Toast.makeText(this, getString(R.string.networkConnectionError), Toast.LENGTH_SHORT).show();
        }

        connectionLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionStateProvider.isOnline(getApplicationContext())) {
                    saveData();
                    SharedPreferences sharedPreferences = getSharedPreferences(AppSharedPreferences.SHARED_PREFERENCES, MODE_PRIVATE);
                    String username = sharedPreferences.getString(AppSharedPreferences.USERNAME, "");
                    String password = sharedPreferences.getString(AppSharedPreferences.PASSWORD, "");

                    LoginUser login = new LoginUser(username, password);
                    Call<Jwt> call = dressApi.getJwtToken(login);
                    call.enqueue(new Callback<Jwt>() {
                        @Override
                        public void onResponse(Call<Jwt> call, Response<Jwt> response) {
                            if (!response.isSuccessful()) {
                                if (getApplicationContext() != null)
                                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                                return;
                            }
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(AppSharedPreferences.ACCESS_TOKEN, response.body().getAccess_token());

                            Call<Customer> callGetId = dressApi.getCustomer(sharedPreferences.getString(AppSharedPreferences.USERNAME, ""));
                            callGetId.enqueue(new Callback<Customer>() {
                                @Override
                                public void onResponse(Call<Customer> call, Response<Customer> response) {
                                    if (!response.isSuccessful()) {
                                        if (getApplicationContext() != null)
                                            Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    editor.putString(AppSharedPreferences.USER_ID, response.body().getId());
                                    editor.apply();
                                    Intent intentGoPartner = new Intent(ConnectionActivity.this, PartnerActivity.class);
                                    startActivity(intentGoPartner);
                                }

                                @Override
                                public void onFailure(Call<Customer> call, Throwable t) {
                                    if (getApplicationContext() != null)
                                        Toast.makeText(getApplicationContext(), getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Jwt> call, Throwable t) {
                            if (getApplicationContext() != null)
                                Toast.makeText(getApplicationContext(), getString(R.string.networkConnectionError), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.networkConnectionError), Toast.LENGTH_SHORT).show();
                }
                //}
            }
        });

        connectionSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionStateProvider.isOnline(getApplicationContext())) {
                    Intent intentGoRegister = new Intent(ConnectionActivity.this, RegisterActivity.class);
                    startActivity(intentGoRegister);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.networkConnectionError), Toast.LENGTH_SHORT).show();
                }
            }
        });
        loadData();
        updateFields();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(AppSharedPreferences.SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(AppSharedPreferences.USERNAME, connectionUsername.getText().toString());
        editor.putString(AppSharedPreferences.PASSWORD, connectionPassword.getText().toString());

        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(AppSharedPreferences.SHARED_PREFERENCES, MODE_PRIVATE);
        username = sharedPreferences.getString(AppSharedPreferences.USERNAME, "");
        password = sharedPreferences.getString(AppSharedPreferences.PASSWORD, "");
    }

    public void updateFields() {
        connectionUsername.setText(username);
        connectionPassword.setText(password);
    }
}

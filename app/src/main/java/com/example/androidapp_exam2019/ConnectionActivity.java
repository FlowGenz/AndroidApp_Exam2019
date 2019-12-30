package com.example.androidapp_exam2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidapp_exam2019.constants.AppSharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConnectionActivity extends AppCompatActivity {

    @BindView(R.id.connectionLogoId) ImageView connectionLogo;
    @BindView(R.id.connectionUsernameId) public EditText connectionUsername;
    @BindView(R.id.connectionPasswordId) public EditText connectionPassword;
    @BindView(R.id.connectionLoginId) public Button connectionLogin;
    @BindView(R.id.connectionSignInId) public TextView connectionSignIn;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        ButterKnife.bind(this);

        connectionLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!ApiConstants.username.equals(connectionUsername.getText().toString()) || !ApiConstants.password.equals(connectionPassword.getText().toString())) {
                    Toast.makeText(ConnectionActivity.this, "Login failed, try again", Toast.LENGTH_SHORT).show();
                } else {*/
                    saveData();
                    Intent intentGoPartner = new Intent(ConnectionActivity.this, PartnerActivity.class);
                    startActivity(intentGoPartner);
                //}
            }
        });

        connectionSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGoRegister = new Intent(ConnectionActivity.this, RegisterActivity.class);
                startActivity(intentGoRegister);
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

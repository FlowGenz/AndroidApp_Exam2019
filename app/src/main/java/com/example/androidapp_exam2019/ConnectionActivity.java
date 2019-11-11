package com.example.androidapp_exam2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConnectionActivity extends AppCompatActivity {

    @BindView(R.id.connectionLogoId) ImageView connectionLogo;
    @BindView(R.id.connectionUsernameId) public EditText connectionUsername;
    @BindView(R.id.connectionPasswordId) public EditText connectionPassword;
    @BindView(R.id.connectionLoginId) public Button connectionLogin;
    @BindView(R.id.connectionSignInId) public TextView connectionSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        ButterKnife.bind(this);

        connectionLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGoMainScreen = new Intent(ConnectionActivity.this, MainScreenActivity.class);
                startActivity(intentGoMainScreen);
            }
        });

        connectionSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGoRegister = new Intent(ConnectionActivity.this, RegisterActivity.class);
                startActivity(intentGoRegister);
            }
        });
    }
}

package com.example.androidapp_exam2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.registerUsernameId) public EditText registerUsername;
    @BindView(R.id.registerPasswordId) public EditText registerPassword;
    @BindView(R.id.registerEmailId) public EditText registerEmail;
    @BindView(R.id.registerFNameId) public EditText registerFName;
    @BindView(R.id.registerLNameId) public EditText registerLName;
    @BindView(R.id.registerAddressId) public EditText registerAddress;
    @BindView(R.id.registerPhoneId) public EditText registerPhone;
    @BindView(R.id.registerCreateAccountId) public Button registerCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        registerCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGoMainScreen = new Intent(RegisterActivity.this, MainScreenActivity.class);
                startActivity(intentGoMainScreen);
            }
        });
    }
}

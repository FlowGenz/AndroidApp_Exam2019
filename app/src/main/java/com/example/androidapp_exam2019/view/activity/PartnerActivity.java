package com.example.androidapp_exam2019.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.androidapp_exam2019.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartnerActivity extends AppCompatActivity {

    @BindView(R.id.tvPartnerAmiraLebsatteId) public TextView partner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);
        ButterKnife.bind(this);

        partner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGoMainScreen = new Intent(PartnerActivity.this, MainScreenActivity.class);
                startActivity(intentGoMainScreen);
            }
        });
    }
}

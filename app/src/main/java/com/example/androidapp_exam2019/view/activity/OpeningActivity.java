package com.example.androidapp_exam2019.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.androidapp_exam2019.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpeningActivity extends AppCompatActivity {

    @BindView(R.id.openingLogoId)
    public ImageView openingLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        ButterKnife.bind(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentGoConnection = new Intent(OpeningActivity.this, ConnectionActivity.class);
                startActivity(intentGoConnection);
                finish();
            }
        }, 1500);
    }
}

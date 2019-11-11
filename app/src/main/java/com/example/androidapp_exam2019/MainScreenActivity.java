package com.example.androidapp_exam2019;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainScreenActivity extends AppCompatActivity {

    @BindView(R.id.mainScreenBottomNavigationViewId) public BottomNavigationView mainScreenBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        ButterKnife.bind(this);

        /*mainScreenBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigationProfileId :
                        Intent intent = new Intent(MainScreenActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;
                }

                return false;
            }
        });*/
    }
}

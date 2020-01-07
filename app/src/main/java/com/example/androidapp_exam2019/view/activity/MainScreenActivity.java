package com.example.androidapp_exam2019.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.androidapp_exam2019.viewModel.DressViewModel;
import com.example.androidapp_exam2019.R;
import com.example.androidapp_exam2019.view.fragment.BrowseFragment;
import com.example.androidapp_exam2019.view.fragment.CardFragment;
import com.example.androidapp_exam2019.view.fragment.FavoritesFragment;
import com.example.androidapp_exam2019.view.fragment.HomeFragment;
import com.example.androidapp_exam2019.view.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainScreenActivity extends AppCompatActivity {

    @BindView(R.id.mainScreenBottomNavigationViewId) public BottomNavigationView mainScreenBottomNavigationView;
    private DressViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        ButterKnife.bind(this);

        model = ViewModelProviders.of(this).get(DressViewModel.class);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerId, new HomeFragment()).commit();

        mainScreenBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigationMainScreenId :
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navigationBrowseId :
                        selectedFragment = new BrowseFragment();
                        break;
                    case R.id.navigationFavoritesId :
                        selectedFragment = new FavoritesFragment();
                        break;
                    case R.id.navigationCardId :
                        selectedFragment = new CardFragment();
                        break;
                    case R.id.navigationProfileId :
                        selectedFragment = new ProfileFragment();
                        //selectedFragment = new ArticleFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerId, selectedFragment).commit();
                return true;
            }
        });
    }
}

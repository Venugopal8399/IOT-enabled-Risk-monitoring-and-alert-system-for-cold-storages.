package com.example.gague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bnv;
    private HomeFragment hf = new HomeFragment();
    private UserFragment uf = new UserFragment();
    private AlertFragment af = new AlertFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnv= findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.Main_main_activiy,hf).commit();

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Main_main_activiy,hf).commit();
                        return true;
                    case R.id.alert_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Main_main_activiy,af).commit();
                        return true;
                    case R.id.user_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Main_main_activiy,uf).commit();
                        return true;

                }

                return false;
            }
        });

    }
}
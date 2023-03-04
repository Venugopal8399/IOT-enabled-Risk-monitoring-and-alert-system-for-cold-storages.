package com.example.gague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class admin_main extends AppCompatActivity {
    private BottomNavigationView abnv;
    private AdminHomeFragment ahf = new AdminHomeFragment();
    private AdminUserFragment auf = new AdminUserFragment();
    private AdminAlertFragment aaf = new AdminAlertFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        abnv =(BottomNavigationView)findViewById(R.id.admin_bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_activiy,ahf).commit();

        abnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.admin_home_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_activiy,ahf).commit();
                        return true;
                    case R.id.admin_alert_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_activiy,aaf).commit();
                        return true;
                    case R.id.admin_user_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.admin_main_activiy,auf).commit();
                        return true;

                }

                return false;
            }
        });

    }
}
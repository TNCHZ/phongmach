package com.example.jpyou.ui.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.jpyou.ui.view.adapter.ViewPagerAdapterAdmin;
import com.example.jpyou.ui.view.adapter.ViewPagerAdapterNurse;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminInterface extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences, sharedPreferencesNight;
    SharedPreferences.Editor editor;
    private String userID;
    private Boolean isNightMode = false, checkLogout = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_interface);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getString("TaiKhoanID", null);
        checkLogout = getIntent().getBooleanExtra("TaiKhoan", false);
        if (userID == null || checkLogout) {
            editor.putString("TaiKhoanID", getIntent().getStringExtra("TaiKhoanID"));
            editor.apply();
            checkLogout = false;
        } else {
            editor.putString("TaiKhoanID", userID);
            editor.apply();
        }
        sharedPreferencesNight = getSharedPreferences("NightMode", MODE_PRIVATE);
        isNightMode = sharedPreferencesNight.getBoolean("night", false);
        if (isNightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            isNightMode = false;
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        viewPager = findViewById(R.id.viewPager_AdminInterface);
        bottomNavigationView = findViewById(R.id.bottomNavigation_AdminInterface);
        ViewPagerAdapterAdmin adapterNurse = new ViewPagerAdapterAdmin(this);
        viewPager.setAdapter(adapterNurse);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navHome_Admin) {
                    viewPager.setCurrentItem(0);
                } else if (id == R.id.navSetting_Admin) {
                    viewPager.setCurrentItem(1);
                }
                return true;
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.navSetting_Admin).setChecked(true);
                        break;
                    default:
                        bottomNavigationView.getMenu().findItem(R.id.navHome_Admin).setChecked(true);
                        break;
                }
            }
        });

    }
}

package com.example.jpyou.ui.view.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.jpyou.ui.view.adapter.ViewPagerAdapterUser;
import com.example.jpyou.ui.viewmodel.SharedViewModel;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UserInterface extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences, sharedPreferencesNight;
    SharedPreferences.Editor editor;
    private String userID;
    private Boolean isNightMode = false, checkLogout = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_interface);
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
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            isNightMode = false;
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setUpBottomNavigation();
    }

    private void setUpBottomNavigation() {
        viewPager = findViewById(R.id.viewPager_UserInterface);
        bottomNavigationView = findViewById(R.id.bottomNavigation_UserInterface);
        ViewPagerAdapterUser adapterUser = new ViewPagerAdapterUser(this);
        viewPager.setAdapter(adapterUser);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navHome_User) {
                    viewPager.setCurrentItem(0);
                } else if (id == R.id.navSchedule_User) {
                    viewPager.setCurrentItem(1);
                } else if (id == R.id.navProfile_User) {
                    viewPager.setCurrentItem(2);
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
                        bottomNavigationView.getMenu().findItem(R.id.navSchedule_User).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.navProfile_User).setChecked(true);
                        break;
                    default:
                        bottomNavigationView.getMenu().findItem(R.id.navHome_User).setChecked(true);
                        break;
                }
            }
        });
    }


}
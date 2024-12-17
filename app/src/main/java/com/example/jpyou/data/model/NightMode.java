package com.example.jpyou.data.model;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


public class NightMode extends AppCompatActivity {
    SharedPreferences sharedPreferences, sharedPreferencesNight;
    SharedPreferences.Editor editor;
    private String userID;
    private Boolean isNightMode = false, checkLogout = false;

    public void setIsNightMode(Intent intent) {
        sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getString("TaiKhoanID", null);
        checkLogout = intent.getBooleanExtra("TaiKhoan", false);
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
    }
}

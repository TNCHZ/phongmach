package com.example.jpyou.data.model;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;


public class NightMode {
    private boolean isNightMode;
    private SharedPreferences sharedPreferences;

    public NightMode(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void nightMode() {
        isNightMode = sharedPreferences.getBoolean("night", false);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}

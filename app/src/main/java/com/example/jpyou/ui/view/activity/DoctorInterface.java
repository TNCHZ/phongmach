package com.example.jpyou.ui.view.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.jpyou.data.model.NightMode;
import com.example.jpyou.ui.view.adapter.ViewPagerAdapterDoctor;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DoctorInterface extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String userID;

    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.doctor_interface);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);
        editor = sharedPreferences.edit();
//        userID = sharedPreferences.getString("TaiKhoanID", null);
//        if(userID != null){
//            editor.putString("TaiKhoanID", userID);
//            editor.apply();
//        }else {
            editor.putString("TaiKhoanID", getIntent().getStringExtra("TaiKhoanID"));
            editor.apply();
//        }

        viewPager = findViewById(R.id.viewPager_DoctorInterface);
        bottomNavigationView = findViewById(R.id.bottomNavigation_DoctorInterface);
        ViewPagerAdapterDoctor adapterDoctor = new ViewPagerAdapterDoctor(this);
        viewPager.setAdapter(adapterDoctor);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navHome_Doctor) {
                    viewPager.setCurrentItem(0);
                } else if (id == R.id.navSchedule_Doctor) {
                    viewPager.setCurrentItem(1);
                } else if (id == R.id.navProfile_Doctor) {
                    viewPager.setCurrentItem(2);
                }
                return true;
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.navSchedule_Doctor).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.navProfile_Doctor).setChecked(true);
                        break;
                    default:
                        bottomNavigationView.getMenu().findItem(R.id.navHome_Doctor).setChecked(true);
                        break;
                }
            }
        });
    }
}
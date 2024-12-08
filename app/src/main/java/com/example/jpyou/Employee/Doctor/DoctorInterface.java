package com.example.jpyou.Employee.Doctor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.jpyou.Employee.Doctor.DoctorFragments.ViewPagerAdapterDoctor;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DoctorInterface extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    private TextView txtHeader;

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
        SharedPreferences sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TaiKhoanID", getIntent().getStringExtra("TaiKhoanID"));
        editor.apply();

        txtHeader = findViewById(R.id.textViewHeader_DoctorInterface);
        txtHeader.setText("Trang chủ");

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
                txtHeader.setText(item.getTitle());
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
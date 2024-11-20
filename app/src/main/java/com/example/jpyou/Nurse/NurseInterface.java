package com.example.jpyou.Nurse;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.jpyou.Nurse.NurseFragments.ViewPagerAdapterNurse;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NurseInterface extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationViewn;
    private TextView txtHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.nurse_interface);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtHeader =findViewById(R.id.textViewHearder_NurseInterface);
        txtHeader.setText("Danh sách Bệnh nhân");

        viewPager = findViewById(R.id.viewPager_NurseInterface);
        bottomNavigationViewn = findViewById(R.id.bottomNavigation_NurseInterface);
        ViewPagerAdapterNurse adapterNurse = new ViewPagerAdapterNurse(this);
        viewPager.setAdapter(adapterNurse);

        bottomNavigationViewn.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id==R.id.navListParient_Nurse){
                    viewPager.setCurrentItem(0);
                } else if (id == R.id.navProfile_Nurse) {
                    viewPager.setCurrentItem(1);
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
                        bottomNavigationViewn.getMenu().findItem(R.id.navListParient_Nurse).setChecked(true);
                        break;
                    default:
                        bottomNavigationViewn.getMenu().findItem(R.id.navProfile_Nurse).setChecked(true);
                        break;
                }
            }
        });
    }
}
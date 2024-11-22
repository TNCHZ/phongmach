package com.example.jpyou.User;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.jpyou.ProfileFragment;
import com.example.jpyou.User.UserFragments.ViewPagerAdapterUser;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UserInterface extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    private TextView txtHeader;
    private ImageButton imgbtnAvatarUserInterface;

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

        txtHeader = findViewById(R.id.textViewHeader_UserInterface);
        txtHeader.setText("Trang chủ");

        SharedPreferences sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TaiKhoanID", getIntent().getStringExtra("TaiKhoanID"));
        editor.apply();

        setUpBottomNavigation();



    }

    private void setUpBottomNavigation() {
        viewPager = findViewById(R.id.viewPager_UserInterface);
        bottomNavigationView = findViewById(R.id.bottomNavigation_UserInterface);
        ViewPagerAdapterUser adapterUser = new ViewPagerAdapterUser(this);
        viewPager.setAdapter(adapterUser);

        imgbtnAvatarUserInterface = findViewById(R.id.imgbtnAvatar_UserInterface);
        imgbtnAvatarUserInterface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigationView.getMenu().findItem(R.id.navHome_User).setChecked(true);
                viewPager.setCurrentItem(2);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navHome_User){
                    viewPager.setCurrentItem(0);
                } else if (id == R.id.navSchedule_User) {
                    viewPager.setCurrentItem(1);
                } else if (id == R.id.navProfile_User) {
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
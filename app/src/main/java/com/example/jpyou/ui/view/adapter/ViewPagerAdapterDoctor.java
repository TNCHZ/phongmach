package com.example.jpyou.ui.view.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jpyou.ui.view.fragment.ProfileFragment;
import com.example.jpyou.ui.view.fragment.HomeFragment;
import com.example.jpyou.ui.view.fragment.ScheduleDoctorFragment;

public class ViewPagerAdapterDoctor extends FragmentStateAdapter {

    private String userID;

    // Constructor nhận userID
    public ViewPagerAdapterDoctor(@NonNull FragmentActivity fragmentActivity, String userID) {
        super(fragmentActivity);
        this.userID = userID;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;

        // Tạo các Fragment và truyền dữ liệu vào Bundle
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new ScheduleDoctorFragment();
                break;
            case 2:
                fragment = new ProfileFragment();
                break;
            default:
                fragment = new HomeFragment();
                break;
        }

        // Tạo Bundle và truyền userID vào fragment
        Bundle bundle = new Bundle();
        bundle.putString("userID", userID);  // Truyền userID vào Bundle
        fragment.setArguments(bundle);  // Gắn Bundle vào Fragment

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3; // Số lượng Fragment
    }
}

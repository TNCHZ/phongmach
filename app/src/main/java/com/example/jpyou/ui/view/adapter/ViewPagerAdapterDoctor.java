package com.example.jpyou.ui.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jpyou.ui.view.fragment.ProfileFragment;
import com.example.jpyou.ui.view.fragment.HomeFragment;
import com.example.jpyou.ui.view.fragment.ScheduleDoctorFragment;

public class ViewPagerAdapterDoctor extends FragmentStateAdapter {
    public ViewPagerAdapterDoctor(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new HomeFragment();
            case 1: return new ScheduleDoctorFragment();
            case 2: return new ProfileFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() { return 3; }
}
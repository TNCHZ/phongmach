package com.example.jpyou.Employee.Doctor.DoctorFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jpyou.Fragments.ProfileFragment;
import com.example.jpyou.Fragments.HomeFragment;

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

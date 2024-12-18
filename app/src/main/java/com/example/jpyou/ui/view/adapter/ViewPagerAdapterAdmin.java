package com.example.jpyou.ui.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jpyou.ui.view.fragment.HomeAdminFragment;
import com.example.jpyou.ui.view.fragment.ProfileFragment;


public class ViewPagerAdapterAdmin extends FragmentStateAdapter {
    public ViewPagerAdapterAdmin(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new HomeAdminFragment();
            case 1: return new ProfileFragment();
            default: return new HomeAdminFragment();
        }
    }

    @Override
    public int getItemCount() {return 2; }
}

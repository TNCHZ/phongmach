package com.example.jpyou.User.UserFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jpyou.ProfileFragment;

public class ViewPagerAdapterUser extends FragmentStateAdapter {
    public ViewPagerAdapterUser(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new HomeUserFragment();
            case 1: return new ScheduleUserFragment();
            case 2: return new ProfileFragment();
            default: return new HomeUserFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

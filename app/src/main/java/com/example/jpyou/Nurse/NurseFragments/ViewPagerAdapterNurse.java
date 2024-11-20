package com.example.jpyou.Nurse.NurseFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jpyou.ProfileFragment;

public class ViewPagerAdapterNurse extends FragmentStateAdapter {
    public ViewPagerAdapterNurse(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new ConfirmPatientNurseFragment();
            case 1: return new CancelPatientNurseFragment();
            case 2: return new ProfileFragment();
            default: return new ConfirmPatientNurseFragment();
        }
    }

    @Override
    public int getItemCount() { return 3; }
}

package com.example.jpyou.User.UserFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jpyou.MyDatabaseHelper;
import com.example.myapplication.R;

public class HomeUserFragment extends Fragment {
    private String userID;
    private MyDatabaseHelper db;
    private Button btnRegisTreatMent, btnRegisVaccinate;

    public HomeUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("TaiKhoanID", null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        {
            btnRegisTreatMent = view.findViewById(R.id.btnTreatment_FragmentUserHome);
            btnRegisTreatMent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    RegisterForExaminationUserFragment anotherFragment = new RegisterForExaminationUserFragment();
                    transaction.replace(R.id.fragment_container_home, anotherFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

            btnRegisVaccinate = view.findViewById(R.id.btnVaccination_FragmentUserHome);
            btnRegisVaccinate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    RegisterForVaccinateUserFragment anotherFragment = new RegisterForVaccinateUserFragment();
                    transaction.replace(R.id.fragment_container_home, anotherFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }
        return view;
    }
}
package com.example.jpyou.ui.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;


public class HomeAdminFragment extends Fragment {
    private Button btnAdd, btnList, btnStatic;

    public HomeAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
        {
            btnAdd = view.findViewById(R.id.btnCreateEmployee_HomeAdminFragment);
            btnList = view.findViewById(R.id.btnListNguoiDung_HomeAdminFragment);
            btnStatic = view.findViewById(R.id.btnPatientStatistics_HomeAdminFragment);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    AddEmployeeAdminFragment anotherFragment = new AddEmployeeAdminFragment();
                    transaction.replace(R.id.homeAdminFragment, anotherFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

            btnList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    ListUserAdminFragment anotherFragment = new ListUserAdminFragment();
                    transaction.replace(R.id.homeAdminFragment, anotherFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

            btnStatic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    ChartAdminFragment anotherFragment = new ChartAdminFragment();
                    transaction.replace(R.id.homeAdminFragment, anotherFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }
        return view;
    }
}
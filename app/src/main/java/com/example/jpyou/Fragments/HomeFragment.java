package com.example.jpyou.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.jpyou.MyDatabaseHelper;
import com.example.jpyou.User.UserFragments.RegisterForExaminationUserFragment;
import com.example.jpyou.SignIn;
import com.example.myapplication.R;

public class HomeFragment extends Fragment {
    private String userID;
    private MyDatabaseHelper db;
    private Button btnRegisTreatMent, btnChooseDoctor, btnResults;
    private TableLayout layout;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("TaiKhoanID", null);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        {
            Intent intent = new Intent(getActivity(), SignIn.class);
            btnRegisTreatMent = view.findViewById(R.id.btnTreatment_HomeFragment);
            btnChooseDoctor = view.findViewById(R.id.btnChooseDoctor_HomeFragment);
            btnResults = view.findViewById(R.id.btnResults_HomeFragment);
            layout = view.findViewById(R.id.table_HomeFragment);
            if (userID == null) {
                layout.setVisibility(View.GONE);
            } else
                //if (db.getRole(userID).equals("Benh nhan"))
            {
                layout.setVisibility(View.VISIBLE);
                btnRegisTreatMent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (userID == null){
                            startActivity(intent);
                        } else {
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            RegisterForExaminationUserFragment anotherFragment = new RegisterForExaminationUserFragment();
                            transaction.replace(R.id.fragment_home, anotherFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    }
                });
            }



        }
        return view;
    }
}
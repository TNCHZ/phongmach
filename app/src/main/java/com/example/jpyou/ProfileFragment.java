package com.example.jpyou;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jpyou.User.UserInterface;
import com.example.jpyou.User.UserUpdateInformation;
import com.example.myapplication.R;


public class ProfileFragment extends Fragment {
    private String userID;
    private MyDatabaseHelper db;

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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        db = new MyDatabaseHelper(getActivity());
        Button btnUpdateInformation = view.findViewById(R.id.btnUpdateInformation_FragmentProfile);
        Button btnChangePassword = view.findViewById(R.id.btnChangePassword_FragmentProfile);
        Button btnLogOut = view.findViewById(R.id.btnLogOut_FragmentProfile);

        btnUpdateInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserUpdateInformation.class);
                intent.putExtra("Person", db.getInformation(userID));
                startActivity(intent);
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserUpdateInformation.class);
                intent.putExtra("NamePerson", userID);
                startActivity(intent);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserInterface.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
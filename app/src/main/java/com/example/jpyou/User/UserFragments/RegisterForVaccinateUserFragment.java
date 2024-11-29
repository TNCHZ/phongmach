package com.example.jpyou.User.UserFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.jpyou.MyDatabaseHelper;
import com.example.myapplication.R;

public class RegisterForVaccinateUserFragment extends Fragment {
    private ImageButton imgMosquito;
    private String userID;
    private MyDatabaseHelper db;
    private EditText txtDate;

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
        View view = inflater.inflate(R.layout.fragment_user_register_vaccinate, container, false);
        txtDate = view.findViewById(R.id.txtDate_RegisterForVaccinateUserFragment);
        imgMosquito = view.findViewById(R.id.imageMosquito_RegisterForVaccinateUserFragment);
        imgMosquito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Sốt xuất huyết", txtDate.getText().toString());
            }
        });


        return view;
    }


    private void showDialog(String symptom, String day) {
        // Tạo AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Thiết lập tiêu đề, thông báo và các nút
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn xác nhận đặt lịch tiêm chủng");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        // Nút "Có"
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db = new MyDatabaseHelper(getActivity());
                db.registerVaccinate(db.getPatientID(userID), day, symptom);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.ui.view.activity.UserInterface;
import com.example.jpyou.ui.view.activity.SignIn;
import com.example.myapplication.R;


public class ProfileFragment extends Fragment {
    private String userID;
    private MyDatabaseHelper db;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
//        userID = sharedPreferences.getString("TaiKhoanID", null);
    }

    private Switch swicthNightMode;
    private Boolean isNightMode = false;
    SharedPreferences sharedPreferences, sharedPreferencesNight;
    SharedPreferences.Editor editor, editorNight;
    private Button btnUpdateInformation, btnChangePassword, btnLogOut, btnLogIn, btnComment, btnInform, btnHotline;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        {
            sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
            userID = sharedPreferences.getString("TaiKhoanID", null);

            sharedPreferencesNight = requireActivity().getSharedPreferences("NightMode", Context.MODE_PRIVATE);
            isNightMode = sharedPreferencesNight.getBoolean("night", false);
            swicthNightMode = view.findViewById(R.id.switchNightMode_ProfileFragment);
            if (isNightMode) {
                swicthNightMode.setText("Tắt chế độ ban đêm");
                swicthNightMode.setChecked(true);
                isNightMode = false;
            }

            swicthNightMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isNightMode = swicthNightMode.isChecked();
                    editorNight = sharedPreferencesNight.edit();
                    if (isNightMode) {
                        swicthNightMode.setText("Tắt chế độ ban đêm");
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        swicthNightMode.setChecked(true);
                    } else {
                        swicthNightMode.setText("Bật chế độ ban đêm");
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        swicthNightMode.setChecked(false);
                    }
                    editorNight.putBoolean("night", isNightMode);
                    editorNight.apply();
                }
            });

//            sBFontSize = view.findViewById(R.id.seekBarFontSize_ProfileFragment);
//            sBFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                    switch (i){
//                        case 1:
//                            fontSize = i + 17;
//                            break;
//                        case 2:
//                            fontSize = i + 19;
//                            break;
//                        default:
//                            fontSize = i + 15;
//                            break;
//                    }
//                }
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {}
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {}
//            });

            btnInform = view.findViewById(R.id.buttonInform_ProfileFragment);
            btnHotline = view.findViewById(R.id.buttonHotline_ProfileFragment);
            btnUpdateInformation = view.findViewById(R.id.btnUpdateInformation_ProfileFragment);
            btnChangePassword = view.findViewById(R.id.btnChangePassword_ProfileFragment);
            swicthNightMode = view.findViewById(R.id.switchNightMode_ProfileFragment);
            btnLogOut = view.findViewById(R.id.btnLogOut_FragmentProfile);
            btnLogIn = view.findViewById(R.id.btnLogIn_ProfileFragment);
            btnComment = view.findViewById(R.id.btnCMTDoctor_ProfileFragment);
            btnComment.setVisibility(View.GONE);
            db = new MyDatabaseHelper(getActivity());



            if (userID == null) {
                btnLogOut.setVisibility(View.INVISIBLE);
                btnLogIn.setVisibility(View.VISIBLE);
                btnUpdateInformation.setVisibility(View.GONE);
                btnChangePassword.setVisibility(View.GONE);
                btnHotline.setVisibility(View.VISIBLE);
                btnInform.setVisibility(View.VISIBLE);
                btnLogIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SignIn.class);
                        startActivity(intent);
                    }
                });
            } else {
                if (db.getRole(userID).equals("Admin")) {
                    btnUpdateInformation.setVisibility(View.GONE);
                    btnChangePassword.setVisibility(View.VISIBLE);
                    btnLogIn.setVisibility(View.GONE);
                    btnLogOut.setVisibility(View.VISIBLE);
                    btnHotline.setVisibility(View.GONE);
                    btnInform.setVisibility(View.GONE);
                } else {
                    btnHotline.setVisibility(View.VISIBLE);
                    btnInform.setVisibility(View.VISIBLE);
                    btnLogIn.setVisibility(View.INVISIBLE);
                    btnLogOut.setVisibility(View.VISIBLE);
                    btnUpdateInformation.setVisibility(View.VISIBLE);
                    btnChangePassword.setVisibility(View.VISIBLE);
                    if (db.getRole(userID).equals("Benh nhan"))
                        btnComment.setVisibility(View.VISIBLE);

                    btnComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();

                            CommentDoctorFragment anotherFragment = new CommentDoctorFragment(userID);
                            transaction.replace(R.id.fragment_profile, anotherFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });

                    btnUpdateInformation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();

                            UpdateInformationFragment anotherFragment = new UpdateInformationFragment(userID);
                            transaction.replace(R.id.fragment_profile, anotherFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
                }

                btnChangePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();

                        ChangePasswordFragment anotherFragment = new ChangePasswordFragment(userID);
                        transaction.replace(R.id.fragment_profile, anotherFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }
                });

                btnLogOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentLogout = new Intent(getActivity(), UserInterface.class);
                        boolean check = true;
                        intentLogout.putExtra("TaiKhoan", check);
                        Toast.makeText(getActivity().getApplicationContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                        startActivity(intentLogout);
                    }
                });
            }
        }
        return view;
    }
}
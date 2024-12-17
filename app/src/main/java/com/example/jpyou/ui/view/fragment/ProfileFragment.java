package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.UpdateInformation;
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
    private Button btnUpdateInformation, btnChangePassword, btnLogOut, btnLogIn;


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
//            textView = view.findViewById(R.id.textViewFontSize_ProfileFragment);
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
//                    sharedPreferences = view.getContext().getSharedPreferences("FONT", Context.MODE_PRIVATE);
//                    editor = sharedPreferences.edit();
//                    editor.putInt("fontText", i);
//                    editor.commit();
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {
//
//                }
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {
//                    textView.setTextSize(fontSize);
//                }
//            });

            swicthNightMode = view.findViewById(R.id.switchNightMode_ProfileFragment);
            btnLogOut = view.findViewById(R.id.btnLogOut_FragmentProfile);
            btnLogIn = view.findViewById(R.id.btnLogIn_ProfileFragment);
            if (userID == null) {
                btnLogOut.setVisibility(View.INVISIBLE);
                btnLogIn.setVisibility(View.VISIBLE);

                btnLogIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SignIn.class);
                        startActivity(intent);
                    }
                });
            } else {
                btnLogIn.setVisibility(View.INVISIBLE);
                btnLogOut.setVisibility(View.VISIBLE);

                db = new MyDatabaseHelper(getActivity());
                btnUpdateInformation = view.findViewById(R.id.btnUpdateInformation_ProfileFragment);
                btnChangePassword = view.findViewById(R.id.btnChangePassword_ProfileFragment);


                btnUpdateInformation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), UpdateInformation.class);
                        intent.putExtra("PersonInformation", db.getInformation(userID));
                        startActivity(intent);
                    }
                });

                btnChangePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ChangePasswordFragment.class);
                        intent.putExtra("NamePerson", userID);
                        startActivity(intent);
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
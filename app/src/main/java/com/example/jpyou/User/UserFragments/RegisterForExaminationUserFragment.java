package com.example.jpyou.User.UserFragments;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.jpyou.BottomNavigator.HomeFragment;
import com.example.jpyou.MyDatabaseHelper;
import com.example.jpyou.Model.PersonInformation;
import com.example.myapplication.R;


public class RegisterForExaminationUserFragment extends Fragment {
    private PersonInformation ps;
    private String userID;
    private EditText txtName, txtDayOfBirth, txtPhone, txtEmail, txtDayRegis;
    private MyDatabaseHelper db;
    private RadioButton rdMale, rdFemale;
    private Button btnRegis;
    private EditText txtDate, txtSymptom;


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
        View view = inflater.inflate(R.layout.fragment_user_register_for_examination, container, false);
        init(view);

        txtDate = view.findViewById(R.id.txtDate_RegisterForExaminonUserFragment);
        txtSymptom = view.findViewById(R.id.txtSymptom_RegisterForExaminonUserFragment);

        btnRegis = view.findViewById(R.id.btnUpdate_RegisterForExaminonUserFragment);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtDate.getText().toString().isEmpty() && !txtSymptom.getText().toString().isEmpty()) {
                    db.registerExamination(db.getPatientID(userID), txtDayRegis.getText().toString(), txtSymptom.getText().toString());
                }
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                @SuppressLint("CommitTransaction") FragmentTransaction transaction = fragmentManager.beginTransaction();
                HomeFragment anotherFragment = new HomeFragment();
                transaction.replace(R.id.fragment_home, anotherFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    private void init(View view) {
        ps = new PersonInformation();
        db = new MyDatabaseHelper(getActivity());
        ps = db.getInformation(userID);
        txtName = view.findViewById(R.id.txtName_RegisterForExaminonUserFragment);
        txtDayOfBirth = view.findViewById(R.id.txtDayOfBirth_RegisterForExaminonUserFragment);
        txtPhone = view.findViewById(R.id.txtPhone_RegisterForExaminonUserFragment);
        txtEmail = view.findViewById(R.id.txtEmail_RegisterForExaminonUserFragment);
        rdMale = view.findViewById(R.id.rdMale_RegisterForExaminonUserFragment);
        rdFemale = view.findViewById(R.id.rdFemale_RegisterForExaminonUserFragment);
        txtDayRegis = view.findViewById(R.id.txtDate_RegisterForExaminonUserFragment);

        txtName.setText(ps.getHoTen());
        txtDayOfBirth.setText(ps.getNgaySinh());
        txtEmail.setText(ps.getEmail());
        txtPhone.setText(ps.getSoDT());

        if (ps.getGioiTinh().equals("Nam")) {
            rdMale.setChecked(true);
        } else {
            rdFemale.setChecked(true);
        }
    }


}
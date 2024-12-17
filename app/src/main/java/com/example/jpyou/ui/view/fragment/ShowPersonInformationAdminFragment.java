package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.PersonInformation;
import com.example.myapplication.R;

public class ShowPersonInformationAdminFragment extends Fragment {
    private EditText txtName, txtDayOfBirth, txtPhone, txtEmail, txtGender;
    private String patientID;
    private Button btnStop, btnBack, btnOpen;
    private PersonInformation ps;
    private MyDatabaseHelper db;

    public ShowPersonInformationAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_show_person_information, container, false);
        {
            txtName = view.findViewById(R.id.txtName_ShowPersonInformationAdminFragment);
            txtDayOfBirth = view.findViewById(R.id.txtDayOfBirth_ShowPersonInformationAdminFragment);
            txtPhone = view.findViewById(R.id.txtPhone_ShowPersonInformationAdminFragment);
            txtEmail = view.findViewById(R.id.txtEmail_ShowPersonInformationAdminFragment);
            txtGender = view.findViewById(R.id.txtGender_ShowPersonInformationAdminFragment);
            btnStop = view.findViewById(R.id.btnStop_ShowPersonInformationAdminFragment);
            btnBack = view.findViewById(R.id.buttonBack_ShowPersonInformationAdminFragment);
            btnOpen = view.findViewById(R.id.btnOpen_ShowPersonInformationAdminFragment);


            db = new MyDatabaseHelper(getActivity());

            Bundle bundle = getArguments();
            if (bundle != null) {
                patientID = bundle.getString("patient_id", "-1");  // Giá trị mặc định là "-1" nếu không tìm thấy
                ps = db.getInformation(patientID);
                txtName.setText(ps.getHoTen());
                txtPhone.setText(ps.getSoDT());
                txtGender.setText(ps.getGioiTinh());
                txtDayOfBirth.setText(ps.getNgaySinh());
                txtEmail.setText(ps.getEmail());
            }
            if (db.isAccountActive(patientID)) {
                btnOpen.setVisibility(View.GONE);
                btnStop.setVisibility(View.VISIBLE);
                btnStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (db.lockAccount(patientID)) {
                            Toast.makeText(getActivity(), "Dừng hoạt động tài khoản thành công", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_admin_show_person_information, new ListUserAdminFragment())
                                    .commit();
                        } else
                            Toast.makeText(getActivity(), "Dừng hoạt động tài khoản thất bại", Toast.LENGTH_SHORT).show();

                    }
                });
            } else {
                btnStop.setVisibility(View.GONE);
                btnOpen.setVisibility(View.VISIBLE);
                btnOpen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (db.unlockAccount(patientID)) {
                            Toast.makeText(getActivity(), "Mở khóa hoạt động tài khoản thành công", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_admin_show_person_information, new ListUserAdminFragment())
                                    .commit();
                        } else
                            Toast.makeText(getActivity(), "Mở khóa hoạt động tài khoản thất bại", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_admin_show_person_information, new ListUserAdminFragment())
                            .commit();
                }
            });
        }
        return view;
    }
}
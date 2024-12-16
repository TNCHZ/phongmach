package com.example.jpyou.ui.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.myapplication.R;

import java.util.ArrayList;


public class AddEmployeeAdminFragment extends Fragment {
    private EditText txtName, txtDayOfBirth, txtPhone, txtEmail, txtExp, txtPassword;
    private String gender, selectedWho, selectedRole, exp;
    private RadioButton male;
    private Spinner spnWho, spnRole;
    private Button btnBack, btnConfirm;
    private TextView tv;
    private MyDatabaseHelper db;

    public AddEmployeeAdminFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_add_employee, container, false);
        txtName = view.findViewById(R.id.txtName_AddEmployeeAdminFragment);
        txtPassword = view.findViewById(R.id.txtPassword_AddEmployeeAdminFragment);
        txtDayOfBirth = view.findViewById(R.id.txtDayOfBirth_AddEmployeeAdminFragment);
        txtPhone = view.findViewById(R.id.txtPhone_AddEmployeeAdminFragment);
        txtEmail = view.findViewById(R.id.txtEmail_AddEmployeeAdminFragment);
        txtExp = view.findViewById(R.id.txtExp_AddEmployeeAdminFragment);
        spnRole = view.findViewById(R.id.spinnerRole_AddEmployeeAdminFragment);
        spnWho = view.findViewById(R.id.spinnerWho_AddEmployeeAdminFragment);
        btnBack = view.findViewById(R.id.buttonBack_AddEmployeeAdminFragment);
        btnConfirm = view.findViewById(R.id.btnRegis_AddEmployeeAdminFragment);
        tv = view.findViewById(R.id.textViewRole_AddEmployeeAdminFragment);
        male = view.findViewById(R.id.rdMale_AddEmployeeAdminFragment);
        gender = male.isChecked() ? "Nam" : "nữ";
        db = new MyDatabaseHelper(getActivity());

        spnRole.setVisibility(View.GONE);
        tv.setVisibility(View.GONE);
        txtExp.setVisibility(View.GONE);
        exp = "";

        ArrayList<String> roleList = new ArrayList<>();
        roleList.add("Bác sĩ");
        roleList.add("Admin");
        roleList.add("Y tá");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item, // Layout hiển thị item
                roleList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Layout cho dropdown
        spnWho.setAdapter(adapter);

        spnWho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedWho = parent.getItemAtPosition(position).toString();
                if (selectedWho.equals("Admin")) {
                    txtExp.setVisibility(View.GONE);
                    spnRole.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.VISIBLE);
                    ArrayList<String> rl = new ArrayList<>();
                    rl.add("Quản lý đăng nhập");
                    rl.add("Báo cáo thống kê");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            getContext(),
                            android.R.layout.simple_spinner_item, // Layout hiển thị item
                            rl
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Layout cho dropdown
                    spnRole.setAdapter(adapter);
                    spnRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedRole = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            selectedRole = parent.getItemAtPosition(0).toString();
                        }
                    });
                } else if (selectedWho.equals("Bác sĩ")) {
                    txtExp.setVisibility(View.VISIBLE);
                    exp = txtExp.getText().toString();
                } else {
                    spnRole.setVisibility(View.GONE);
                    tv.setVisibility(View.GONE);
                    txtExp.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedWho = parent.getItemAtPosition(0).toString();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                String dayOfBirth = txtDayOfBirth.getText().toString();
                String phone = txtPhone.getText().toString();
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                if (name.isEmpty() || dayOfBirth.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.addUser(phone, password, name, gender, dayOfBirth, phone, email, exp, selectedWho, selectedRole)) {
                        Toast.makeText(getActivity(), "Thêm tài khoản thành công", Toast.LENGTH_SHORT).show();
                        txtName.setText("");
                        txtPassword.setText("");
                        txtDayOfBirth.setText("");
                        txtPhone.setText("");
                        txtEmail.setText("");
                        txtExp.setText("");
                    } else
                        Toast.makeText(getActivity(), "Thêm tài khoản thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}
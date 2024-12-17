package com.example.jpyou.ui.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.jpyou.UpdateInformation;
import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.PersonInformation;
import com.example.jpyou.ui.view.activity.UserInterface;
import com.example.myapplication.R;


public class UpdateInformationFragment extends Fragment {
    private PersonInformation ps;
    private EditText txtName, txtDayOfBirth, txtPhone, txtEmail;
    private MyDatabaseHelper db;
    private RadioButton rdMale, rdFemale;
    private Button btnUpdate;
    private String userID;

    public UpdateInformationFragment(String userID) {
        this.userID = userID;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_update_information, container, false);
        {
            db = new MyDatabaseHelper(getActivity());
            ps = db.getInformation(userID);
            txtName = view.findViewById(R.id.txtName_UpdateInformationFragment);
            txtDayOfBirth = view.findViewById(R.id.txtDayOfBirth_UpdateInformationFragment);
            txtPhone = view.findViewById(R.id.txtPhone_UpdateInformationFragment);
            txtEmail = view.findViewById(R.id.txtEmail_UpdateInformationFragment);
            rdMale = view.findViewById(R.id.rdMale_UpdateInformationFragment);
            rdFemale = view.findViewById(R.id.rdFemale_UpdateInformationFragment);

            txtName.setText(ps.getHoTen());
            txtDayOfBirth.setText(ps.getNgaySinh());
            txtPhone.setText(ps.getSoDT());
            txtEmail.setText(ps.getEmail());

            if (ps.getGioiTinh().equals("Nam")) {
                rdMale.setChecked(true);
            } else {
                rdFemale.setChecked(true);
            }


            btnUpdate = view.findViewById(R.id.btnUpdate_UpdateInformationFragment);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = ps.getId();
                    String name = txtName.getText().toString();
                    String gender = rdMale.isChecked() ? "Nam" : "Nữ";
                    String dayOfBirth = txtDayOfBirth.getText().toString();
                    String phone = txtPhone.getText().toString();
                    String email = txtEmail.getText().toString();

                    db.updateInformation(id, name, gender, dayOfBirth, phone, email);
                    Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }
        return view;
    }
}
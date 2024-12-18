package com.example.jpyou.ui.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.myapplication.R;

public class ChangePasswordFragment extends Fragment {

    private String userID;
    private EditText txtOldPass, txtNewPass, txtNewPassConfirm;
    private String oldPass, newPass, newPassConfirm;
    private Button btn;
    private MyDatabaseHelper db;

    public ChangePasswordFragment(String id) {
        userID = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        {
            txtNewPass = view.findViewById(R.id.txtNewPassword_ChangePasswordFragment);
            txtNewPassConfirm = view.findViewById(R.id.txtConfirmPassword2_ChangePasswordFragment);
            txtOldPass = view.findViewById(R.id.txtOldPassword_ChangePasswordFragment);
            db = new MyDatabaseHelper(getActivity());
            btn = view.findViewById(R.id.btnUpdatePassword_ChangePasswordFragment);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    oldPass = txtOldPass.getText().toString();
                    newPass = txtNewPass.getText().toString();
                    newPassConfirm = txtNewPassConfirm.getText().toString();
                    if (newPass.equals(newPassConfirm)) {
                        if (db.changePassword(userID, newPass, oldPass))
                            Toast.makeText(getActivity(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                    }
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }
        return view;
    }
}
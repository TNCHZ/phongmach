package com.example.jpyou;

import android.widget.EditText;
import android.widget.TextView;

public class WarningAccount {
    private EditText txtUserName;
    private EditText txtPassword;
    private TextView txtWarning;
    private String taiKhoanID;

    private Boolean check = false;

    public WarningAccount(EditText txtUserName, EditText txtPassword, TextView txtWarning) {
        this.txtUserName = txtUserName;
        this.txtPassword = txtPassword;
        this.txtWarning = txtWarning;
        this.taiKhoanID = "0";
        this.check = false;
    }

    public WarningAccount(EditText txtUserName, EditText txtPassword, String taiKhoanID, TextView txtWarning) {
        this.txtUserName = txtUserName;
        this.txtPassword = txtPassword;
        this.taiKhoanID = taiKhoanID;
        this.txtWarning = txtWarning;
        this.check = false;
    }

    public Boolean checkAccount() {
        String userName = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();

        if (userName.isEmpty() && password.isEmpty()) {
            txtWarning.setText("Vui lòng nhập \"Số điện thoại\" và \"Mật khẩu\"");
        } else if (userName.isEmpty()) {
            txtWarning.setText("Vui lòng nhập \"Số điện thoại\"");
        } else if (userName.length() <= 9) {
            txtWarning.setText("\"Số điện thoại\" không hợp lệ");
        } else if (password.isEmpty()) {
            txtWarning.setText("Vui lòng nhập \"Mật khẩu\"");
        } else if (password.length() < 8) {
            txtWarning.setText("Mật khẩu không đủ 8 ký tự");
            //this.check = true;
        } else if (taiKhoanID.equals("-1")) {
            txtWarning.setText("Không tìm thông tin tài khoản");
        } else {
            this.check = true;
        }
        return getCheck();
    }


    public EditText getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(EditText txtUserName) {
        this.txtUserName = txtUserName;
    }

    public EditText getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(EditText txtPassword) {
        this.txtPassword = txtPassword;
    }

    public TextView getTxtWarning() {
        return txtWarning;
    }

    public void setTxtWarning(TextView txtWarning) {
        this.txtWarning = txtWarning;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

}

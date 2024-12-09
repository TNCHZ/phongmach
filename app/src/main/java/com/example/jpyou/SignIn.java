package com.example.jpyou;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jpyou.Employee.Doctor.DoctorInterface;
import com.example.jpyou.Employee.Nurse.NurseInterface;
import com.example.jpyou.Model.WarningAccount;
import com.example.jpyou.User.UserInterface;
import com.example.jpyou.User.UserSignUp;
import com.example.myapplication.R;

public class SignIn extends AppCompatActivity {
    private Button btnSignIn, btnSignUp;
    private EditText txtUsername, txtPassword;
    private TextView txtWarning;
    private MyDatabaseHelper db;
    private CheckBox ckbShowPasswork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        db = new MyDatabaseHelper(this);
        txtUsername = findViewById(R.id.txtUserName_SignIn);
        txtPassword = findViewById(R.id.txtPasswork_SignIn);
        txtWarning = findViewById(R.id.textViewWarning_SignIn);
        showPasswork();

        btnSignUp = findViewById(R.id.btnSignUp_SignIn);
        btnSignIn = findViewById(R.id.btnSignIn_SignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taiKhoanID = db.verifyPassword(txtUsername.getText().toString(), txtPassword.getText().toString());
                String role = db.getRole(taiKhoanID);
                WarningAccount warningAccount = new WarningAccount(txtUsername, txtPassword, taiKhoanID, txtWarning);
                if (warningAccount.checkAccount()) {
                    if (role.equals("Benh nhan")) {
                        Intent intent = new Intent(SignIn.this, UserInterface.class);
                        intent.putExtra("TaiKhoanID", taiKhoanID);
                        startActivity(intent);
                    } else if (role.equals("Bac si")) {
                        Intent intent = new Intent(SignIn.this, DoctorInterface.class);
                        intent.putExtra("TaiKhoanID", taiKhoanID);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(SignIn.this, NurseInterface.class);
                        intent.putExtra("TaiKhoanID", taiKhoanID);
                        startActivity(intent);
                    }
                }
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, UserSignUp.class);
                startActivity(intent);
            }
        });
    }

    private void showPasswork() {
        ckbShowPasswork = findViewById(R.id.ckbShow_SignIn);
        txtPassword = findViewById(R.id.txtPasswork_SignIn);

        ckbShowPasswork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    txtPassword.setTransformationMethod(null);
                } else {
                    txtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    txtPassword.setSelection(txtPassword.getText().length());
                }
            }
        });
    }
}
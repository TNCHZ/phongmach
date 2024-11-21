package com.example.jpyou.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jpyou.MyDatabaseHelper;
import com.example.myapplication.R;

public class UserSignIn extends AppCompatActivity {
    private Button btnSignIn, btnSignUp;
    private EditText txtUsername, txtPassword;
    private MyDatabaseHelper db;
    private CheckBox ckbShowPasswork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new MyDatabaseHelper(this);
        btnSignUp = findViewById(R.id.btnSignUp_UserSignIn);
        btnSignIn = findViewById(R.id.btnSignIn_UserSignIn);

        txtUsername = findViewById(R.id.txtUserName_UserSignIn);
        txtPassword = findViewById(R.id.txtPasswork_UserSignIn);

        showPasswork();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taiKhoanID = db.verifyPassword(txtUsername.getText().toString(), txtPassword.getText().toString(), "Bệnh nhân");
                if (taiKhoanID.equals("-1"))
                    return;
                Intent intent = new Intent(UserSignIn.this, UserInterface.class);
                intent.putExtra("TaiKhoanID", taiKhoanID);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserSignIn.this, UserSignUp.class);
                startActivity(intent);
            }
        });

    }

    private void showPasswork() {
        ckbShowPasswork = findViewById(R.id.ckbShow_UserSignIn);
        txtPassword = findViewById(R.id.txtPasswork_UserSignIn);

        ckbShowPasswork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    txtPassword.setTransformationMethod(null);
                }else {
                    txtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    txtPassword.setSelection(txtPassword.getText().length());
                }
            }
        });
    }
}
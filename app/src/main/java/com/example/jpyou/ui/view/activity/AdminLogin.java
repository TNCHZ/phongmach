package com.example.jpyou.ui.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.WarningAccount;
import com.example.myapplication.R;

public class AdminLogin extends AppCompatActivity {
    private EditText username, password;
    private Button btnSignIn;
    private MyDatabaseHelper db;
    private TextView txtWarning;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_in_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtWarning = findViewById(R.id.textViewWarning_Admin);
        username = findViewById(R.id.txtUsername_Admin);
        password = findViewById(R.id.txtPassword_Admin);
        btnSignIn = findViewById(R.id.btnLogin_Admin);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taiKhoanID = db.verifyPassword(username.getText().toString(), password.getText().toString());
                String role = db.getRole(taiKhoanID);
                WarningAccount warningAccount = new WarningAccount(username, password, taiKhoanID, txtWarning);
                if (warningAccount.checkAccount()) {
                    if (role.equals("Admin")) {
                        Intent intent = new Intent(AdminLogin.this, AdminInterface.class);
                        intent.putExtra("TaiKhoanID", taiKhoanID);
                        startActivity(intent);
                    }
                }
            }
        });


        db = new MyDatabaseHelper(this);
    }


}

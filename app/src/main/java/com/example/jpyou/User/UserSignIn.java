package com.example.jpyou.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        btnSignUp = findViewById(R.id.btnSignUp_User);
        btnSignIn = findViewById(R.id.btnSignIn_User);

        txtUsername = findViewById(R.id.txtUserName_User);
        txtPassword = findViewById(R.id.txtPasswork_User);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.verifyPassword(txtUsername.getText().toString(), txtPassword.getText().toString(), "Bệnh nhân").equals("-1"))
                    return;

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
}
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

public class UserSignUp extends AppCompatActivity {
    private Button btnAddUser;
    private EditText txtUsername, txtPassword;
    private MyDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new MyDatabaseHelper(this);

        txtUsername = findViewById(R.id.txtUserNameSignUp_User);
        txtPassword = findViewById(R.id.txtPasswordSignUp_User);

        btnAddUser = findViewById(R.id.btnAdd_User);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                if(username.isEmpty() || password.isEmpty())
                    return;
                db.addUser(username, password, "Bệnh nhân");
                Intent intent = new Intent(UserSignUp.this, UserSignIn.class);
                startActivity(intent);
            }
        });

    }


}
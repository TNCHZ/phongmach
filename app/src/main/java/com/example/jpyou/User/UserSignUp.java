package com.example.jpyou.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jpyou.MyDatabaseHelper;
import com.example.myapplication.R;

public class UserSignUp extends AppCompatActivity {
    private Button btnAddUser;
    private EditText txtUsername, txtPassword, txtName, txtDayOfBirth, txtPhone, txtEmail;
    private MyDatabaseHelper db;
    private RadioButton rdMale;

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
        txtName = findViewById(R.id.txtNameSignUp_User);
        txtDayOfBirth = findViewById(R.id.txtDayOfBirthSignUp_User);
        txtPhone = findViewById(R.id.txtPhoneSignUp_User);
        txtEmail = findViewById(R.id.txtEmailSignUp_User);
        rdMale = findViewById(R.id.rdMaleSignUp_User);


        btnAddUser = findViewById(R.id.btnAdd_User);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                String name = txtName.getText().toString();
                String gender = rdMale.isChecked() ? "Nam" : "Nữ";
                String dayOfBirth = txtDayOfBirth.getText().toString();
                String phone = txtPhone.getText().toString();
                String email = txtEmail.getText().toString();
                if(username.isEmpty() || password.isEmpty() || name.isEmpty() || dayOfBirth.isEmpty() || phone.isEmpty() || email.isEmpty())
                    return;
                db.addUser(username, password, name,gender, dayOfBirth, phone, email);
                Intent intent = new Intent(UserSignUp.this, UserSignIn.class);
                startActivity(intent);
            }
        });

    }


}
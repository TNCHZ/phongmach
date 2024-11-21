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
    private EditText txtPassword, txtName, txtDayOfBirth, txtPhone, txtEmail;
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

        txtPassword = findViewById(R.id.txtPassword_UserSignUp);
        txtName = findViewById(R.id.txtName_UserSignUp);
        txtDayOfBirth = findViewById(R.id.txtDayOfBirth_UserSignUp);
        txtPhone = findViewById(R.id.txtPhone_UserSignUp);
        txtEmail = findViewById(R.id.txtEmail_UserSignUp);
        rdMale = findViewById(R.id.rdMale_UserSignUp);


        btnAddUser = findViewById(R.id.btnAdd_UserSignUp);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = txtPassword.getText().toString();
                String name = txtName.getText().toString();
                String gender = rdMale.isChecked() ? "Nam" : "Nữ";
                String dayOfBirth = txtDayOfBirth.getText().toString();
                String phone = txtPhone.getText().toString();
                String email = txtEmail.getText().toString();
                if(password.isEmpty() || name.isEmpty() || dayOfBirth.isEmpty() || phone.isEmpty() || email.isEmpty())
                    return;
                db.addUser(phone, password, name,gender, dayOfBirth, phone, email);
                Intent intent = new Intent(UserSignUp.this, UserSignIn.class);
                startActivity(intent);
            }
        });

    }


}
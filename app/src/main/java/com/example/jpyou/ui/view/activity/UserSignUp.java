package com.example.jpyou.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.WarningAccount;
import com.example.myapplication.R;

public class UserSignUp extends AppCompatActivity {
    private Button btnAddUser;
    private EditText txtPassword, txtPasswordConfirm, txtName, txtDayOfBirth, txtPhone, txtEmail;
    private MyDatabaseHelper db;
    private RadioButton rdMale;
    private TextView txtWarning;

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

        txtName = findViewById(R.id.txtName_UserSignUp);
        txtDayOfBirth = findViewById(R.id.txtDayOfBirth_UserSignUp);
        rdMale = findViewById(R.id.rdMale_UserSignUp);
        txtEmail = findViewById(R.id.txtEmail_UserSignUp);

        txtPhone = findViewById(R.id.txtPhone_UserSignUp);
        txtWarning = findViewById(R.id.textViewWarning_UserSignUp);
        txtPassword = findViewById(R.id.txtPassword_UserSignUp);
        txtPasswordConfirm = findViewById(R.id.txtComfirmPassword_UserSignUp);


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

                if (name.isEmpty() || dayOfBirth.isEmpty() || email.isEmpty()) {
                    txtWarning.setText("Vui lòng kiểm tra lại thông tin");
                } else {
                    WarningAccount warningAccount = new WarningAccount(txtPhone, txtPassword, txtWarning);
                    if (warningAccount.checkAccount()) {
                        if (txtPasswordConfirm.getText().toString().isEmpty()){
                            txtWarning.setText("Vui lòng nhập lại mật khẩu");
                        } else if (!txtPasswordConfirm.getText().toString().equals(password)) {
                            txtWarning.setText("Mật khẩu nhập lại không trùng khớp");
                        } else {
                            db.addUser(phone, password, name, gender, dayOfBirth, phone, email);
                            Intent intent = new Intent(UserSignUp.this, SignIn.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

    }


}
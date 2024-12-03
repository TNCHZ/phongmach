package com.example.jpyou.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jpyou.Employee.Nurse.NurseInterface;
import com.example.jpyou.MyDatabaseHelper;
import com.example.myapplication.R;

public class EmployeeLogin extends AppCompatActivity {
    private Button btnSignIn;
    private EditText txtUsername, txtPassword;
    private MyDatabaseHelper db;
    private String[] role = {"Bác sĩ", "Y tá"};
    private Spinner sp;
    private String roleChoose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.employee_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new MyDatabaseHelper(this);
        txtUsername = findViewById(R.id.txtUserName_EmployeeSignIn);
        txtPassword = findViewById(R.id.txtPasswork_EmployeeSignIn);
        btnSignIn = findViewById(R.id.btnSignIn_EmployeeSignIn);
        sp = findViewById(R.id.spinnerRole_EmployeeSignIn);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item, // Layout hiển thị mỗi item
                role
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Layout khi dropdown mở
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                roleChoose = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Khi không chọn gì
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taiKhoanID = db.verifyPassword(txtUsername.getText().toString(), txtPassword.getText().toString(), roleChoose);

                switch (roleChoose) {
                    case "Y tá":
                    if (!taiKhoanID.equals("-1")) {
                        Intent intent = new Intent(EmployeeLogin.this, NurseInterface.class);
                        intent.putExtra("TaiKhoanID", taiKhoanID);
                        startActivity(intent);
                    }
                    break;
                }

            }
        });
    }


}

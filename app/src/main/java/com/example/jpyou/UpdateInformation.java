package com.example.jpyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jpyou.Model.PersonInformation;
import com.example.jpyou.User.UserInterface;
import com.example.myapplication.R;

public class UpdateInformation extends AppCompatActivity {
    private PersonInformation ps;
    private EditText txtName, txtDayOfBirth, txtPhone, txtEmail;
    private MyDatabaseHelper db;
    private RadioButton rdMale, rdFemale;
    private Button btnUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.update_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new MyDatabaseHelper(this);
        ps = (PersonInformation) getIntent().getSerializableExtra("PersonInformation");

        txtName = findViewById(R.id.txtNameUpdate);
        txtDayOfBirth = findViewById(R.id.txtDayOfBirthUpdate);
        txtPhone = findViewById(R.id.txtPhoneUpdate);
        txtEmail = findViewById(R.id.txtEmailUpdate);
        rdMale = findViewById(R.id.rdMaleUpdate);
        rdFemale = findViewById(R.id.rdFemaleUpdate);
        
        txtName.setText(ps.getHoTen());
        txtDayOfBirth.setText(ps.getNgaySinh());
        txtPhone.setText(ps.getSoDT());
        txtEmail.setText(ps.getEmail());

        if (ps.getGioiTinh().equals("Nam")) {
            rdMale.setChecked(true);
        } else {
            rdFemale.setChecked(true);
        }


        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = ps.getId();
                String name = txtName.getText().toString();
                String gender = rdMale.isChecked() ? "Nam" : "Nữ";
                String dayOfBirth = txtDayOfBirth.getText().toString();
                String phone = txtPhone.getText().toString();
                String email = txtEmail.getText().toString();

                db.updateInformation(id, name, gender, dayOfBirth, phone, email);

                Intent intent = new Intent(UpdateInformation.this, UserInterface.class);
                startActivity(intent);
            }
        });
    }
}

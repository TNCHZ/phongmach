package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
import android.app.Person;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.PersonInformation;
import com.example.jpyou.ui.adapter.MedicalRecordAdapter;
import com.example.jpyou.data.model.Medicine;
import com.example.jpyou.ui.viewmodel.SharedViewModel;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDoctorFragment extends Fragment {
    private String userID;
    private String patientID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("TaiKhoanID", null);
    }


    //====================================================================
    private ListView listView;
    private Button btnAddMedicine, btnConfirm;
    private EditText symptom, txtName, txtGender, txtPhone, txtDayOfBirth;
    private List<Medicine> medicines;
    private MyDatabaseHelper db;
    private PersonInformation ps;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_medical_record, container, false);
        {
            listView = view.findViewById(R.id.listPrescriptions_MedicalRecordDoctorFragment);
            symptom = view.findViewById(R.id.txtResults_MedicalRecordDoctorFragment);
            db = new MyDatabaseHelper(getActivity());
            txtName = view.findViewById(R.id.txtName_MedicalRecordDoctorFragment);
            txtDayOfBirth = view.findViewById(R.id.txtDate_MedicalRecordDoctorFragment);
            txtGender = view.findViewById(R.id.txtGender_MedicalRecordDoctorFragment);
            txtPhone = view.findViewById(R.id.txtPhone_MedicalRecordDoctorFragment);
            medicines = new ArrayList<>();

            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.getMedicines().observe(getViewLifecycleOwner(), saveMedicines -> {
                medicines.addAll(saveMedicines);

                if (!saveMedicines.isEmpty()) {
                    MedicalRecordAdapter adapter = new MedicalRecordAdapter(getActivity(), R.layout.row_medicine_chosen, saveMedicines);
                    listView.setAdapter(adapter);
                } else {
                    listView.setVisibility(View.GONE);
                }
            });


            Bundle bundle = getArguments();
            if (bundle != null) {
                patientID = bundle.getString("patient_id", "-1");  // Giá trị mặc định là "-1" nếu không tìm thấy
                ps = db.getInformation(patientID);
                txtName.setText(ps.getHoTen());
                txtPhone.setText(ps.getSoDT());
                txtGender.setText(ps.getGioiTinh());
                txtDayOfBirth.setText(ps.getNgaySinh());
            }

            btnAddMedicine = view.findViewById(R.id.btnAddMedicine_MedicalRecordDoctorFragment);
            btnAddMedicine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() != null) {
                        AddMedicineDoctorFragment addMedicineDoctorFragment = new AddMedicineDoctorFragment();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_doctor_medical_records, addMedicineDoctorFragment)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        // Xử lý khi context không hợp lệ (tùy vào tình huống)
                        Log.e("AddMedicine", "Context is null. Cannot perform fragment transaction.");
                    }
                }
            });

            btnConfirm = view.findViewById(R.id.btnFinish_MedicalRecordDoctorFragment);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String symp = symptom.getText().toString();
                    if (!medicines.isEmpty() && !symp.isEmpty()) {
                        db.addMedicine(db.getPatientID(patientID), db.getDoctorID(userID), symp, medicines);
                    }
                    getActivity().getSupportFragmentManager().popBackStack(); // Quay lại Fragment trước
                }
            });

        }
        return view;
    }
}

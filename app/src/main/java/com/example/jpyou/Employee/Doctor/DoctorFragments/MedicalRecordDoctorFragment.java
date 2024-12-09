package com.example.jpyou.Employee.Doctor.DoctorFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.jpyou.Model.Medicine;
import com.example.myapplication.R;

import java.util.ArrayList;

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
    private Button btnAddMedicine;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_medical_record, container, false);
        {
            listView = view.findViewById(R.id.listPrescriptions_MedicalRecordDoctorFragment);



            Bundle bundle = getArguments();
            if (bundle != null) {
                // Lấy patientID
                patientID = bundle.getString("patient_id", "-1");  // Giá trị mặc định là "-1" nếu không tìm thấy

                if (patientID.equals("-1")) {
                    Log.d("Info", "No patient ID found, default value used.");
                } else {
                    Log.d("Info", "Patient ID: " + patientID);
                }

                // Kiểm tra chosen_medicines và xử lý nếu có
                if (bundle.getSerializable("chosen_medicines") != null) {
                    ArrayList<Medicine> chosenMedicines = (ArrayList<Medicine>) bundle.getSerializable("chosen_medicines");
                    Log.d("alo", "Chosen medicines: " + chosenMedicines.size() + " items found.");
                    // Tiến hành xử lý chosenMedicines ở đây
                } else {
                    Log.d("alo", "No chosen medicines found.");
                }
            } else {
                Log.d("alo", "Bundle is null.");
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
                                .commit();
                    } else {
                        // Xử lý khi context không hợp lệ (tùy vào tình huống)
                        Log.e("AddMedicine", "Context is null. Cannot perform fragment transaction.");
                    }
                }
            });
        }
        return view;
    }
}

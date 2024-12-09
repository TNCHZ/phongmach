package com.example.jpyou.Employee.Doctor.DoctorFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.jpyou.BottomNavigator.HomeFragment;
import com.example.jpyou.Model.Medicine;
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
    private Spinner spinner;
    private Context context;
    private Button btnAddMedicine;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_medical_record, container, false);
        {
            listView = view.findViewById(R.id.listPrescriptions_MedicalRecordDoctorFragment);

            Bundle bundlePatientID = getArguments();
            if (bundlePatientID != null) {
                patientID = bundlePatientID.getString("patient_id", "-1");  // -1 là giá trị mặc định nếu không có key "patient_id"
            }

            btnAddMedicine = view.findViewById(R.id.btnAddMedicine_MedicalRecordDoctorFragment);
            btnAddMedicine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Kiểm tra xem context có hợp lệ hay không
                    if (getActivity() != null) {
                        // Tạo một instance mới của AddMedicineDoctorFragment
                        AddMedicineDoctorFragment addMedicineDoctorFragment = new AddMedicineDoctorFragment();

                        // Kiểm tra và truyền patientID vào bundle (nếu có)
                        if (patientID != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("patient_id", patientID);  // Truyền patientID vào Fragment
                            addMedicineDoctorFragment.setArguments(bundle);
                        }

                        // Chuyển tiếp đến Fragment AddMedicineDoctorFragment
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
        }
        return view;
    }
}
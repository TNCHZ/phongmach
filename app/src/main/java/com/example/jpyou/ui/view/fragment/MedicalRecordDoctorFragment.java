package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
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
import android.widget.ListView;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.PersonInformation;
import com.example.jpyou.ui.adapter.MedicalRecordAdapter;
import com.example.jpyou.data.model.Medicine;
import com.example.jpyou.ui.viewmodel.SharedViewModel;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDoctorFragment extends Fragment implements MedicalRecordAdapter.OnMedicineRemovedListener{
    private String userID;
    private String patientID;
    private List<Medicine> medicines;

    public MedicalRecordDoctorFragment(String id) {
        patientID = id;
    }

    public MedicalRecordDoctorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("TaiKhoanID", null);
    }


    private MedicalRecordAdapter adapter;
    private ListView listView;
    private Button btnAddMedicine, btnConfirm, btnBack;
    private EditText symptom, txtName, txtGender, txtPhone, txtDayOfBirth;

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
            btnBack = view.findViewById(R.id.btnBack_MedicalRecordDoctorFragment);

            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.getMedicines().observe(getViewLifecycleOwner(), saveMedicines -> {
                medicines.clear();
                medicines.addAll(saveMedicines);

                if (!saveMedicines.isEmpty()) {
                    adapter = new MedicalRecordAdapter(getActivity(), R.layout.row_medicine_chosen, saveMedicines,new MedicalRecordAdapter.OnMedicineRemovedListener(){
                        @Override
                        public void onMedicineRemoved(Medicine removedMedicine) {
                            medicines.remove(removedMedicine); // Xóa thuốc khỏi danh sách local
                            adapter.notifyDataSetChanged(); // Cập nhật giao diện adapter

                            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
                            viewModel.removeMedicine(removedMedicine); // Cập nhật LiveData trong ViewModel

                            if (medicines.isEmpty()) {
                                listView.setVisibility(View.GONE); // Ẩn ListView nếu không có thuốc nào
                            }
                        }

                    });
                    listView.setAdapter(adapter);
                } else {
                    listView.setVisibility(View.GONE);
                }
            });

            ps = db.getInformation(patientID);
            if (ps != null) {
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

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }
        return view;
    }


    @Override
    public void onMedicineRemoved(Medicine removedMedicine) {
    }
}

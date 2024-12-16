package com.example.jpyou.ui.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.Medicine;
import com.example.jpyou.data.model.PersonInformation;
import com.example.jpyou.ui.adapter.ShowMedicineForPatientAdapter;
import com.example.myapplication.R;

import java.util.List;


public class ShowResultUserFragment extends Fragment {
    private EditText txtName, txtDayOfBirth, txtGender, txtSympton;
    private ListView lv;
    private Button btnBack;
    private String patientID, day;
    private PersonInformation ps;
    private MyDatabaseHelper db;
    private List<Medicine> mcs;

    public ShowResultUserFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user_show_result, container, false);
        txtName = view.findViewById(R.id.txtName_ShowResultUserFragment);
        txtDayOfBirth = view.findViewById(R.id.txtDayOfBirth_ShowResultUserFragment);
        txtGender = view.findViewById(R.id.txtGender_ShowResultUserFragment);
        txtSympton = view.findViewById(R.id.txtPhone_ShowResultUserFragment);
        lv = view.findViewById(R.id.listView_ShowResultUserFragment);
        btnBack = view.findViewById(R.id.buttonBack_ShowResultUserFragment);
        db = new MyDatabaseHelper(getActivity());


        Bundle bundle = getArguments();
        if (bundle != null) {
            Log.d("1", ps.getId());
            patientID = bundle.getString("patient_id", "-1");  // Giá trị mặc định là "-1" nếu không tìm thấy
            day = bundle.getString("NgayKham", "-1");
            ps = db.getInformation(patientID);
            txtName.setText(ps.getHoTen());
            txtGender.setText(ps.getGioiTinh());
            txtDayOfBirth.setText(ps.getNgaySinh());
            txtSympton.setText(db.getSympton(patientID, day));
            mcs = db.getResult(patientID, day);
            ShowMedicineForPatientAdapter adapter = new ShowMedicineForPatientAdapter(getActivity(), R.layout.row_medicine_user, mcs);
            lv.setAdapter(adapter);
        }
        return view;
    }
}
package com.example.jpyou.Employee.Doctor.DoctorFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jpyou.Employee.Doctor.Medicine;
import com.example.jpyou.Employee.Doctor.MedicineAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicalRecordDoctorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicalRecordDoctorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MedicalRecordDoctorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicalRecordDoctorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicalRecordDoctorFragment newInstance(String param1, String param2) {
        MedicalRecordDoctorFragment fragment = new MedicalRecordDoctorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    //====================================================================
    private ListView listView;
    private Spinner spinner;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_medical_record, container, false);
        {
            spinner = view.findViewById(R.id.spinnerPrescriptions_MedicalRecordDoctorFragment);
            listView = view.findViewById(R.id.listPrescriptions_MedicalRecordDoctorFragment);
//            MedicineAdapter adapter = new MedicineAdapter(view.getContext(), R.layout.row_medicine, getListMedicine());
//            spinner.setAdapter(adapter);
//            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    Toast.makeText(getContext().getApplicationContext(), adapter.getItem(i).getName(), Toast.LENGTH_SHORT).show();
//
//                }
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {}
//            });

        }
        return view;
    }
    private List<Medicine> getListMedicine(){
        List<Medicine> list = new ArrayList<>();
        list.add(new Medicine("Thuoc A"));
        list.add(new Medicine("Thuoc B"));
        list.add(new Medicine("Thuoc C"));

        return list;
    }
}
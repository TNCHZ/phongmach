package com.example.jpyou.Employee.Doctor.DoctorFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jpyou.Employee.SchedulePatientAdapter;
import com.example.jpyou.User.UserInformation;
import com.example.myapplication.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleDoctorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleDoctorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScheduleDoctorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleDoctorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleDoctorFragment newInstance(String param1, String param2) {
        ScheduleDoctorFragment fragment = new ScheduleDoctorFragment();
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
    private ArrayList<UserInformation> arrayList;
    private SchedulePatientAdapter adapter;
    private Context context;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_schedule, container, false);
        {
            listView = view.findViewById(R.id.list_ScheduleDoctorFragment);
            init(view);

//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                    FragmentTransaction transaction = fragmentManager.beginTransaction();
//                    MedicalRecordDoctorFragment anotherFragment = new MedicalRecordDoctorFragment();
//                    transaction.replace(R.id.fragment_medical_records, anotherFragment);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                }
//            });
        }
        return view;
    }

    private void init(View view) {
        listView = view.findViewById(R.id.list_ScheduleDoctorFragment);
        arrayList = new ArrayList<>();

        UserInformation p1, p2;
        p1 = new UserInformation("Chương", "Bệnh: hết tiền", R.drawable.ic_person);
        p2 = new UserInformation("Trung", "Bệnh: Đẹp trai vãi", R.drawable.ic_person);
        arrayList.add(p1);
        arrayList.add(p2);

        context = view.getContext();
        adapter = new SchedulePatientAdapter(context, R.layout.row_list_patient, arrayList);
        listView.setAdapter(adapter);
    }
}
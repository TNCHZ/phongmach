package com.example.jpyou.Nurse.NurseFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jpyou.Nurse.SchedulePatientAdapter;
import com.example.jpyou.Nurse.SchedulePatient;
import com.example.myapplication.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SchedulePatientNurseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchedulePatientNurseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SchedulePatientNurseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleNurseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SchedulePatientNurseFragment newInstance(String param1, String param2) {
        SchedulePatientNurseFragment fragment = new SchedulePatientNurseFragment();
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

    ListView listView;
    ArrayList<SchedulePatient> arrayList;
    SchedulePatientAdapter adapter;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nurse_schedule_patient, container, false);
        {
            listView = (ListView) view.findViewById(R.id.listSchedulePatient_ScheduleNurseFragment);
            //Add các phiếu khám
            arrayList = new ArrayList<>();
            arrayList.add(new SchedulePatient("Tuấn", "Bệnh: chán đời", R.drawable.ic_person));
            arrayList.add(new SchedulePatient("Tú", "Bệnh: sốt, ho, xổ mũi, phê pha, ngáo đớ,...", R.drawable.ic_person));
            arrayList.add(new SchedulePatient("Đức", "Bệnh: Sơ hở là về quê bỏ rơi Tú", R.drawable.ic_person));
            arrayList.add(new SchedulePatient("Minh", "Bệnh: Hắc xì dễ thương", R.drawable.ic_person));
            arrayList.add(new SchedulePatient("Chương", "Bệnh: hết tiền", R.drawable.ic_person));
            arrayList.add(new SchedulePatient("Trung", "Bệnh: Đẹp trai vãi", R.drawable.ic_person));


            context = view.getContext();
            adapter = new SchedulePatientAdapter(context, R.layout.nurse_row_schedule_patient, arrayList);
            listView.setAdapter(adapter);
        }
        return view;
    }
}
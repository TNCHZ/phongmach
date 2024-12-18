package com.example.jpyou.ui.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.Doctor;
import com.example.jpyou.ui.adapter.ShowDoctorAdapter;
import com.example.myapplication.R;

import java.util.List;


public class ChooseDoctorUserFragment extends Fragment {
    private String userID;
    public ChooseDoctorUserFragment() {

    }

    public ChooseDoctorUserFragment(String id, List<Doctor> dcs){
        userID = id;
        doctors = dcs;
    }

    private SearchView sv;
    private Button btnBack;
    private ListView lv;
    private List<Doctor> doctors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_choose_doctor, container, false);
        sv = view.findViewById(R.id.searchView_ChooseDoctorUserFragment);
        btnBack = view.findViewById(R.id.buttonBack_ChooseDoctorUserFragment);
        lv = view.findViewById(R.id.listViewDoctor_ChooseDoctorUserFragment);

        ShowDoctorAdapter adapter = new ShowDoctorAdapter(getActivity(), R.layout.row_list_doctor, doctors, userID);
        lv.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        return view;
    }
}
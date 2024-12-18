package com.example.jpyou.ui.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class ScheduleDoctorFragment extends Fragment {
    private String userID;
    private Button btnBack;
    private SearchView sv;
    private ListView lv;
    private List<String> day;
    private MyDatabaseHelper db;

    public ScheduleDoctorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_schedule, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("TaiKhoanID", null);
        db = new MyDatabaseHelper(getActivity());

        btnBack = view.findViewById(R.id.buttonBack_DoctorScheduleFragment);
        sv = view.findViewById(R.id.searchView_DoctorScheduleFragment);
        lv = view.findViewById(R.id.listView_DoctorScheduleFragment);
        day = new ArrayList<>();
        day = db.getAllDayDoctorWork(db.getDoctorID(userID));

        if (!day.isEmpty()) {
            // Sử dụng ArrayAdapter để hiển thị các ngày trong ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, day);
            lv.setAdapter(adapter);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack(); // Quay lại Fragment trước
            }
        });


        return view;
    }
}
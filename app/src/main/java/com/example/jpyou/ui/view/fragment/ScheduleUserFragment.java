package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jpyou.ui.adapter.ShowSchduleAndCancelAdapter;
import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.UserInformation;
import com.example.jpyou.ui.view.activity.SignIn;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ScheduleUserFragment extends Fragment {

    private String userID;
    private CalendarView cldView;
    private ListView lv;
    private MyDatabaseHelper db;
    private List<UserInformation> schedules;


    public ScheduleUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("TaiKhoanID", null);
    }

    private Button btnCheck;
    private LinearLayout layoutChecked, layoutNotChecked;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_schedule, container, false);
        {
            layoutChecked = view.findViewById(R.id.linearLayoutChecked_ScheduleUserFragment);
            layoutNotChecked = view.findViewById(R.id.linearLayoutNotChecked_ScheduleUserFragment);
            if (userID== null) {
                layoutNotChecked.setVisibility(View.VISIBLE);
                btnCheck = view.findViewById(R.id.btnChecked_ScheduleUserFragment);
                btnCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SignIn.class);
                        startActivity(intent);
                    }
                });
            } else {
                layoutNotChecked.setVisibility(View.INVISIBLE);
                layoutChecked.setVisibility(View.VISIBLE);

                db = new MyDatabaseHelper(getActivity());
                schedules = new ArrayList<>();
                cldView = view.findViewById(R.id.calendar_ScheduleUserFragment);
                lv = view.findViewById(R.id.listSchedule_ScheduleUserFragment);

                schedules = db.getDaySchedule(db.getPatientID(userID));
                ShowSchduleAndCancelAdapter adapter = new ShowSchduleAndCancelAdapter(getActivity(), R.layout.row_show_schedule_and_cancel, schedules);
                lv.setAdapter(adapter);

                cldView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String selectedDate = sdf.format(calendar.getTime());

                    schedules = db.getScheduleAtDay(db.getPatientID(userID), selectedDate);
                    ShowSchduleAndCancelAdapter adapter2 = new ShowSchduleAndCancelAdapter(getActivity(), R.layout.row_show_schedule_and_cancel, schedules);
                    lv.setAdapter(adapter2);
                });
            }
        }
        return view;
    }
}
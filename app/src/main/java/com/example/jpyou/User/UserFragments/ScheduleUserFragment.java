package com.example.jpyou.User.UserFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import com.example.jpyou.MyDatabaseHelper;
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
    private List<String> schedules;

    public ScheduleUserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("TaiKhoanID", null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_schedule, container, false);

        db = new MyDatabaseHelper(getActivity());
        schedules = new ArrayList<>();

        cldView = view.findViewById(R.id.calendar_ScheduleUserFragment);
        lv = view.findViewById(R.id.listSchedule_ScheduleUserFragment);

        schedules = db.getDaySchedule(db.getPatientID(userID));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), // Context
                android.R.layout.simple_list_item_1, // Layout mặc định cho mỗi mục
                schedules // Dữ liệu
        );
        lv.setAdapter(adapter);

        cldView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            // Chuyển đổi ngày được chọn thành định dạng "dd/MM/yyyy"
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String selectedDate = sdf.format(calendar.getTime());

            schedules = db.getScheduleAtDay(db.getPatientID(userID), selectedDate);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                    getActivity(), // Context
                    android.R.layout.simple_list_item_1, // Layout mặc định cho mỗi mục
                    schedules // Dữ liệu
            );
            lv.setAdapter(adapter2);
        });

        return view;
    }
}
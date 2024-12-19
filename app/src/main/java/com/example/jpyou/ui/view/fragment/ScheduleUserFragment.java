package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.jpyou.data.model.Medicine;
import com.example.jpyou.ui.adapter.SchedulePatientAdapter;
import com.example.jpyou.ui.adapter.ShowMedicineAdapter;
import com.example.jpyou.ui.adapter.ShowScheduleAndCancelAdapter;
import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.jpyou.data.model.Patient;
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
    private List<Patient> schedules;
    private ShowScheduleAndCancelAdapter adapter;

    private Button btnCheck, btnRefresh;
    private LinearLayout layoutChecked, layoutNotChecked;
    private SearchView sv;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_schedule, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("TaiKhoanID", null);

        btnRefresh = view.findViewById(R.id.buttonRefresh_ScheduleUserFragment);
        layoutChecked = view.findViewById(R.id.linearLayoutChecked_ScheduleUserFragment);
        layoutNotChecked = view.findViewById(R.id.linearLayoutNotChecked_ScheduleUserFragment);

        if (userID == null) {
            setupUnauthenticatedLayout(view);
        } else {
            setupAuthenticatedLayout(view);
        }

        return view;
    }

    private void setupUnauthenticatedLayout(View view) {
        layoutNotChecked.setVisibility(View.VISIBLE);
        layoutChecked.setVisibility(View.GONE);

        btnCheck = view.findViewById(R.id.btnChecked_ScheduleUserFragment);
        btnCheck.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SignIn.class);
            startActivity(intent);
        });
    }

    private void setupAuthenticatedLayout(View view) {
        layoutNotChecked.setVisibility(View.GONE);
        layoutChecked.setVisibility(View.VISIBLE);

        db = new MyDatabaseHelper(getActivity());
        schedules = db.getDaySchedule(db.getPatientID(userID));

        sv = view.findViewById(R.id.searchView_ScheduleUserFragment);
        cldView = view.findViewById(R.id.calendar_ScheduleUserFragment);
        lv = view.findViewById(R.id.listSchedule_ScheduleUserFragment);

        adapter = new ShowScheduleAndCancelAdapter(getActivity(), R.layout.row_show_schedule_and_cancel, schedules);
        lv.setAdapter(adapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterSchedules(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterSchedules(newText);
                return false;
            }
        });

        cldView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String selectedDate = sdf.format(calendar.getTime());

            updateSchedulesForDate(selectedDate);
        });

        btnRefresh.setOnClickListener(v -> refreshList());
        adapter.setOnCancelClickListener((patient, view1) -> {
            if (db.cancelDay(patient.getId(), patient.getAppointDay())) {
                Toast.makeText(getActivity(), "Hủy lịch thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Hủy lịch thất bại", Toast.LENGTH_SHORT).show();
            }
            refreshList();
        });
    }

    private void filterSchedules(String query) {
        List<Patient> filteredList = new ArrayList<>();
        for (Patient ps : schedules) {
            if (ps.getAppointDay().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(ps);
            }
        }

        adapter = new ShowScheduleAndCancelAdapter(getActivity(), R.layout.row_show_schedule_and_cancel, filteredList);
        lv.setAdapter(adapter);
    }

    private void updateSchedulesForDate(String selectedDate) {
        schedules = db.getScheduleAtDay(db.getPatientID(userID), selectedDate);
        adapter = new ShowScheduleAndCancelAdapter(getActivity(), R.layout.row_show_schedule_and_cancel, schedules);
        lv.setAdapter(adapter);
    }

    private void refreshList() {
        schedules.clear();
        schedules.addAll(db.getDaySchedule(db.getPatientID(userID)));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(userID!=null)
            refreshList();
    }

}

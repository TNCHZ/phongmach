package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.jpyou.data.model.Medicine;
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


    public ScheduleUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Button btnCheck;
    private LinearLayout layoutChecked, layoutNotChecked;
    private SearchView sv;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_schedule, container, false);
        {
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
            userID = sharedPreferences.getString("TaiKhoanID", null);

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

                sv = view.findViewById(R.id.searchView_ScheduleUserFragment);
                db = new MyDatabaseHelper(getActivity());
                cldView = view.findViewById(R.id.calendar_ScheduleUserFragment);
                lv = view.findViewById(R.id.listSchedule_ScheduleUserFragment);

                sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        filterAtDay(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        // Filter results as the user types
                        filterAtDay(newText);
                        return false;
                    }
                });


                schedules = new ArrayList<>();
                schedules = db.getDaySchedule(db.getPatientID(userID));
                ShowScheduleAndCancelAdapter adapter = new ShowScheduleAndCancelAdapter(getActivity(), R.layout.row_show_schedule_and_cancel, schedules);
                lv.setAdapter(adapter);

                cldView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String selectedDate = sdf.format(calendar.getTime());

                    schedules = db.getScheduleAtDay(db.getPatientID(userID), selectedDate);
                    ShowScheduleAndCancelAdapter adapter2 = new ShowScheduleAndCancelAdapter(getActivity(), R.layout.row_show_schedule_and_cancel, schedules);
                    lv.setAdapter(adapter2);
                });
            }
        }
        return view;
    }

    private void filterAtDay(String query) {
        List<Patient> filteredList = new ArrayList<>();
        for (Patient ps : schedules) {
            if (ps.getAppointDay().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(ps);
            }
        }

        // Update the adapter with the filtered list
        ShowScheduleAndCancelAdapter filteredAdapter = new ShowScheduleAndCancelAdapter(getActivity(), R.layout.row_show_schedule_and_cancel, filteredList);
        lv.setAdapter(filteredAdapter); // Update the ListView with the filtered results
    }
}
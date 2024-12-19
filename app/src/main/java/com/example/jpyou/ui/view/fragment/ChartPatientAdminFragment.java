package com.example.jpyou.ui.view.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.jpyou.data.datasource.MyDatabaseHelper;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChartPatientAdminFragment extends Fragment {
    private BarChart barChart;
    //private HorizontalBarChart barChart;
    private List<String> xValue;
    private List<String> quy = Arrays.asList("Quý 1", "Quý 2", "Quý 3", "Quý 4", "Năm");
    private MyDatabaseHelper db;
    private Button btnBack;
    private Spinner sp;

    public ChartPatientAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_chart, container, false);
        barChart = view.findViewById(R.id.chart);
        sp = view.findViewById(R.id.spinner_ChartAdminFragment);
        btnBack = view.findViewById(R.id.buttonBack_ChartAdminFragment);
        db = new MyDatabaseHelper(getActivity());
        // Thiết lập Adapter cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, quy);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        // Xử lý sự kiện Spinner
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                ArrayList<BarEntry> entries = new ArrayList<>();
                String selectedOption = adapterView.getItemAtPosition(position).toString();

                switch (selectedOption) {
                    case "Quý 1":
                        entries = chartQuarter(1);
                        xValue = getQuarterLabels(1, 3);
                        break;
                    case "Quý 2":
                        entries = chartQuarter(4);
                        xValue = getQuarterLabels(4, 6);
                        break;
                    case "Quý 3":
                        entries = chartQuarter(7);
                        xValue = getQuarterLabels(7, 9);
                        break;
                    case "Quý 4":
                        entries = chartQuarter(10);
                        xValue = getQuarterLabels(10, 12);
                        break;
                    case "Năm":
                        entries = chartYear();
                        xValue = getYearLabels();
                        break;
                }

                // Cập nhật dữ liệu biểu đồ
                updateChart(entries);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // Thiết lập biểu đồ ban đầu
        setupChart();

        return view;
    }

    private void setupChart() {
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(30f);
        yAxis.setAxisLineWidth(1f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        barChart.getAxisRight().setEnabled(false); // Ẩn trục Y bên phải
        barChart.getDescription().setEnabled(false); // Tắt mô tả biểu đồ
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValue));
    }

    private void updateChart(ArrayList<BarEntry> entries) {
        BarDataSet barDataSet = new BarDataSet(entries, "Thống kê Bệnh Nhân");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f); // Độ rộng của thanh
        barChart.setData(barData);
        barChart.invalidate(); // Làm mới biểu đồ
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValue));
    }

    private ArrayList<BarEntry> chartQuarter(int startMonth) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            String j = startMonth < 10 ? "0" + String.valueOf(startMonth + i) : String.valueOf(startMonth + i);
            entries.add(new BarEntry(i, db.countPatient(j) * 1f));

        }
        return entries;
    }

    private ArrayList<BarEntry> chartYear() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            String j = i < 9 ? "0" + String.valueOf(i + 1) : String.valueOf(i + 1);
            entries.add(new BarEntry(i, db.countPatient(j) * 1f));
        }
        return entries;
    }

    private List<String> getQuarterLabels(int startMonth, int endMonth) {
        List<String> labels = new ArrayList<>();
        for (int i = startMonth; i <= startMonth + 2; i++) {
            labels.add("Tháng " + i);
        }
        return labels;
    }

    private List<String> getYearLabels() {
        List<String> labels = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            labels.add("Tháng " + i);
        }
        return labels;
    }

}
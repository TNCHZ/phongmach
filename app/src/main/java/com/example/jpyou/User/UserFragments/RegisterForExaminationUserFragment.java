package com.example.jpyou.User.UserFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.jpyou.MyDatabaseHelper;
import com.example.jpyou.PersonInformation;
import com.example.jpyou.User.UserInformation;
import com.example.myapplication.R;


public class RegisterForExaminationUserFragment extends Fragment {
    PersonInformation ps = new UserInformation();
    private String userID;
    private MyDatabaseHelper db;
    private String[] itemsDepartment;
    private Spinner spinner;
    Context context;

    private ViewPager2 viewPager;
    private LinearLayout linearLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("TaiKhoanID", null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_register_for_examination, container, false);
        {
            View view_UserInterface = inflater.inflate(R.layout.user_interface, container ,false);
            viewPager = view_UserInterface.findViewById(R.id.viewPager_UserInterface);
            linearLayout = view_UserInterface.findViewById(R.id.linearLayoutTop_UserInterface);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
            params.removeRule(RelativeLayout.BELOW);
            linearLayout.setLayoutParams(params);


            setUpSpinnerDepartment(view);

            Button btnRegis = view.findViewById(R.id.btnUpdate_RegisterForExaminonUserFragment);
            btnRegis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        return view;
    }

    private void setUpSpinnerDepartment(View view) {
        spinner = view.findViewById(R.id.spinnerDepartment_RegisterForExaminonUserFragment);
        itemsDepartment = new String[]{"Khoa 1", "Khoa 2", "Khoa 3"};
        context = view.getContext();
        ArrayAdapter adapter = new ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, itemsDepartment);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                // Xử lý lựa chọn của người dùng
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Xử lý khi không có gì được chọn
            }
        });
    }

}
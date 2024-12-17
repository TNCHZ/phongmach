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
import com.example.jpyou.data.model.Medicine;
import com.example.jpyou.data.model.Role;
import com.example.jpyou.ui.adapter.ShowInformationUserAdapter;
import com.example.jpyou.ui.adapter.ShowMedicineAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ListUserAdminFragment extends Fragment {
    private Button btnBack;
    private SearchView sv;
    private ListView lv;
    private MyDatabaseHelper db;
    private List<Role> rs;

    public ListUserAdminFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_list_user, container, false);
        btnBack = view.findViewById(R.id.buttonBack_ListUserAdminFragment);
        sv = view.findViewById(R.id.searchView_ListUserAdminFragment);
        lv = view.findViewById(R.id.listView_ListUserAdminFragment);
        db = new MyDatabaseHelper(getActivity());
        rs = new ArrayList<>();
        rs = db.showInformationForPerson();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Filter results when the user submits the query (optional)
                filterPeople(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter results as the user types
                filterPeople(newText);
                return false;
            }
        });

        ShowInformationUserAdapter adapter = new ShowInformationUserAdapter(getContext(), R.layout.row_information, rs);
        lv.setAdapter(adapter);

        return view;
    }

    private void filterPeople(String query) {
        List<Role> filteredList = new ArrayList<>();
        for (Role r : rs) {
            if (r.getPs().getHoTen().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(r);
            }
        }

        ShowInformationUserAdapter filteredAdapter = new ShowInformationUserAdapter(getActivity(), R.layout.row_information, filteredList);
        lv.setAdapter(filteredAdapter); // Update the ListView with the filtered results
    }

    @Override
    public void onResume() {
        super.onResume();
        rs.clear();
        rs.addAll(db.showInformationForPerson());
        ShowInformationUserAdapter adapter = new ShowInformationUserAdapter(getContext(), R.layout.row_information, rs);
        lv.setAdapter(adapter);
    }
}
package com.example.jpyou.Employee.Doctor.DoctorFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import com.example.jpyou.ListViewEdit.ShowMedicine;
import com.example.jpyou.Model.Medicine;
import com.example.jpyou.MyDatabaseHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AddMedicineDoctorFragment extends Fragment {
    private String userID;
    private MyDatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("TaiKhoanID", null);
    }

    private ListView lvMedicine, lvMedicineChosen;
    private Button btnBack, brnConfirm;
    private SearchView sv;
    private List<Medicine> medicines, saveMedicines;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_add_medicine, container, false);
        {
            db = new MyDatabaseHelper(getActivity());
            lvMedicine = view.findViewById(R.id.listMedicine_AddMedicineDoctorFragment);
            lvMedicineChosen = view.findViewById(R.id.listMedicineChosen_AddMedicineDoctorFragment);
            btnBack = view.findViewById(R.id.btnBack_AddMedicineDoctorFragment);
            sv = view.findViewById(R.id.searchView_AddMedicineDoctorFragment);
            brnConfirm = view.findViewById(R.id.btnConfirm_AddMedicineDoctorFragment);

            List<String> chosenMedicines = new ArrayList<>();
            saveMedicines = new ArrayList<>();
            medicines = new ArrayList<>();
            medicines = db.getMedicines();
            ShowMedicine adapter = new ShowMedicine(getActivity(), R.layout.row_medicine, medicines);
            lvMedicine.setAdapter(adapter);

            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Filter results when the user submits the query (optional)
                    filterMedicines(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Filter results as the user types
                    filterMedicines(newText);
                    return false;
                }
            });

            lvMedicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the selected medicine
                    Medicine selectedMedicine = medicines.get(position);
                    Log.d("alo", selectedMedicine.toString());

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Nhập thông tin");

                    // Set up the input fields (number picker for quantity and instructions TextView)
                    LinearLayout layout = new LinearLayout(getActivity());
                    layout.setOrientation(LinearLayout.VERTICAL);

                    // Input for quantity
                    final EditText etQuantity = new EditText(getActivity());
                    etQuantity.setHint("Nhập số lượng");
                    etQuantity.setInputType(InputType.TYPE_CLASS_NUMBER); // Restrict to numeric input
                    layout.addView(etQuantity);

                    // Instructions TextView (Fix: Use correct EditText for instructions)
                    final EditText etUsage = new EditText(getActivity());
                    etUsage.setHint("Hướng dẫn sử dụng");  // Fixed: Set hint for etUsage
                    etUsage.setInputType(InputType.TYPE_CLASS_TEXT); // Restrict to text input (for usage instructions)
                    layout.addView(etUsage);

                    builder.setView(layout);

                    // Set up the "Confirm" button
                    builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Get the quantity entered by the user
                            String quantityText = etQuantity.getText().toString();
                            String usageText = etUsage.getText().toString();
                            if (!quantityText.isEmpty() && !usageText.isEmpty()) {
                                // Parse quantity as integer and set usage as string
                                int quantity = Integer.parseInt(quantityText); // Convert quantity to integer
                                selectedMedicine.setUsage(usageText);
                                selectedMedicine.setQuantity(quantityText);

                                chosenMedicines.add(selectedMedicine.getName());

                                saveMedicines.add(selectedMedicine);

                                // Update the list view with the new data
                                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, chosenMedicines);
                                lvMedicineChosen.setAdapter(adapter1);  // Assuming lvMedicineChosen is defined correctly
                            }
                        }
                    });

                    // Set up the "Cancel" button
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Show the dialog
                    builder.show();
                }
            });


        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicalRecordDoctorFragment back = new MedicalRecordDoctorFragment();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_doctor_add_medicines, back)
                        .commit();
            }
        });
        brnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MedicalRecordDoctorFragment back = new MedicalRecordDoctorFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("chosen_medicines", (ArrayList<Medicine>) saveMedicines);

                // Tạo fragment cũ mà bạn muốn quay lại
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_doctor_add_medicines, back)
                        .commit();
            }
        });
        return view;
    }

    private void filterMedicines(String query) {
        List<Medicine> filteredList = new ArrayList<>();
        for (Medicine medicine : medicines) {
            if (medicine.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(medicine);
            }
        }

        // Update the adapter with the filtered list
        ShowMedicine filteredAdapter = new ShowMedicine(getActivity(), R.layout.row_medicine, filteredList);
        lvMedicine.setAdapter(filteredAdapter); // Update the ListView with the filtered results
    }



}
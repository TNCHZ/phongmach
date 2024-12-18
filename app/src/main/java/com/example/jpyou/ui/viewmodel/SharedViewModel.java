package com.example.jpyou.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jpyou.data.model.Medicine;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<Medicine>> medicines = new MutableLiveData<>();


    public LiveData<List<Medicine>> getMedicines() {
        return medicines;
    }

    public void addMedicines(List<Medicine> newMedicines) {
        if (newMedicines == null || newMedicines.isEmpty()) {
            return;
        }
        List<Medicine> currentMedicines = medicines.getValue();
        if (currentMedicines == null) {
            currentMedicines = new ArrayList<>();
        }
        currentMedicines.addAll(newMedicines); // Thêm thuốc mới vào danh sách cũ
        medicines.setValue(currentMedicines);  // Cập nhật lại LiveData
    }

}

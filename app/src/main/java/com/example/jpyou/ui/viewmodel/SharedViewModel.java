package com.example.jpyou.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jpyou.data.model.Medicine;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<Medicine>> medicines = new MutableLiveData<>();


    public LiveData<List<Medicine>> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> updatedMedicines) {
        medicines.setValue(updatedMedicines);
    }

}

package com.example.myapplication.clubs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClubViewModel extends ViewModel {
    private MutableLiveData<Boolean> fabClicked = new MutableLiveData<>();
    public LiveData<Boolean> getFabClicked() {
        return fabClicked;
    }

    public void onFabClicked() {
        // Handle FAB click logic in the ViewModel
        // You can perform any additional logic here

        // For example, update LiveData to notify observers
        fabClicked.setValue(true);
    }
}
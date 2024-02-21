package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myapplication.performing.PerformingViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Performing extends Fragment {

    private PerformingViewModel viewModel;

    public static Performing newInstance() {
        return new Performing();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        System.out.println("hello2");
        View rootView = inflater.inflate(R.layout.fragment_performing2, container, false);
        viewModel = new ViewModelProvider(this).get(PerformingViewModel.class);

        // Find the FAB in the layout
        FloatingActionButton fab = rootView.findViewById(R.id.addaperf);

        // Set up click listener for the FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle FAB click in the Fragment
                System.out.println("hello33");
                viewModel.onFabClicked();

                // If you want to navigate to another fragment, you can use Navigation component
                Navigation.findNavController(view).navigate(R.id.add_page);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PerformingViewModel.class);
        // TODO: Use the ViewModel
    }
}
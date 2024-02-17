package com.example.myapplication.Athletics;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AthleticFragment extends Fragment {

    private AthleticViewModel viewModel;

    public static AthleticFragment newInstance() {
        return new AthleticFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_athletics2, container, false);
        viewModel = new ViewModelProvider(this).get(AthleticViewModel.class);

        // Find the FAB in the layout
        FloatingActionButton fab = rootView.findViewById(R.id.addath);

        // Set up click listener for the FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle FAB click in the Fragment
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
        viewModel = new ViewModelProvider(this).get(AthleticViewModel.class);
        // TODO: Use the ViewModel
    }
}
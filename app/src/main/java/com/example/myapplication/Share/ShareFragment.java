package com.example.myapplication.Share;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.MainActivity;
import com.example.myapplication.PdfUtils;
import com.example.myapplication.R;
import com.example.myapplication.ShareUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;

public class ShareFragment extends Fragment {

    private ShareViewModel viewModel;

    public static ShareFragment newInstance() {
        return new ShareFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        Button shareButton = view.findViewById(R.id.buttonPDF);

        MainActivity.activityType = "share";


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File pdfFile = PdfUtils.createPdf(getContext());
                    ShareUtils.sharePdf(getContext(), pdfFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ShareViewModel.class);
        // TODO: Use the ViewModel
    }
}
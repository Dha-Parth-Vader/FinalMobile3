package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myapplication.Athletics.AthleticViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Athletic extends AppCompatActivity {

    private Button addButton;
    public static int NUM_ACHIEVEMENTS = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_athletics2);

        try {
            Intent received = getIntent();

            String achievementName = received.getStringExtra("AchievementName");
            String achievementDescription = received.getStringExtra("AchievementDescription");

            


        } catch (Exception e) {

        }

        addButton = findViewById(R.id.addath);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddPage.class);
                i.putExtra("PreviousPage", "Athletics");
                startActivity(i);
            }
        });
    }
}
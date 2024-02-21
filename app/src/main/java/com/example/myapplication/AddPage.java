package com.example.myapplication;

import static android.content.Intent.getIntent;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;

public class AddPage extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 2;

    private Button saveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_page);

        Intent i = getIntent();
        String previousPage = i.getStringExtra("PreviousPage");

        Button buttonSelectPicture = findViewById(R.id.buttonSelectPicture);
        buttonSelectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture();
            }
        });

        saveButton = findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent i = null;

                try {
                    Intent received = getIntent();

                    String subfield = received.getStringExtra("PreviousPage");

                    if (subfield == "Academics") {
                        i = new Intent(getApplicationContext(), Academic.class);
                    } else if (subfield == "Athletics") {
                        i = new Intent(getApplicationContext(), Athletic.class);
                    } else if (subfield == "PerformingArts") {
                        i = new Intent(getApplicationContext(), Performing.class);
                    } else if (subfield == "Clubs") {
                        i = new Intent(getApplicationContext(), Club.class);
                    } else if (subfield == "Community") {
                        i = new Intent(getApplicationContext(), Community.class);
                    } else {
                        i = new Intent(getApplicationContext(), Home.class);
                    }
                } catch (Exception e) {
                    i = new Intent(getApplicationContext(), Home.class);
                }

                EditText achievementNameObj = (EditText) findViewById(R.id.editTextName);
                String achievementName = achievementNameObj.getText().toString();

                EditText achievementDescriptionObj = (EditText) findViewById(R.id.editTextName);
                String achievementDescription = achievementNameObj.getText().toString();

                i.putExtra("AchievementName", achievementName);
                i.putExtra("AchievementDescription", achievementDescription);

                startActivity(i);
            }
        });


    }

    public void selectPicture() {
        // Check and request permission if needed
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            // Permission is already granted, proceed with selecting an image
            startImagePicker();
        }
    }

    private void startImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with selecting an image
                startImagePicker();
            } else {
                // Permission denied, handle accordingly (e.g., show a message to the user)
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            // Handle the selected image URI
            Uri selectedImageUri = data.getData();
            // Do something with the selected image URI
            // For example, display it in an ImageView
            ImageView imageView = findViewById(R.id.imageViewPicture);
            imageView.setImageURI(selectedImageUri);
        }
    }
}
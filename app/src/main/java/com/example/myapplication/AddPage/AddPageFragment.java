package com.example.myapplication.AddPage;

import static android.app.Activity.RESULT_OK;

import com.example.myapplication.Googlesignin;
import com.example.myapplication.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.Manifest;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddPageFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private AddPageViewModel mViewModel;
    private Uri imageUsed = null;

    public static AddPageFragment newInstance() {
        return new AddPageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_page, container, false);

        Button buttonSelectPicture = rootView.findViewById(R.id.buttonSelectPicture);
        buttonSelectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture();
            }
        });

        FloatingActionButton buttonSave = rootView.findViewById(R.id.save);
        buttonSave.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToDatabase(rootView);
                Navigation.findNavController(view).navigate(R.id.academic);
            }
        });

        // ... rest of your code ...

        return rootView;


    }

    public void saveToDatabase(View rootView) {

        /*EditText achievementName = rootView.findViewById(R.id.editTextName);
        EditText achievementDesc = rootView.findViewById(R.id.editTextDescription);
        String attributeValue = "";

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse((InputStream) getResources().getLayout(R.layout.add_page));
            document.getDocumentElement().normalize();
            attributeValue = document.getElementById("imageViewPicture").getAttribute("attributeName");

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!hasNoText(achievementName)
                && !hasNoText(achievementDesc)
                && attributeValue != "@android:drawable/ic_menu_gallery") {

        }*/

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> userDataCollected = new HashMap<>();

        String achievementName = ((EditText)(rootView.findViewById(R.id.editTextName))).getText().toString();
        String achievementDesc = ((EditText)(rootView.findViewById(R.id.editTextDescription))).getText().toString();

        userDataCollected.put("Achievement Name", achievementName);
        userDataCollected.put("Achievement Description", achievementDesc);
        userDataCollected.put("Achievement Image", imageUsed);

        db.collection("Users").document(Googlesignin.userEmail).collection(MainActivity.activityType).document(achievementName).set(userDataCollected);

    }

    private boolean hasNoText(EditText text) {
        return (text.getText().toString().isEmpty());
    }

    public void selectPicture() {
        // Check and request permission if needed
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
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
            ImageView imageView = getView().findViewById(R.id.imageViewPicture);
            imageView.setImageURI(selectedImageUri);
            imageUsed = selectedImageUri;
        }
    }
}
package com.example.myapplication.honors;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Googlesignin;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HonorsFragment extends Fragment {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private HonorsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private HonorsViewModel viewModel;

    public static HonorsFragment newInstance() {
        return new HonorsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_honors, container, false);
        viewModel = new ViewModelProvider(this).get(HonorsViewModel.class);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        MainActivity.activityType = "Honors Achievements";

        // Set up RecyclerView
        recyclerView = rootView.findViewById(R.id.recyclerViewHonor);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Initialize the adapter with an empty data set
        adapter = new HonorsAdapter(new ArrayList<String>());
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = rootView.findViewById(R.id.addHonor);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle FAB click in the Fragment
                viewModel.onFabClicked();

                // If you want to navigate to another fragment, you can use Navigation component
                Navigation.findNavController(view).navigate(R.id.add_page);
            }
        });

        toggleVisibility(rootView);

        // Fetch documents
        fetchDocumentIds();

        return rootView;
    }

    public void toggleVisibility(View rootView) {
        Log.d("Item count", "" + adapter.getItemCount());
        if (adapter.getItemCount() != 1) {
            rootView.findViewById(R.id.recyclerViewHonor).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.noContentHonor).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.recyclerViewHonor).setVisibility(View.GONE);
            rootView.findViewById(R.id.noContentHonor).setVisibility(View.VISIBLE);
        }
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HonorsViewModel.class);
        // TODO: Use the ViewModel
    }

    private void fetchDocumentIds() {
        db.collection("Users").document(Googlesignin.userEmail).collection(MainActivity.activityType)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot result) {
                        ArrayList<String> documentIds = new ArrayList<>();
                        for (DocumentSnapshot document : result.getDocuments()) {
                            documentIds.add(document.getId());
                        }

                        // Log the document IDs or do something with them
                        for (String id : documentIds) {
                            Log.d("Document ID", id);
                            adapter.updateData(documentIds);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error getting documents.", e);
                    }
                });
    }
}

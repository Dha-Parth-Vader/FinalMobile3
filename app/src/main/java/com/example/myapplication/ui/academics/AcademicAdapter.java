package com.example.myapplication.ui.academics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Googlesignin;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class AcademicAdapter extends RecyclerView.Adapter<AcademicAdapter.ViewHolder> {
    private ArrayList<String> recyclerData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public FloatingActionButton button;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.academicRecyclerTextView);
            button = itemView.findViewById(R.id.deleteButton);
        }
    }

    public AcademicAdapter(ArrayList<String> newList) {
        this.recyclerData = newList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.academic_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (recyclerData != null) {
            String id = recyclerData.get(position);
            if (id != null) {
                holder.textView.setText(id);
            }
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Users")
                        .document(Googlesignin.userEmail)
                        .collection(MainActivity.activityType)
                        .document(holder.textView.getText().toString())
                        .delete();

                notifyDataSetChanged();
                recyclerData.remove(holder.textView.getText().toString());
            }
        });
    }

    public int getItemCount() {
        if (recyclerData != null) {
            return recyclerData.size();
        } else {
            return -1;
        }
    }

    public void updateData(ArrayList<String> newData) {
        recyclerData = new ArrayList<String>();
        for (int i = 1; i < newData.size(); i++) {
            recyclerData.add(newData.get(i));
        }

        notifyDataSetChanged();
    }

}

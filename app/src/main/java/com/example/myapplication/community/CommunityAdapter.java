package com.example.myapplication.community;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Googlesignin;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {
    private ArrayList<String> recyclerData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public FloatingActionButton button;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.communityRecyclerTextView);
            button = itemView.findViewById(R.id.deleteButtonCommunity);
        }
    }

    public CommunityAdapter(ArrayList<String> newList) {
        this.recyclerData = newList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.community_recycler, parent, false);
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

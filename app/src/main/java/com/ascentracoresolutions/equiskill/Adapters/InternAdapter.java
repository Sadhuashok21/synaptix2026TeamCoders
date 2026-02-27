package com.ascentracoresolutions.equiskill.Adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ascentracoresolutions.equiskill.Getters.Interns;
import com.ascentracoresolutions.equiskill.R;

import java.util.ArrayList;

public class InternAdapter extends RecyclerView.Adapter<InternAdapter.ViewHolder> {

    private final ArrayList<Interns> list;

    public InternAdapter(ArrayList<Interns> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_intern, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Interns interns = list.get(position);

        holder.jobTitle.setText(interns.getTitle());
        holder.companyName.setText(interns.getCompanyName());
        holder.jobDescription.setText(interns.getDescription());



        int score = interns.getMatchScore();

        if(score >= 80){
            holder.matchProgress.setProgressTintList(
                    ColorStateList.valueOf(Color.parseColor("#22C55E"))); // Green
        }
        else if(score >= 50){
            holder.matchProgress.setProgressTintList(
                    ColorStateList.valueOf(Color.parseColor("#F59E0B"))); // Orange
        }
        else{
            holder.matchProgress.setProgressTintList(
                    ColorStateList.valueOf(Color.parseColor("#EF4444"))); // Red
        }
        holder.matchScore.setText(String.valueOf(score));
        holder.matchProgress.setProgress(score);
    }

    @Override
    public int getItemCount() {
        return list.size();   // ✅ IMPORTANT
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView jobTitle, companyName, jobDescription, matchScore;
        ProgressBar matchProgress;
        Button viewDetailsBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            jobTitle = itemView.findViewById(R.id.jobTitle);
            companyName = itemView.findViewById(R.id.companyName);
            jobDescription = itemView.findViewById(R.id.jobDescription);
            matchScore = itemView.findViewById(R.id.matchScore);
            matchProgress = itemView.findViewById(R.id.matchProgress);
            viewDetailsBtn = itemView.findViewById(R.id.viewDetailsBtn);
        }
    }
}
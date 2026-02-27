package com.ascentracoresolutions.equiskill.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ascentracoresolutions.equiskill.Getters.Student;
import com.ascentracoresolutions.equiskill.R;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private final ArrayList<Student> list;

    public StudentAdapter(ArrayList<Student> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Student student = list.get(position);

        holder.name.setText(student.getName());
        holder.score.setText(student.getScore() + "%");

        holder.rank.setText("#" + (position + 1));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, score, rank;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            score = itemView.findViewById(R.id.tvScore);
            rank = itemView.findViewById(R.id.tvRank);
        }
    }
}
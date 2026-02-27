package com.ascentracoresolutions.equiskill.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ascentracoresolutions.equiskill.Getters.Skill;
import com.ascentracoresolutions.equiskill.R;

import java.util.ArrayList;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.ViewHolder> {

    private final ArrayList<Skill> list;
    private final Context context;


    public SkillAdapter(ArrayList<Skill> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SkillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_skills, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillAdapter.ViewHolder holder, int position) {

        Skill skill = list.get(position);

        holder.name.setText(skill.getName());
        if (skill.getLevel() < 2) {

            holder.level.setText("Beginner");
            holder.level.setBackground(ContextCompat.getDrawable(context, R.drawable.border_orange));
        } else if (skill.getLevel() == 3) {

            holder.level.setText("Intermediate");
            holder.level.setBackgroundColor(ContextCompat.getColor(context, R.color.rank_orange));
        } else {
            holder.level.setText("Advanced");
            holder.level.setBackgroundColor(ContextCompat.getColor(context, R.color.button_color));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name, level;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            level = itemView.findViewById(R.id.success);

        }
    }
}

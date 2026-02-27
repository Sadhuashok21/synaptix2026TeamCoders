package com.ascentracoresolutions.equiskill.Admin.Upload;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ascentracoresolutions.equiskill.Fetchers.InsertFetch;
import com.ascentracoresolutions.equiskill.MainActivity;
import com.ascentracoresolutions.equiskill.R;
import com.google.android.material.textfield.TextInputEditText;

import java.net.URLEncoder;
import java.util.ArrayList;

public class SkillUploadFragment extends Fragment {


    TextInputEditText etSkillName;
    Spinner spSkillLevel;
    Button btnAddSkill, btnSaveSkills;
    LinearLayout skillsContainer;

    ArrayList<String> skillNames = new ArrayList<>();
    ArrayList<String> skillLevels = new ArrayList<>();

    String BASE_URL = MainActivity.url + "upload-student-skill/";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skill_upload, container, false);

        etSkillName = view.findViewById(R.id.etSkillName);
        spSkillLevel = view.findViewById(R.id.spSkillLevel);
        btnAddSkill = view.findViewById(R.id.btnAddSkill);
        btnSaveSkills = view.findViewById(R.id.btnSaveSkills);
        skillsContainer = view.findViewById(R.id.skillsContainer);

        // Spinner values
        String[] levels = {"1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                levels
        );
        spSkillLevel.setAdapter(adapter);

        // Add Skill button
        btnAddSkill.setOnClickListener(v -> addSkill());

        // Save Skills button
        btnSaveSkills.setOnClickListener(v -> saveSkillsToServer());

        return view;
    }


    private void addSkill() {

        String skill = etSkillName.getText().toString().trim();
        String level = spSkillLevel.getSelectedItem().toString();

        if (skill.isEmpty()) {
            Toast.makeText(getContext(), "Enter skill name", Toast.LENGTH_SHORT).show();
            return;
        }

        skillNames.add(skill);
        skillLevels.add(level);

        TextView tv = new TextView(getContext());
        tv.setText(skill + " (Level " + level + ")");
        tv.setPadding(10,10,10,10);

        skillsContainer.addView(tv);

        etSkillName.setText("");
    }

    private void saveSkillsToServer() {

        SharedPreferences sp = requireActivity()
                .getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);

        String userId = sp.getString(MainActivity.USER_ID, "");

        for (int i = 0; i < skillNames.size(); i++) {

            try {

                String skill = URLEncoder.encode(skillNames.get(i), "UTF-8");
                String level = skillLevels.get(i);



                InsertFetch insertFetch = new InsertFetch("upload");
                insertFetch.fetchData(MainActivity.url + "upload-student-skill/?name=" + skill + "&level=" + level + "&user_id=" + "Z9EAQOWDYPQN4HBD");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(getContext(), "Skills Uploaded!", Toast.LENGTH_SHORT).show();
    }


}
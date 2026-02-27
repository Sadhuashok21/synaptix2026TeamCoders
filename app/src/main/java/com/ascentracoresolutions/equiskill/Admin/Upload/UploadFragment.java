package com.ascentracoresolutions.equiskill.Admin.Upload;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ascentracoresolutions.equiskill.Fetchers.InsertFetch;
import com.ascentracoresolutions.equiskill.MainActivity;
import com.ascentracoresolutions.equiskill.R;
import com.google.android.material.textfield.TextInputEditText;

import java.net.URLEncoder;
import java.util.ArrayList;

public class UploadFragment extends Fragment {

    TextInputEditText etTitle, etCompany, etDescription;
    Button btnCreate;
    LinearLayout mainLayout;


    ArrayList<String> skillList = new ArrayList<>();
    String BASE_URL = MainActivity.url + "upload-internship/?";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        etTitle = view.findViewById(R.id.etTitle);
        etCompany = view.findViewById(R.id.etCompany);
        etDescription = view.findViewById(R.id.etDescription);
        btnCreate = view.findViewById(R.id.btnCreate);
        mainLayout = view.findViewById(R.id.mainLayout);

        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        btnCreate.setOnClickListener(v -> uploadInternship());


        Button btnAddSkill = view.findViewById(R.id.btnAddSkill);
        LinearLayout skillsContainer = view.findViewById(R.id.skillsContainer);

        btnAddSkill.setOnClickListener(v -> showSkillDialog(skillsContainer));
        return view;
    }


    private void showSkillDialog(LinearLayout skillsContainer) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Skill");

        final EditText input = new EditText(requireContext());
        input.setHint("Enter skill name");

        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {

            String skillName = input.getText().toString().trim();

            if (skillName.isEmpty()) {
                Toast.makeText(getContext(),
                        "Skill name cannot be empty",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            addSkillToContainer(skillsContainer, skillName);
        });

        builder.setNegativeButton("Cancel", null);

        builder.show();
    }


    private void addSkillToContainer(LinearLayout skillsContainer, String skillName) {

        skillList.add(skillName);

        if (skillsContainer.getChildCount() == 1 &&
                skillsContainer.getChildAt(0) instanceof TextView) {

            TextView tv = (TextView) skillsContainer.getChildAt(0);

            if (tv.getText().toString().contains("No skills")) {
                skillsContainer.removeAllViews();
            }
        }

        LinearLayout row = new LinearLayout(requireContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(10, 10, 10, 10);

        TextView skillText = new TextView(requireContext());
        skillText.setText(skillName);
        skillText.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));

        Button removeBtn = new Button(requireContext());
        removeBtn.setText("X");

        removeBtn.setOnClickListener(v -> {
            skillsContainer.removeView(row);
            skillList.remove(skillName);
        });

        row.addView(skillText);
        row.addView(removeBtn);

        skillsContainer.addView(row);
    }
    private void uploadInternship() {

        try {

            btnCreate.setEnabled(false);
            mainLayout.setAlpha(0.5f);

            String title = URLEncoder.encode(etTitle.getText().toString().trim(), "UTF-8");
            String company = URLEncoder.encode(etCompany.getText().toString().trim(), "UTF-8");
            String description = URLEncoder.encode(etDescription.getText().toString().trim(), "UTF-8");

            SharedPreferences sp = requireActivity()
                    .getSharedPreferences(MainActivity.SHARED_PREF, MODE_PRIVATE);

            String userId = sp.getString(MainActivity.USER_ID, "");

            StringBuilder skillParams = new StringBuilder();

            for (String skill : skillList) {
                skillParams.append("&skills=")
                        .append(URLEncoder.encode(skill, "UTF-8"));
            }

            String url = MainActivity.url +
                    "upload-internship/?title=" + title +
                    "&company_name=" + company +
                    "&description=" + description +
                    "&user_id=" + "Z9EAQOWDYPQN4HBD" +
                    skillParams.toString();

            InsertFetch insertFetch = new InsertFetch("upload");
            insertFetch.fetchData(url);

            Toast.makeText(getContext(),
                    "Internship & Skills Uploaded",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
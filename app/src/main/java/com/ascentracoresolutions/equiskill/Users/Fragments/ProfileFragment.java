package com.ascentracoresolutions.equiskill.Users.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ascentracoresolutions.equiskill.Adapters.SkillAdapter;
import com.ascentracoresolutions.equiskill.Fetchers.InsertFetch;
import com.ascentracoresolutions.equiskill.Getters.Skill;
import com.ascentracoresolutions.equiskill.MainActivity;
import com.ascentracoresolutions.equiskill.R;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        RecyclerView skills = view.findViewById(R.id.skills);
        ArrayList<Skill> list = new ArrayList<>();

        SkillAdapter skillAdapter = new SkillAdapter(list, requireContext());
        skills.setAdapter(skillAdapter);

        skills.setLayoutManager(new LinearLayoutManager(requireContext()));
        skills.setHasFixedSize(true);


        InsertFetch insertFetch = new InsertFetch(skillAdapter, list);
        insertFetch.fetchData(MainActivity.url + "skills?user_id=Z9EAQOWDYPQN4HBD");

        return view;
    }
}
package com.ascentracoresolutions.equiskill.Users.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ascentracoresolutions.equiskill.Adapters.InternAdapter;
import com.ascentracoresolutions.equiskill.Fetchers.InsertFetch;
import com.ascentracoresolutions.equiskill.Getters.Interns;
import com.ascentracoresolutions.equiskill.MainActivity;
import com.ascentracoresolutions.equiskill.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recycler);

        ArrayList<Interns> list = new ArrayList<>();

        list.add(new Interns("Frontend Developer Intern",
                "TechCorp",
                "Work with React and TypeScript",
                92));

        list.add(new Interns("Backend Intern",
                "CodeLabs",
                "Django and REST APIs",
                78));

        InternAdapter adapter = new InternAdapter(list);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        InsertFetch insertFetch = new InsertFetch(adapter, list);
        insertFetch.fetchData(MainActivity.url + "internships/");

        Button button = view.findViewById(R.id.check_skills);
        button.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new CheckFragment()).commit();
        });

        return view;
    }
}
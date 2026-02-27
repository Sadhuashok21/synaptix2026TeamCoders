package com.ascentracoresolutions.equiskill.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ascentracoresolutions.equiskill.Adapters.InternAdapter;
import com.ascentracoresolutions.equiskill.Fetchers.InsertFetch;
import com.ascentracoresolutions.equiskill.Getters.Interns;
import com.ascentracoresolutions.equiskill.MainActivity;
import com.ascentracoresolutions.equiskill.R;

import java.util.ArrayList;

public class AHomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler);

        ArrayList<Interns> list = new ArrayList<>();
        InternAdapter adapter = new InternAdapter(list);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        InsertFetch insertFetch = new InsertFetch(adapter, list);
        insertFetch.fetchData(MainActivity.url + "internships/");

        return view;
    }
}
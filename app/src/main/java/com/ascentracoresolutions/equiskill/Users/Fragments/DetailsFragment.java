package com.ascentracoresolutions.equiskill.Users.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ascentracoresolutions.equiskill.R;


public class DetailsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);


        Bundle args = getArguments();
         if (args != null) {
             String int_id = args.getString("int_id");

         }


        return view;
    }
}
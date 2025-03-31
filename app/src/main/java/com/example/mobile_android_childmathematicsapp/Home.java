package com.example.mobile_android_childmathematicsapp;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Home extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        Button button = view.findViewById(R.id.compareButton);
        button.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).loadFragment(new CompareNumbers(), true);
        });

        return view;
    }
}
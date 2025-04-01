package com.example.mobile_android_childmathematicsapp;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Home extends Fragment {

    private Button compareButton, orderButton, composeButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        compareButton = view.findViewById(R.id.compareButton);
        orderButton = view.findViewById(R.id.orderingButton);
        composeButton = view.findViewById(R.id.composingButton);

        compareButton.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).loadFragment(new CompareNumbers(), true);
        });

        orderButton.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).loadFragment(new OrderingNumbers(), true);
        });

        composeButton.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).loadFragment(new ComposingNumbers(), true);
        });


        return view;
    }
}
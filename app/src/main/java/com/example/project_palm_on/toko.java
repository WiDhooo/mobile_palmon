package com.example.project_palm_on;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class toko extends Fragment {

    private Button btnBibit, btnPeralatan, btnPupuk;

    public toko() {
        // Required empty public constructor
    }

    public static toko newInstance(String param1, String param2) {
        toko fragment = new toko();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_toko, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initializing buttons
        btnBibit = view.findViewById(R.id.ttokobibit);
        btnPeralatan = view.findViewById(R.id.ttokoperalatan);
        btnPupuk = view.findViewById(R.id.ttokopupuk);

        // Default fragment
        setActiveButton(btnBibit);
        loadFragment(new fragment_toko_bibit());

        // Set click listeners for each button
        btnBibit.setOnClickListener(v -> {
            setActiveButton(btnBibit);
            loadFragment(new fragment_toko_bibit());
        });

        btnPeralatan.setOnClickListener(v -> {
            setActiveButton(btnPeralatan);
            loadFragment(new fragment_toko_peralatan());
        });

        btnPupuk.setOnClickListener(v -> {
            setActiveButton(btnPupuk);
            loadFragment(new fragment_toko_pupuk());
        });
    }

    private void loadFragment(Fragment fragment) {
        // Load the selected fragment
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.container_toko, fragment)
                .commit();
    }

    private void setActiveButton(Button activeButton) {
        // Reset button styles
        resetButtonStyles();

        // Set active button style
        activeButton.setBackgroundColor(getResources().getColor(R.color.green_login));
        activeButton.setTextColor(Color.WHITE);
    }

    private void resetButtonStyles() {
        // Reset styles for all buttons
        btnBibit.setBackgroundColor(Color.WHITE);
        btnBibit.setTextColor(Color.BLACK);

        btnPeralatan.setBackgroundColor(Color.WHITE);
        btnPeralatan.setTextColor(Color.BLACK);

        btnPupuk.setBackgroundColor(Color.WHITE);
        btnPupuk.setTextColor(Color.BLACK);
    }
}

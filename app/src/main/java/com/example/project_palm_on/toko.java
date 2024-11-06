package com.example.project_palm_on;

<<<<<<< HEAD
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link toko#newInstance} factory method to
 * create an instance of this fragment.
 */
public class toko extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
=======
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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
>>>>>>> dev-zan
    private String mParam1;
    private String mParam2;

    public toko() {
        // Required empty public constructor
    }

<<<<<<< HEAD
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment toko.
     */
    // TODO: Rename and change types and number of parameters
=======
>>>>>>> dev-zan
    public static toko newInstance(String param1, String param2) {
        toko fragment = new toko();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
<<<<<<< HEAD
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
=======
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
>>>>>>> dev-zan
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_toko, container, false);
    }
<<<<<<< HEAD
}
=======

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
>>>>>>> dev-zan

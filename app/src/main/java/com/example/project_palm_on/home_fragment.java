package com.example.project_palm_on;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ImageButton icon_kalkulasi, icon_simulasi, icon_artikel, icon_guide;
    private RecyclerView recyclerViewSlider;
    private SliderAdapter sliderAdapter;
    private List<SliderItem> sliderItems;

    public home_fragment() {
        // Required empty public constructor
    }

    public static home_fragment newInstance(String param1, String param2) {
        home_fragment fragment = new home_fragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        icon_kalkulasi = view.findViewById(R.id.icon_kalkulasi_home);
        icon_guide = view.findViewById(R.id.icon_guide_home);
        icon_artikel = view.findViewById(R.id.icon_artikel_home);
        icon_simulasi = view.findViewById(R.id.icon_simulasi_home);
        recyclerViewSlider = view.findViewById(R.id.recyclerViewSlider);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSlider.setLayoutManager(layoutManager);

        sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem("Keberlanjutan dalam Pertanian Sawit: Tantangan..", "15/10/24", R.drawable.carousel_sawit_1));
        sliderItems.add(new SliderItem("Judul Artikel Lain", "15/10/24", R.drawable.carousel_sawit_1));

        sliderAdapter = new SliderAdapter(getActivity(), sliderItems);
        recyclerViewSlider.setAdapter(sliderAdapter);

        // fungsi tombol kalkulasi
        icon_kalkulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), kalkulasi_page.class);
                startActivity(i);
            }
        });

        // fungsi tombol simulasi
        icon_simulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), simulasi.class);
                startActivity(i);
            }
        });

        // fungsi tombol guide
        icon_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), guide.class);
                startActivity(i);
            }
        });

        // Mengarah ke halaman artikel
        icon_artikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), artikel.class);
                startActivity(i);
            }
        });

        return view;
    }
}

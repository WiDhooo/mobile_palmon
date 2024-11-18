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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        getSliderData();  // Fetch the slider data from the API

        // Set up the adapter for the slider
        sliderAdapter = new SliderAdapter(getActivity(), sliderItems);
        recyclerViewSlider.setAdapter(sliderAdapter);

        // Other button actions
        icon_kalkulasi.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), kalkulasi_page.class);
            startActivity(i);
        });

        icon_simulasi.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), simulasi.class);
            startActivity(i);
        });

        icon_guide.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), guide.class);
            startActivity(i);
        });

        icon_artikel.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), artikel.class);
            startActivity(i);
        });

        return view;
    }

    // Fetch slider data from API
    private void getSliderData() {
        String url = Constants.URL_ARTIKEL;  // Replace with your actual URL

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Loop through the API response and add slider items
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject sliderObject = response.getJSONObject(i);

                                String title = sliderObject.getString("judul");
                                String date = sliderObject.getString("created_at");
                                String description = sliderObject.getString("isi");  // Image URL from API
                                String author = sliderObject.getString("nama_pembuat");
                                String imageURL = sliderObject.getString("gambar");

                                // Add the slider item to the list
                                sliderItems.add(new SliderItem(author, title, description, date, imageURL));
                            }

                            // Notify the adapter that the data has changed
                            sliderAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error parsing slider data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> Toast.makeText(getActivity(), "Error fetching slider data", Toast.LENGTH_SHORT).show()
        );

        // Add the request to the request queue
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
    }
}

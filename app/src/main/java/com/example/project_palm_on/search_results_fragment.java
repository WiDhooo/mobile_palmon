package com.example.project_palm_on;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class search_results_fragment extends Fragment {

    private RecyclerView recyclerView;
    private GuideAdapter guideAdapter;
    private List<GuideItem> guideItems;

    public search_results_fragment() {
        // Required empty public constructor
    }

    // Static method to create an instance of the fragment with the List<GuideItem>
    public static search_results_fragment newInstance(List<GuideItem> guideItems) {
        search_results_fragment fragment = new search_results_fragment();
        Bundle args = new Bundle();
        args.putSerializable("guideItems", (java.io.Serializable) guideItems);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);

        // Retrieve data passed to this fragment
        if (getArguments() != null) {
            guideItems = (List<GuideItem>) getArguments().getSerializable("guideItems");
        }

        // Initialize the RecyclerView and set the adapter
        recyclerView = rootView.findViewById(R.id.recycler_view_search_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        guideAdapter = new GuideAdapter(getContext(), guideItems); // Pass the data to the adapter
        recyclerView.setAdapter(guideAdapter);

        return rootView;
    }
}

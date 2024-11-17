package com.example.project_palm_on;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class pemanenan_fragment_guide extends Fragment {

    private RecyclerView recyclerView;
    private GuideAdapter guideAdapter;
    private List<GuideItem> pemanenanItems;
    private GuideViewModel guideViewModel;

    public pemanenan_fragment_guide() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewModel
        guideViewModel = new ViewModelProvider(requireActivity()).get(GuideViewModel.class);

        // Observe the data in ViewModel
        guideViewModel.getGuideItems().observe(this, guideItems -> {
            if (guideItems != null) {
                pemanenanItems = guideItems;
                setupRecyclerView();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pemanenan_guide, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_guide_pemanenan);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Add Divider
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        // Set Adapter
        // Make sure to pass getContext() as the first argument
        guideAdapter = new GuideAdapter(getContext(), pemanenanItems);
        recyclerView.setAdapter(guideAdapter);

        return view;
    }

    private void setupRecyclerView() {
        if (recyclerView == null) {
            recyclerView = getView().findViewById(R.id.recycler_view_guide_pemanenan);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Pass getContext() to GuideAdapter constructor
        guideAdapter = new GuideAdapter(getContext(), pemanenanItems);
        recyclerView.setAdapter(guideAdapter);
    }
}

package com.example.project_palm_on;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class perawatan_fragment_guide extends Fragment {

    private RecyclerView recyclerView;
    private GuideAdapter guideAdapter;
    private List<GuideItem> perawatanItems;
    private GuideViewModel guideViewModel;

    public perawatan_fragment_guide() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        guideViewModel = new ViewModelProvider(requireActivity()).get(GuideViewModel.class); // Initialize ViewModel

        // Observe changes in guide items
        guideViewModel.getGuideItems().observe(this, guideItems -> {
            if (guideItems != null) {
                perawatanItems = guideItems;
                setupRecyclerView();
            } else {
                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perawatan_guide, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_guide_perawatan);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Add Divider
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        return view;
    }

    private void setupRecyclerView() {
        if (recyclerView == null) {
            recyclerView = getView().findViewById(R.id.recycler_view_guide_perawatan);
        }
        // Use Context along with the List of items
        guideAdapter = new GuideAdapter(getContext(), perawatanItems);  // Context is added here
        recyclerView.setAdapter(guideAdapter);
    }
}

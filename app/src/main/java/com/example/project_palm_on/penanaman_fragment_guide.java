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

public class penanaman_fragment_guide extends Fragment {

    private RecyclerView recyclerView;
    private GuideAdapter guideAdapter;
    private List<GuideItem> penanamanItems;
    private GuideViewModel guideViewModel;

    public penanaman_fragment_guide() {
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
                penanamanItems = guideItems;
                setupRecyclerView();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_penanaman_guide, container, false);

        // Inisialisasi RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_guide_penanaman);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tambahkan DividerItemDecoration
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider); // Menambahkan divider ke RecyclerView

        // Setup Adapter dengan menambahkan Context
        guideAdapter = new GuideAdapter(getContext(), penanamanItems);  // Menambahkan Context di sini
        recyclerView.setAdapter(guideAdapter);

        return view;
    }

    private void setupRecyclerView() {
        recyclerView = getView().findViewById(R.id.recycler_view_guide_penanaman);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        guideAdapter = new GuideAdapter(getContext(), penanamanItems);  // Menambahkan Context di sini
        recyclerView.setAdapter(guideAdapter);
    }
}

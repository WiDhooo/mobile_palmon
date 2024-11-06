package com.example.project_palm_on;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class artikel extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArtikelAdapter artikelAdapter;
    private List<item_artikel> artikelList;
    ImageView ic_kembali_artikel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_artikel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView_artikel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ic_kembali_artikel = findViewById(R.id.icon_kembali_artikel);

        //tombol fungsi kembali ke home

        ic_kembali_artikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(artikel.this, home.class);
                startActivity(i);
            }
        });

        // Initialize data
        artikelList = new ArrayList<>();
        artikelList.add(new item_artikel("Ahmad", "Keberlanjutan dalam Pertanian Sawit: Tantangan..",
                "Industri sawit merupakan salah satu sektor pertanian yang paling penting di Indonesia,..",
                "17 hours ago", R.drawable.image_artikel_1, R.drawable.icon_titik_tiga_artikel));

        artikelAdapter = new ArtikelAdapter(this, artikelList);
        recyclerView.setAdapter(artikelAdapter);
    }
}
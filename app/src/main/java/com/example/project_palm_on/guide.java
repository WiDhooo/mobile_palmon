package com.example.project_palm_on;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class guide extends AppCompatActivity {
    ImageView ic_kembali_guide;
    Button btn_penanaman_guide, btn_perawatan_guide, btn_pemanenan_guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guide);

        // Inset listener to adjust padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        btn_penanaman_guide = findViewById(R.id.button_penanaman_guide);
        btn_perawatan_guide = findViewById(R.id.button_perawatan_guide);
        btn_pemanenan_guide = findViewById(R.id.button_pemanenan_guide);
        ic_kembali_guide = findViewById(R.id.icon_kembali_guide);

        // Back button to return to home activity
        ic_kembali_guide.setOnClickListener(view -> {
            Intent i = new Intent(guide.this, home.class);
            startActivity(i);
        });

        // Set default fragment and active button
        setActiveButton(btn_penanaman_guide);  // Set default active button
        loadFragment(new penanaman_fragment_guide());

        // Set up button click listeners with active/inactive logic
        btn_penanaman_guide.setOnClickListener(v -> {
            setActiveButton(btn_penanaman_guide);
            loadFragment(new penanaman_fragment_guide());
        });

        btn_perawatan_guide.setOnClickListener(v -> {
            setActiveButton(btn_perawatan_guide);
            loadFragment(new perawatan_fragment_guide());
        });

        btn_pemanenan_guide.setOnClickListener(v -> {
            setActiveButton(btn_pemanenan_guide);
            loadFragment(new pemanenan_fragment_guide());
        });
    }

    // Method to set the active button and reset other buttons to inactive
    private void setActiveButton(Button activeButton) {
        // Reset all buttons to inactive style
        resetButtonStyles();

        // Set active button style
        activeButton.setBackgroundColor(getResources().getColor(R.color.green_login)); // Set background color to active (green)
        activeButton.setTextColor(Color.WHITE); // Set text color to white
    }


    private void resetButtonStyles() {

        btn_penanaman_guide.setBackgroundColor(Color.WHITE); // Set background color to white
        btn_penanaman_guide.setTextColor(Color.BLACK); // Set text color to black

        // Inactive style for "Perawatan" button
        btn_perawatan_guide.setBackgroundColor(Color.WHITE);
        btn_perawatan_guide.setTextColor(Color.BLACK);

        // Inactive style for "Pemanenan" button
        btn_pemanenan_guide.setBackgroundColor(Color.WHITE);
        btn_pemanenan_guide.setTextColor(Color.BLACK);
    }

    // Method to load the selected fragment
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_guide, fragment)
                .commit();
    }
}

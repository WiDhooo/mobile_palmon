package com.example.project_palm_on;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class isi_guide extends AppCompatActivity {

    private TextView titleTextView, descriptionTextView;
    private ImageView guideImageView, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_guide);

        // Referensi ke UI
        titleTextView = findViewById(R.id.guide_title);
        descriptionTextView = findViewById(R.id.guide_content);
        guideImageView = findViewById(R.id.guide_image);
        backButton = findViewById(R.id.icon_kembali_isi_guide_guide);

        // Ambil data dari intent
        String title = getIntent().getStringExtra("title");
        String imageUrl = getIntent().getStringExtra("image");
        String description = getIntent().getStringExtra("description");

        // Set data ke UI
        titleTextView.setText(title);
        descriptionTextView.setText(description);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .apply(new RequestOptions().placeholder(R.drawable.carousel_sawit_1))
                    .error(R.drawable.carousel_sawit_1)
                    .into(guideImageView);
        } else {
            guideImageView.setImageResource(R.drawable.carousel_sawit_1);
        }

        // Mengatur button kembali
        backButton.setOnClickListener(v -> onBackPressed());
    }
}

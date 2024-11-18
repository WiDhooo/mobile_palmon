package com.example.project_palm_on;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class isi_artikel extends AppCompatActivity {

    private TextView titleTextView, authorTextView, contentTextView, timeTextView;
    private ImageView articleImageView, Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_isi_artikel);

        // Mengatur padding sistem
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referensi ke UI
        titleTextView = findViewById(R.id.article_title);
        authorTextView = findViewById(R.id.author_name);
        contentTextView = findViewById(R.id.article_content);
        timeTextView = findViewById(R.id.upload_time);
        articleImageView = findViewById(R.id.article_image);
        Back = findViewById(R.id.btn_back_isi_artikel);

        Back.setOnClickListener(v -> onBackPressed());

        // Ambil data dari intent
        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        String content = getIntent().getStringExtra("content");
        String time = getIntent().getStringExtra("time");
        String image = getIntent().getStringExtra("image");

        // Set data ke UI
        titleTextView.setText(title);
        authorTextView.setText(author);
        contentTextView.setText(content);

        // Format waktu menjadi 'X hari yang lalu'
        String formattedTime = TimeUtils.getTimeAgo(time);
        timeTextView.setText(formattedTime);

        // Menampilkan gambar artikel
        Glide.with(this).load(image).into(articleImageView);
    }
}

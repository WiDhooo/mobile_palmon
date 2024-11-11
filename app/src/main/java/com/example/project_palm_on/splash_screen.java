package com.example.project_palm_on;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splash_screen extends AppCompatActivity {
    private int t_delay = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Menangani sistem bar insets agar UI responsif terhadap status bar dan navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Mendapatkan shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userId = sharedPreferences.getString("user_id", null);  // Mengambil user_id

        // Menunda eksekusi untuk memeriksa apakah user_id sudah ada
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userId != null) {
                    // Jika user_id ada, langsung ke halaman beranda
                    Intent intent = new Intent(splash_screen.this, home.class);
                    startActivity(intent);
                    finish(); // Menutup SplashScreen agar tidak bisa kembali
                } else {
                    // Jika user_id tidak ada, arahkan ke halaman registrasi
                    Intent i = new Intent(splash_screen.this, login_user.class);
                    startActivity(i);
                    finish(); // Menutup SplashScreen agar tidak bisa kembali
                }
            }
        }, t_delay); // Delay waktu splash screen selama 3000 ms
    }
}

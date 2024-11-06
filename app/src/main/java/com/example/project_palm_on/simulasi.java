package com.example.project_palm_on;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class simulasi extends AppCompatActivity {
    Button btn_mulai_simulasi, btn_kembali_simulasi_home;
    ImageView icon_kembali_simulasi_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_simulasi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_mulai_simulasi = findViewById(R.id.button_mulai_simulasi);
        btn_kembali_simulasi_home = findViewById(R.id.button_kembali_simulasi);
        icon_kembali_simulasi_home = findViewById(R.id.icon_kembali_simulasi);

        //mengarah ke hasil simulasi
        btn_mulai_simulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(simulasi.this, hasil_simulasi.class);
                startActivity(i);
            }
        });

        //balik ke home dari simulasi
        btn_kembali_simulasi_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(simulasi.this, home.class);
                startActivity(i);
            }
        });

        //icon kembali dari simulasi page ke home
        icon_kembali_simulasi_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(simulasi.this, home.class);
                startActivity(i);
            }
        });
    }
}
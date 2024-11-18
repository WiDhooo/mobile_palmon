package com.example.project_palm_on;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class simulasi extends AppCompatActivity {
    EditText editTextLuasLahan, editTextLokasi, editTextJenisTanah, editTextModal;
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

        editTextLuasLahan = findViewById(R.id.luas_lahan_simulasi);
        editTextLokasi = findViewById(R.id.lokasi_simulasi);
        editTextJenisTanah = findViewById(R.id.jenis_tanah_simulasi);
        editTextModal = findViewById(R.id.modal_simulasi);
        btn_mulai_simulasi = findViewById(R.id.button_mulai_simulasi);
        btn_kembali_simulasi_home = findViewById(R.id.button_kembali_simulasi);
        icon_kembali_simulasi_home = findViewById(R.id.icon_kembali_simulasi);

        //mengarah ke hasil simulasi
        btn_mulai_simulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get input values
                String luasLahan = editTextLuasLahan.getText().toString();
                String lokasi = editTextLokasi.getText().toString();
                String jenisTanah = editTextJenisTanah.getText().toString();
                String modal = editTextModal.getText().toString();

                // Create an Intent to pass the values to HasilSimulasiActivity
                Intent i = new Intent(simulasi.this, hasil_simulasi.class);
                i.putExtra("LUAS_LAHAN", luasLahan);
                i.putExtra("LOKASI", lokasi);
                i.putExtra("JENIS_TANAH", jenisTanah);
                i.putExtra("MODAL", modal);
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
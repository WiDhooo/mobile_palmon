package com.example.project_palm_on;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class detail_produk_toko extends AppCompatActivity {

    private TextView quantityText;
    private ImageButton quantityIncrease;
    private ImageButton quantityDecrease;
    private int quantity = 1;
    Button btn_beli_sekarang_detail_produk; // Set nilai awal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_produk_toko);

        // Inisialisasi komponen untuk pengaturan jumlah produk
        quantityText = findViewById(R.id.quantity_text);
        quantityIncrease = findViewById(R.id.quantity_increase);
        quantityDecrease = findViewById(R.id.quantity_decrease);
        btn_beli_sekarang_detail_produk = findViewById(R.id.button_beli_detail_toko);

        // Tampilkan jumlah awal di TextView
        quantityText.setText(String.valueOf(quantity));

        // Tambah jumlah saat tombol increase diklik
        quantityIncrease.setOnClickListener(v -> {
            quantity++;
            quantityText.setText(String.valueOf(quantity));
        });

        // Kurangi jumlah saat tombol decrease diklik, dengan batas minimal 1
        quantityDecrease.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                quantityText.setText(String.valueOf(quantity));
            }
        });

        btn_beli_sekarang_detail_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(detail_produk_toko.this, order_page.class);
                startActivity(i);
            }
        });
    }
}

package com.example.project_palm_on;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class artikel extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArtikelAdapter artikelAdapter;
    private List<item_artikel> artikelList;
    private ImageView icKembaliArtikel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_artikel);

        // Padding untuk sistem bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView_artikel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        icKembaliArtikel = findViewById(R.id.icon_kembali_artikel);

        // Tombol kembali
        icKembaliArtikel.setOnClickListener(view -> {
            Intent i = new Intent(artikel.this, home.class);
            startActivity(i);
        });



    private void getArtikelData() {
        // URL API untuk mendapatkan data artikel
        String url = Constants.URL_ARTIKEL;  // Ganti dengan URL API yang sesuai

        // Membuat permintaan API menggunakan Volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        // Menyaring data dari JSON response
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject artikelObject = response.getJSONObject(i);

                            String namaAuthor = artikelObject.getString("nama_pembuat");
                            String judulArtikel = artikelObject.getString("judul");
                            String deskripsiArtikel = artikelObject.getString("isi");
                            String waktuUpload = artikelObject.getString("created_at");
                            String gambarUrl = artikelObject.getString("gambar");
                            // URL gambar artikel

                            // Menambahkan item artikel ke dalam daftar
                            artikelList.add(new item_artikel(namaAuthor, judulArtikel, deskripsiArtikel, waktuUpload, gambarUrl ));
                        }

                        // Menghubungkan RecyclerView dengan Adapter
                        artikelAdapter = new ArtikelAdapter(artikel.this, artikelList);
                        recyclerView.setAdapter(artikelAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(artikel.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(artikel.this, "Error fetching data", Toast.LENGTH_SHORT).show()
        );

        // Menambahkan permintaan ke request queue
        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
}

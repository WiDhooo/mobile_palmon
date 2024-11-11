package com.example.project_palm_on;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ubah_kalkulasi extends AppCompatActivity {

    private EditText tglPanenEditText, hargaTBSEditText, beratTotalTBSEditText, potonganTimbanganEditText,
            upahPanenEditText, biayaTransportasiEditText, biayaLainnyaEditText;
    private Button buttonSimpanPerubahan, buttonKembali;
    private String kalkulasiId;
    private String userId; // Variabel userId diambil dari Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ubah_kalkulasi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi EditText
        tglPanenEditText = findViewById(R.id.tgl_panen_kalkulasi_ubah);
        hargaTBSEditText = findViewById(R.id.harga_tbs_kalkulasi_ubah);
        beratTotalTBSEditText = findViewById(R.id.berat_total_tbs_kalkulasi_ubah);
        potonganTimbanganEditText = findViewById(R.id.potongan_timbangan_kalkulasi_ubah);
        upahPanenEditText = findViewById(R.id.upah_panen_kalkulasi_ubah);
        biayaTransportasiEditText = findViewById(R.id.biaya_transportasi_kalkulasi_ubah);
        biayaLainnyaEditText = findViewById(R.id.biaya_lainnya_kalkulasi_ubah);

        // Ambil data dari Intent
        kalkulasiId = getIntent().getStringExtra("KALKULASI_ID");
        userId = getIntent().getStringExtra("USER_ID"); // Ambil user_id dari Intent
        String tglPanen = getIntent().getStringExtra("tgl_panen");
        String hargaTbs = getIntent().getStringExtra("harga_tbs");
        String beratTotalTbs = getIntent().getStringExtra("berat_total_tbs");
        String potonganTimbangan = getIntent().getStringExtra("potongan_timbangan");
        String upahPanen = getIntent().getStringExtra("upah_panen");
        String biayaTransportasi = getIntent().getStringExtra("biaya_transportasi");
        String biayaLainnya = getIntent().getStringExtra("biaya_lainnya");

        // Set data ke EditText
        tglPanenEditText.setText(tglPanen);
        hargaTBSEditText.setText(hargaTbs);
        beratTotalTBSEditText.setText(beratTotalTbs);
        potonganTimbanganEditText.setText(potonganTimbangan);
        upahPanenEditText.setText(upahPanen);
        biayaTransportasiEditText.setText(biayaTransportasi);
        biayaLainnyaEditText.setText(biayaLainnya);

        // Inisialisasi Button
        buttonSimpanPerubahan = findViewById(R.id.button_simpan_ubah);
        buttonKembali = findViewById(R.id.button_kembali_catat_kalkulasi_ubah);

        // Aksi Simpan Perubahan
        buttonSimpanPerubahan.setOnClickListener(v -> updateKalkulasi());

        // Aksi Kembali
        buttonKembali.setOnClickListener(v -> finish());
    }

    private void updateKalkulasi() {
        if (userId == null || kalkulasiId == null) {
            Toast.makeText(this, "User ID atau Kalkulasi ID tidak ditemukan", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = Constants.URL_KALKULASI + "/" + userId + "/" + kalkulasiId;

        JSONObject jsonBody = new JSONObject();
        try {
            // Memasukkan data ke dalam JSONObject
            jsonBody.put("tgl_panen", tglPanenEditText.getText().toString());
            jsonBody.put("harga_tbs", Double.parseDouble(hargaTBSEditText.getText().toString()));
            jsonBody.put("berat_total_tbs", Double.parseDouble(beratTotalTBSEditText.getText().toString()));
            jsonBody.put("potongan_timbangan", Double.parseDouble(potonganTimbanganEditText.getText().toString()));
            jsonBody.put("upah_panen", Double.parseDouble(upahPanenEditText.getText().toString()));
            jsonBody.put("biaya_transportasi", Double.parseDouble(biayaTransportasiEditText.getText().toString()));
            jsonBody.put("biaya_lainnya", Double.parseDouble(biayaLainnyaEditText.getText().toString()));

            // Membuat request JSON untuk memperbarui data
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                    response -> {
                        // Tindakan setelah berhasil memperbarui data
                        Toast.makeText(ubah_kalkulasi.this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK); // Memberi tanda sukses ke hasil_kalkulasi
                        Intent intent = new Intent(ubah_kalkulasi.this, hasil_kalkulasi.class);
                        intent.putExtra("KALKULASI_ID", kalkulasiId);  // Mengirim ID kalkulasi
                        intent.putExtra("USER_ID", userId);  // Mengirim userId
                        startActivity(intent);
                    },
                    error -> {
                        // Tindakan ketika ada error
                        Log.e("API_ERROR", error.toString());
                        Toast.makeText(ubah_kalkulasi.this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show();
                    }
            );

            // Menambahkan request ke request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal membuat JSON", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "Input tidak valid, pastikan angka dimasukkan dengan benar", Toast.LENGTH_SHORT).show();
        }
    }
}

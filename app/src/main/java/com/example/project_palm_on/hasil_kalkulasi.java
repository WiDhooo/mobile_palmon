package com.example.project_palm_on;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class hasil_kalkulasi extends AppCompatActivity {
    private TextView tanggalPanenValue, totalHasilBersihValue, totalPendapatanValue,
            beratKotorValue, hargaTBSValue, potonganTaraValue, beratBersihValue,
            totalPengeluaranValue, biayaUpahPanenValue, biayaTransportasiValue,
            biayaLainnyaValue;
    private Button buttonHapus, buttonUbahData, buttonSimpan;
    private ImageView iconKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_kalkulasi);

        // Inisialisasi TextView
        tanggalPanenValue = findViewById(R.id.tanggal_panen_value);
        totalHasilBersihValue = findViewById(R.id.total_hasil_bersih_value);
        totalPendapatanValue = findViewById(R.id.total_pendapatan_value);
        beratKotorValue = findViewById(R.id.berat_kotor_value);
        hargaTBSValue = findViewById(R.id.hargatbsvalue);
        potonganTaraValue = findViewById(R.id.potongantaravalue);
        beratBersihValue = findViewById(R.id.beratbersihvalue);
        totalPengeluaranValue = findViewById(R.id.totalpengeluaranvalue);
        biayaUpahPanenValue = findViewById(R.id.biayaupahpanenvalue);
        biayaTransportasiValue = findViewById(R.id.biayatransportasivalue);
        biayaLainnyaValue = findViewById(R.id.biayalainnyavalue);

        // Inisialisasi Button
        buttonHapus = findViewById(R.id.button_hapus_kalkulasi);
        buttonUbahData = findViewById(R.id.button_ubah_data_kalkulasi);
//        buttonSimpan = findViewById(R.id.button_simpan_kalkulasi);
        iconKembali = findViewById(R.id.icon_kembali_kalkulasi);

        // Ambil ID dari Intent dan panggil fungsi untuk mengambil kalkulasi
        String kalkulasiId = getIntent().getStringExtra("KALKULASI_ID");
        if (kalkulasiId != null) {
            fetchKalkulasi(kalkulasiId);
            buttonHapus.setOnClickListener(v -> deleteKalkulasi(kalkulasiId));
        } else {
            Toast.makeText(this, "ID kalkulasi tidak ditemukan", Toast.LENGTH_SHORT).show();
        }

        // Aksi untuk kembali
        iconKembali.setOnClickListener(v -> finish());

    }

    private void deleteKalkulasi(String id) {
        String url = Constants.URL_KALKULASI + "/" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    // Tindakan ketika delete berhasil
                    Toast.makeText(hasil_kalkulasi.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(hasil_kalkulasi.this, kalkulasi_page.class); // Kembali ke layar sebelumnya setelah berhasil dihapus
                    startActivity(i);
                },
                error -> {
                    // Tindakan ketika ada kesalahan
                    Log.e("DELETE_ERROR", error.toString());
                    Toast.makeText(hasil_kalkulasi.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                }
        );

        // Menambahkan request ke queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void fetchKalkulasi(String id) {
        String url = Constants.URL_KALKULASI + "/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Ambil nilai berat total TBS dan upah panen
                        double beratTotalTBS = response.optDouble("berat_total_tbs", 0);
                        double upahPanen = response.optDouble("upah_panen", 0);
                        double potonganTaraPersentase = response.optDouble("potongan_timbangan", 0);

                        // Hitung total upah panen yang dikali berat total TBS
                        double totalUpahPanen = beratTotalTBS * upahPanen;
                        double potonganTara = beratTotalTBS * (potonganTaraPersentase / 100);

                        // Format berat dengan Kg
                        String beratKotor = String.format("%.0f Kg", response.optDouble("berat_total_tbs", 0));
                        String beratBersih = String.format("%.0f Kg", response.optDouble("berat_bersih", 0));

                        // Format angka dengan Rp. di depan
                        String totalPendapatan = String.format("Rp. %.0f", response.optDouble("total_pendapatan", 0));
                        String totalPengeluaran = String.format("Rp. %.0f", response.optDouble("total_pengeluaran", 0));
                        String upahTransportasi = String.format("Rp. %.0f", response.optDouble("biaya_transportasi", 0));
                        String biayaLainnya = String.format("Rp. %.0f", response.optDouble("biaya_lainnya", 0));
                        String hargaTbs = String.format("Rp. %.0f", response.optDouble("harga_tbs", 0));

                        // Set nilai ke TextView
                        tanggalPanenValue.setText(response.optString("tgl_panen", "-"));
                        totalPendapatanValue.setText(totalPendapatan);
                        totalHasilBersihValue.setText(String.valueOf(response.optDouble("total_hasil_bersih", 0)));
                        beratKotorValue.setText(beratKotor);
                        hargaTBSValue.setText(hargaTbs);
                        potonganTaraValue.setText(String.format("%.0f Kg", potonganTara));
                        beratBersihValue.setText(beratBersih);
                        totalPengeluaranValue.setText(totalPengeluaran);
                        biayaUpahPanenValue.setText(String.format("Rp. %.0f", totalUpahPanen));
                        biayaTransportasiValue.setText(upahTransportasi);
                        biayaLainnyaValue.setText(biayaLainnya);

                        buttonUbahData.setOnClickListener(v -> {
                            Intent intent = new Intent(hasil_kalkulasi.this, ubah_kalkulasi.class);
                            intent.putExtra("KALKULASI_ID", id);
                            intent.putExtra("tgl_panen", tanggalPanenValue.getText().toString());
                            intent.putExtra("harga_tbs", hargaTBSValue.getText().toString().replace("Rp. ", ""));
                            intent.putExtra("berat_total_tbs", beratKotorValue.getText().toString().replace(" Kg", ""));
                            intent.putExtra("potongan_timbangan", String.valueOf(potonganTaraPersentase).replace(".0", ""));
                            intent.putExtra("upah_panen", String.valueOf(upahPanen).replace(".0", ""));
                            intent.putExtra("biaya_transportasi", biayaTransportasiValue.getText().toString().replace("Rp. ", ""));
                            intent.putExtra("biaya_lainnya", biayaLainnyaValue.getText().toString().replace("Rp. ", ""));
                            startActivity(intent);
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API_ERROR", error.toString());
                        Toast.makeText(hasil_kalkulasi.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}
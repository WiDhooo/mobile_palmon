package com.example.project_palm_on;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;

public class hasil_kalkulasi extends AppCompatActivity {
    private TextView tanggalPanenValue, totalHasilBersihValue, totalPendapatanValue,
            beratKotorValue, hargaTBSValue, potonganTaraValue, beratBersihValue,
            totalPengeluaranValue, biayaUpahPanenValue, biayaTransportasiValue,
            biayaLainnyaValue;
    private Button buttonHapus, buttonUbahData;
    private ImageView iconKembali;
    private double potonganTaraPersentase, upahPanen;
    private String userId;

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
        iconKembali = findViewById(R.id.icon_kembali_kalkulasi);

        // Mengambil user_id dari Intent
        userId = getIntent().getStringExtra("USER_ID");  // Ambil userId yang diteruskan dari KalkulasiAdapter
        if (userId == null) {
            Toast.makeText(this, "User ID tidak ditemukan. Silakan login.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ambil ID kalkulasi dari Intent
        String kalkulasiId = getIntent().getStringExtra("KALKULASI_ID");
        if (kalkulasiId != null) {
            fetchKalkulasi(kalkulasiId, userId);
            buttonHapus.setOnClickListener(v -> deleteKalkulasi(kalkulasiId, userId));
        } else {
            Toast.makeText(this, "ID kalkulasi tidak ditemukan", Toast.LENGTH_SHORT).show();
        }

        // Aksi untuk kembali
        iconKembali.setOnClickListener(view -> {
            Intent i = new Intent(hasil_kalkulasi.this, kalkulasi_page.class);
            startActivity(i);
        });

        // Aksi untuk tombol Ubah Data
        buttonUbahData.setOnClickListener(v -> {
            // Tidak perlu mendeklarasikan kalkulasiId lagi di sini, langsung gunakan yang sudah ada
            if (kalkulasiId != null && userId != null) {
                // Membuat Intent untuk berpindah ke activity ubah_kalkulasi
                Intent intent = new Intent(hasil_kalkulasi.this, ubah_kalkulasi.class);
                intent.putExtra("KALKULASI_ID", kalkulasiId); // Mengirim ID kalkulasi
                intent.putExtra("USER_ID", userId); // Mengirim ID pengguna

                // Meneruskan data kalkulasi yang diperlukan ke activity ubah_kalkulasi
                intent.putExtra("tgl_panen", tanggalPanenValue.getText().toString());
                intent.putExtra("harga_tbs", hargaTBSValue.getText().toString().replace("Rp. ", "").replace(",", "").replace(" /kg", "").replace(".00", ""));
                intent.putExtra("berat_total_tbs", beratKotorValue.getText().toString().replace(" Kg", ""));
                intent.putExtra("potongan_timbangan", String.valueOf(potonganTaraPersentase).replace(".0", ""));
                intent.putExtra("upah_panen", String.valueOf(upahPanen).replace("Rp. ", "").replace(",", "").replace(".0", ""));
                intent.putExtra("biaya_transportasi", biayaTransportasiValue.getText().toString().replace("Rp. ", "").replace(",", "").replace(".00", ""));
                intent.putExtra("biaya_lainnya", biayaLainnyaValue.getText().toString().replace("Rp. ", "").replace(",", "").replace(".00", ""));

                // Memulai activity ubah_kalkulasi
                startActivity(intent);
            } else {
                Toast.makeText(hasil_kalkulasi.this, "ID kalkulasi atau User ID tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteKalkulasi(String id, String userId) {
        String url = Constants.URL_KALKULASI + "/" + userId + "/" + id;

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

    private void fetchKalkulasi(String id, String userId) {
        String url = Constants.URL_KALKULASI + "/" + userId + "/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    // Ambil nilai-nilai yang diperlukan dari JSON API
                    double beratTotalTBS = response.optDouble("berat_total_tbs", 0); // Misalnya, berat total TBS
                    upahPanen = response.optDouble("upah_panen", 0); // Misalnya, upah per Kg TBS
                    potonganTaraPersentase = response.optDouble("potongan_timbangan", 0); // Potongan tara dalam persen

                    // Hitung total upah panen berdasarkan berat TBS
                    double totalUpahPanen = beratTotalTBS * upahPanen;

                    // Hitung potongan tara berdasarkan persentase
                    double potonganTara = beratTotalTBS * (potonganTaraPersentase / 100);

                    // Format berat dalam Kg
                    String beratKotor = String.format("%.0f Kg", beratTotalTBS);
                    String beratBersih = String.format("%.0f Kg", response.optDouble("berat_bersih", 0));

                    // Format angka dengan Rp. di depan
                    String totalPendapatan = formatAngka(response.optDouble("total_pendapatan", 0));
                    String totalPengeluaran = formatAngka(response.optDouble("total_pengeluaran", 0));

                    // Menampilkan data di UI
                    tanggalPanenValue.setText(response.optString("tgl_panen"));
                    beratKotorValue.setText(beratKotor);
                    totalHasilBersihValue.setText(formatAngka(response.optDouble("total_hasil_bersih", 0)));
                    hargaTBSValue.setText(formatAngka(response.optDouble("harga_tbs", 0)) + " /kg");
                    potonganTaraValue.setText(String.format("%.0f Kg", potonganTara));
                    beratBersihValue.setText(beratBersih);
                    totalPendapatanValue.setText(totalPendapatan);
                    totalPengeluaranValue.setText(totalPengeluaran);
                    biayaUpahPanenValue.setText(formatAngka(totalUpahPanen));
                    biayaTransportasiValue.setText(formatAngka(response.optDouble("biaya_transportasi", 0)));
                    biayaLainnyaValue.setText(formatAngka(response.optDouble("biaya_lainnya", 0)));
                },
                error -> {
                    // Tindakan ketika ada error
                    Log.e("ERROR", error.toString());
                    Toast.makeText(hasil_kalkulasi.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
        );

        // Menambahkan request ke queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    // Membuat method untuk memformat angka dengan pemisah ribuan
    private String formatAngka(double angka) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");  // Pemisah ribuan dengan titik
        return "Rp. " + decimalFormat.format(angka);  // Format angka dengan "Rp."
    }
}

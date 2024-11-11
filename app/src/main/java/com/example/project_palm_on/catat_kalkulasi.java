package com.example.project_palm_on;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class catat_kalkulasi extends AppCompatActivity implements View.OnClickListener {

    EditText tglPanen, hargaTbs, beratTotalTbs, potonganTimbangan, upahPanen, biayaTransportasi, biayaLainnya;
    Button buttonHitung, buttonKembali;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catat_kalkulasi);

        tglPanen = findViewById(R.id.tgl_panen_kalkulasi);
        hargaTbs = findViewById(R.id.harga_tbs_kalkulasi);
        beratTotalTbs = findViewById(R.id.berat_total_tbs_kalkulasi);
        potonganTimbangan = findViewById(R.id.potongan_timbangan_kalkulasi);
        upahPanen = findViewById(R.id.upah_panen_kalkulasi);
        biayaTransportasi = findViewById(R.id.biaya_transportasi_kalkulasi);
        biayaLainnya = findViewById(R.id.biaya_lainnya_kalkulasi);
        buttonHitung = findViewById(R.id.button_hitung_catat_kalkulasi);
        buttonKembali = findViewById(R.id.button_kembali_catat_kalkulasi);

        progressDialog = new ProgressDialog(this);

        buttonHitung.setOnClickListener(this);
        buttonKembali.setOnClickListener(this);
    }

    private void hitungHasil() {
        // Mengambil ID user dari SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userId = sharedPreferences.getString("user_id", null);

        if (userId == null) {
            Toast.makeText(this, "User ID tidak ditemukan. Pastikan sudah login.", Toast.LENGTH_SHORT).show();
            return;
        }

        String tglpanen = tglPanen.getText().toString().trim();
        String hargatbs = hargaTbs.getText().toString().trim();
        String berattotaltbs = beratTotalTbs.getText().toString().trim();
        String potongantimbangan = potonganTimbangan.getText().toString().trim();
        String upahpanen = upahPanen.getText().toString().trim();
        String biayatransportasi = biayaTransportasi.getText().toString().trim();
        String biayalainnya = biayaLainnya.getText().toString().trim();

        // Menampilkan progress dialog
        progressDialog.setMessage("Menghitung Data...");
        progressDialog.show();

        // Membuat request ke API untuk menyimpan data kalkulasi
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_KALKULASI + "/" + userId,  // Menambahkan id_user pada URL
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            // Menampilkan pesan sukses dari response API
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            // Setelah berhasil, navigasi ke halaman kalkulasi page
                            Intent i = new Intent(catat_kalkulasi.this, kalkulasi_page.class);
                            startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // Menambahkan parameter untuk kalkulasi
                params.put("tgl_panen", tglpanen);
                params.put("harga_tbs", hargatbs);
                params.put("berat_total_tbs", berattotaltbs);
                params.put("potongan_timbangan", potongantimbangan);
                params.put("upah_panen", upahpanen);
                params.put("biaya_transportasi", biayatransportasi);
                params.put("biaya_lainnya", biayalainnya);
                return params;
            }
        };

        // Menambahkan request ke queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_hitung_catat_kalkulasi) {
            hitungHasil();
            // Setelah menghitung, navigasi ke halaman kalkulasi page
            Intent i = new Intent(catat_kalkulasi.this, kalkulasi_page.class);
            startActivity(i);
        } else if (view.getId() == R.id.button_kembali_catat_kalkulasi) {
            Intent i = new Intent(catat_kalkulasi.this, kalkulasi_page.class);
            startActivity(i);
        }
    }
}

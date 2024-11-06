package com.example.project_palm_on;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        // Optional: Apply edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_hitung_catat_kalkulasi) {
            final Dialog dialog = new Dialog(catat_kalkulasi.this);
            dialog.setContentView(R.layout.dv_hitungkalkulasi_confirm);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent); // Set background transparan
            }
            dialog.show();

            Button confirm = dialog.findViewById(R.id.btn_ya_kalkulasi);
            confirm.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green_login)));
            Button back = dialog.findViewById(R.id.btn_kembali_kalkulasi);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hitungHasil();
                    dialog.dismiss();
                    Intent i = new Intent(catat_kalkulasi.this, kalkulasi_page.class);
                    startActivity(i);
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        } else if (view.getId() == R.id.button_kembali_catat_kalkulasi) {
            final Dialog dialog = new Dialog(catat_kalkulasi.this);
            dialog.setContentView(R.layout.dv_hitungkalkulasi_cancel);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent); // Set background transparan
            }
            dialog.show();

            Button confirm = dialog.findViewById(R.id.btn_lanjutkan_kalkulasi);
            confirm.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green_login)));
            Button back = dialog.findViewById(R.id.btn_batal_kalkulasi);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Intent i = new Intent(catat_kalkulasi.this, kalkulasi_page.class);
                    startActivity(i);
                }
            });
        }
    }

    private void hitungHasil() {
        String tglpanen = tglPanen.getText().toString().trim();
        String hargatbs = hargaTbs.getText().toString().trim();
        String berattotaltbs = beratTotalTbs.getText().toString().trim();
        String potongantimbangan = potonganTimbangan.getText().toString().trim();
        String upahpanen = upahPanen.getText().toString().trim();
        String biayatransportasi = biayaTransportasi.getText().toString().trim();
        String biayalainnya = biayaLainnya.getText().toString().trim();

        progressDialog.setMessage("Menghitung Data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_KALKULASI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

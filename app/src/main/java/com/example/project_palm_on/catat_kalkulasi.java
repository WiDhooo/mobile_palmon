package com.example.project_palm_on;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
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

        // Tanggal Picker
        tglPanen.setOnClickListener(v -> showDatePicker());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_hitung_catat_kalkulasi) {
            if (validateInput()) {
                showConfirmationDialog(true);
            } else {
                Toast.makeText(this, "Perbaiki input sebelum melanjutkan", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.button_kembali_catat_kalkulasi) {
            showConfirmationDialog(false);
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new android.app.DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String date = year + "-" + (month + 1) + "-" + dayOfMonth;
            tglPanen.setText(date);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showConfirmationDialog(boolean isCalculateAction) {
        final Dialog dialog = new Dialog(catat_kalkulasi.this);
        dialog.setContentView(isCalculateAction ? R.layout.dv_hitungkalkulasi_confirm : R.layout.dv_hitungkalkulasi_cancel);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        dialog.show();

        Button confirmButton = dialog.findViewById(isCalculateAction ? R.id.btn_ya_kalkulasi : R.id.btn_lanjutkan_kalkulasi);
        Button cancelButton = dialog.findViewById(isCalculateAction ? R.id.btn_kembali_kalkulasi : R.id.btn_batal_kalkulasi);

        confirmButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green_login)));
        confirmButton.setOnClickListener(v -> {
            dialog.dismiss();
            if (isCalculateAction) {
                hitungHasil();
                Intent intent = new Intent(catat_kalkulasi.this, kalkulasi_page.class);
                startActivity(intent);
            } else {
                finish();
            }
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());
    }

    private boolean validateInput() {
        boolean isValid = true;

        String tglpanen = tglPanen.getText().toString().trim();
        String hargatbs = hargaTbs.getText().toString().trim();
        String berattotaltbs = beratTotalTbs.getText().toString().trim();
        String potongantimbangan = potonganTimbangan.getText().toString().trim();
        String upahpanen = upahPanen.getText().toString().trim();
        String biayatransportasi = biayaTransportasi.getText().toString().trim();
        String biayalainnya = biayaLainnya.getText().toString().trim();

        if (tglpanen.isEmpty()) {
            tglPanen.setError("Tanggal panen tidak boleh kosong");
            isValid = false;
        }

        if (hargatbs.isEmpty() || !hargatbs.matches("\\d+(\\.\\d+)?")) {
            hargaTbs.setError("Harga TBS harus berupa angka");
            isValid = false;
        }

        if (berattotaltbs.isEmpty() || !berattotaltbs.matches("\\d+(\\.\\d+)?")) {
            beratTotalTbs.setError("Berat total TBS harus berupa angka");
            isValid = false;
        }

        if (potongantimbangan.isEmpty() || !potongantimbangan.matches("\\d+(\\.\\d+)?")) {
            potonganTimbangan.setError("Potongan timbangan harus berupa angka");
            isValid = false;
        }

        if (upahpanen.isEmpty() || !upahpanen.matches("\\d+(\\.\\d+)?")) {
            upahPanen.setError("Upah panen harus berupa angka");
            isValid = false;
        }

        if (biayatransportasi.isEmpty() || !biayatransportasi.matches("\\d+(\\.\\d+)?")) {
            biayaTransportasi.setError("Biaya transportasi harus berupa angka");
            isValid = false;
        }

        if (biayalainnya.isEmpty() || !biayalainnya.matches("\\d+(\\.\\d+)?")) {
            biayaLainnya.setError("Biaya lainnya harus berupa angka");
            isValid = false;
        }

        return isValid;
    }

    private void hitungHasil() {
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

        progressDialog.setMessage("Menghitung Data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_KALKULASI + "/" + userId,
                response -> {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressDialog.hide();
                    Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }) {
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

package com.example.project_palm_on;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class hasil_simulasi extends AppCompatActivity {
    TextView hasilLuasLahan, hasilLokasi, hasilJenisTanah, hasilModal, hasilSkalaKeseluruhan, banyakSawit;
    private static final String API_KEY = "b526e624987d5a329aaeb193943e4128";
    private String jenisTanahInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hasil_simulasi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        hasilLuasLahan = findViewById(R.id.hasil_luas_lahan_simulasi);
        hasilLokasi = findViewById(R.id.lokasi_hasil_simulasi);
        hasilJenisTanah = findViewById(R.id.jenis_tanah_simulasi);
        hasilModal = findViewById(R.id.modal_hasil_simulasi);
        hasilSkalaKeseluruhan = findViewById(R.id.hasil_skor_tanah);
        banyakSawit = findViewById(R.id.hasil_banyak_kelapa_sawit);

        // Ambil data dari Intent
        Intent intent = getIntent();
        String luasLahanInput = intent.getStringExtra("LUAS_LAHAN");
        String lokasiInput = intent.getStringExtra("LOKASI");
        jenisTanahInput = intent.getStringExtra("JENIS_TANAH");
        String modalInput = intent.getStringExtra("MODAL");

        // Tampilkan data yang diterima
        hasilLuasLahan.setText(luasLahanInput+ "hektare");
        hasilLokasi.setText(lokasiInput);
        hasilJenisTanah.setText(jenisTanahInput);
        hasilModal.setText("Rp. " + modalInput);

        // Hitung banyak kelapa sawit berdasarkan luas lahan
        calculateBanyakSawit(luasLahanInput);

        // Ambil data cuaca berdasarkan lokasi
        String cityName = lokasiInput;
        getWeatherData(cityName); // Ambil data cuaca berdasarkan lokasi
    }

    // Fungsi untuk menghitung banyak kelapa sawit
    private void calculateBanyakSawit(String luasLahanInput) {
        try {
            // Konversi luas lahan dari hektar ke meter persegi
            double luasLahan = Double.parseDouble(luasLahanInput);
            double luasLahanMeter = luasLahan * 10000; // 1 hektar = 10,000 meter persegi

            // Hitung banyak kelapa sawit (dibagi 8 meter persegi per pohon)
            double banyakKelapaSawit = luasLahanMeter / 8;
            int banyakKelapaSawitInt = (int) banyakKelapaSawit;

            // Tampilkan jumlah kelapa sawit
            banyakSawit.setText("Dengan luas lahan sebesar " + luasLahanInput + " hektar, maka akan diperlukan " + banyakKelapaSawitInt + " buah kelapa sawit.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error in calculating the number of palm trees", Toast.LENGTH_SHORT).show();
        }
    }

    // Fungsi untuk mendapatkan data cuaca
    private void getWeatherData(String cityName) {
        // URL OpenWeatherMap API
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + API_KEY;

        // Membuat Request menggunakan Volley
        JsonObjectRequest weatherRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Mengambil data cuaca dari respons JSON
                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject weatherObject = weatherArray.getJSONObject(0);
                            String description = weatherObject.getString("description");

                            // Mengambil suhu dan kelembapan dari respons
                            JSONObject mainObject = response.getJSONObject("main");
                            double temperatureKelvin = mainObject.getDouble("temp");
                            double temperatureCelsius = temperatureKelvin - 273.15; // Konversi ke Celsius
                            double humidity = mainObject.getDouble("humidity");

                            // Mengambil kecepatan angin dan awan dari respons
                            JSONObject windObject = response.getJSONObject("wind");
                            double windSpeed = windObject.getDouble("speed");
                            JSONObject cloudsObject = response.getJSONObject("clouds");
                            int cloudiness = cloudsObject.getInt("all");

                            // Tentukan kecocokan suhu, kelembapan, kecepatan angin, dan cuaca
                            int suhuSkor = getSuhuSkor(temperatureCelsius);
                            int kelembapanSkor = getKelembapanSkor(humidity);
                            int kecepatanAnginSkor = getKecepatanAnginSkor(windSpeed);
                            int cuacaSkor = getCuacaSkor(cloudiness);
                            int jenisTanahSkor = getJenisTanahSkor(jenisTanahInput);

                            int totalSkor = suhuSkor + kelembapanSkor + kecepatanAnginSkor + cuacaSkor + jenisTanahSkor;
                            int skalaKeseluruhan = totalSkor / 5;

                            String deskripsiSkor;
                            switch (skalaKeseluruhan) {
                                case 5:
                                    deskripsiSkor = "Sangat Baik";
                                    break;
                                case 4:
                                    deskripsiSkor = "Baik";
                                    break;
                                case 3:
                                    deskripsiSkor = "Cukup Baik";
                                    break;
                                case 2:
                                    deskripsiSkor = "Kurang Baik";
                                    break;
                                case 1:
                                    deskripsiSkor = "Tidak Baik";
                                    break;
                                default:
                                    deskripsiSkor = "Tidak Diketahui";
                                    break;
                            }


                            // Menampilkan hasil
                            hasilSkalaKeseluruhan.setText("Berdasarkan data yang telah diinput, penilaian tanah yang akan digunakan untuk menanam kelapa sawit sebesar "
                                    + skalaKeseluruhan + " (" + deskripsiSkor + ")");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(hasil_simulasi.this, "Error parsing weather data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("WeatherError", "Error fetching weather data: " + error.getMessage());
                        Toast.makeText(hasil_simulasi.this, "Error fetching weather data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Menambahkan request ke request queue
        Volley.newRequestQueue(this).add(weatherRequest);
    }

    // Fungsi untuk menentukan skor jenis tanah
    private int getJenisTanahSkor(String jenisTanah) {
        switch (jenisTanah) {
            case "Lempung":
                return 5;
            case "Pasir":
                return 3;
            case "Gambut":
                return 1;
            default:
                return 4;
        }
    }

    // Fungsi untuk menentukan skor suhu
    private int getSuhuSkor(double temperature) {
        if (temperature >= 35) {
            return 1;
        } else if (temperature >= 30) {
            return 2;
        } else if (temperature >= 28) {
            return 3;
        } else if (temperature >= 24) {
            return 4;
        } else {
            return 5;
        }
    }

    // Fungsi untuk menentukan skor kelembapan
    private int getKelembapanSkor(double humidity) {
        if (humidity > 90) {
            return 1;
        } else if (humidity >= 80) {
            return 2;
        } else if (humidity >= 70) {
            return 3;
        } else if (humidity >= 60) {
            return 4;
        } else {
            return 5;
        }
    }

    // Fungsi untuk menentukan skor kecepatan angin
    private int getKecepatanAnginSkor(double windSpeed) {
        if (windSpeed >= 10) {
            return 1;
        } else if (windSpeed >= 7) {
            return 2;
        } else if (windSpeed >= 5) {
            return 3;
        } else if (windSpeed >= 3) {
            return 4;
        } else {
            return 5;
        }
    }

    // Fungsi untuk menentukan skor berdasarkan cloudiness
    private int getCuacaSkor(int cloudiness) {
        if (cloudiness > 80) {
            return 1;
        } else if (cloudiness >= 60) {
            return 2;
        } else if (cloudiness >= 40) {
            return 3;
        } else if (cloudiness >= 20) {
            return 4;
        } else {
            return 5;
        }
    }
}
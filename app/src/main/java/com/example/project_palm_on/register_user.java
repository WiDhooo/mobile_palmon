package com.example.project_palm_on;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class register_user extends AppCompatActivity {

    private EditText usernameInput, emailInput, phoneInput, passwordInput;
    private Button registerButton;
    private TextView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // Inisialisasi UI
        usernameInput = findViewById(R.id.username_input_register);
        emailInput = findViewById(R.id.email_input_register);
        phoneInput = findViewById(R.id.number_phone_input_register);
        passwordInput = findViewById(R.id.password_input_register);
        registerButton = findViewById(R.id.button_create_account);
        loginButton = findViewById(R.id.login_account);

        // Tombol login: arahkan ke halaman login
        loginButton.setOnClickListener(view -> {
            Intent i = new Intent(register_user.this, login_user.class);
            startActivity(i);
        });

        // Tombol daftar: lakukan validasi dan registrasi
        registerButton.setOnClickListener(v -> registerUser());
        Intent i = new Intent(register_user.this, login_user.class);
        startActivity(i);
    }

    private void registerUser() {
        if (!validateInputs()) {
            return; // Validasi gagal, hentikan eksekusi
        }

        String url = Constants.URL_USER; // Ganti dengan URL API Anda
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (jsonResponse.has("error")) {
                            Toast.makeText(register_user.this, jsonResponse.getString("error"), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(register_user.this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(register_user.this, login_user.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(register_user.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(register_user.this, "Registrasi gagal: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", usernameInput.getText().toString().trim());
                params.put("email", emailInput.getText().toString().trim());
                params.put("no_telp", phoneInput.getText().toString().trim());
                params.put("password", passwordInput.getText().toString().trim());
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private boolean validateInputs() {
        // Validasi username
        String username = usernameInput.getText().toString().trim();
        if (username.isEmpty()) {
            usernameInput.setError("Username tidak boleh kosong");
            return false;
        }
        if (username.length() > 50) {
            usernameInput.setError("Username tidak boleh lebih dari 50 karakter");
            return false;
        }

        // Validasi email
        String email = emailInput.getText().toString().trim();
        if (email.isEmpty()) {
            emailInput.setError("Email tidak boleh kosong");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Email tidak valid");
            return false;
        }

        // Validasi nomor telepon
        String phone = phoneInput.getText().toString().trim();
        if (phone.isEmpty()) {
            phoneInput.setError("Nomor telepon tidak boleh kosong");
            return false;
        }
        if (!phone.matches("[0-9]+")) {
            phoneInput.setError("Nomor telepon hanya boleh mengandung angka");
            return false;
        }

        // Validasi kata sandi
        String password = passwordInput.getText().toString().trim();
        if (password.isEmpty()) {
            passwordInput.setError("Kata sandi tidak boleh kosong");
            return false;
        }
        if (!isPasswordValid(password)) {
            passwordInput.setError("Kata sandi harus minimal 8 karakter, mengandung huruf besar, huruf kecil, angka, dan simbol");
            return false;
        }

        return true; // Semua validasi lulus
    }

    private boolean isPasswordValid(String password) {
        // Validasi kekuatan kata sandi
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") && // Mengandung huruf besar
                password.matches(".*[a-z].*") && // Mengandung huruf kecil
                password.matches(".*\\d.*") &&  // Mengandung angka
                password.matches(".*[!@#$%^&*(),.?\":{}|<>].*"); // Mengandung simbol
    }
}

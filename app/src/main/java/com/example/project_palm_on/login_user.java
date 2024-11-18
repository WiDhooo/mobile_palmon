package com.example.project_palm_on;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

public class login_user extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView createAccountTextView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameEditText = findViewById(R.id.username_input_login);
        passwordEditText = findViewById(R.id.password_input_login);
        loginButton = findViewById(R.id.button_login);
        createAccountTextView = findViewById(R.id.create_account);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(login_user.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call login API
                loginUser(username, password);
            }
        });
        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to create account screen
                startActivity(new Intent(login_user.this, register_user.class));
            }
        });
        }
    private void loginUser(String usernameOrEmail, String password) {
        // URL endpoint API untuk login
        String loginUrl = Constants.URL_USER + "/login";  // Ganti dengan URL API login Anda

        // Membuat request JSON untuk login
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", usernameOrEmail);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, loginUrl, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Memeriksa apakah login berhasil
                            if (response.getBoolean("success")) {
                                String userId = response.getString("user_id");
                                // Menyimpan user_id ke SharedPreferences
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(login_user.this);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user_id", userId);
                                editor.apply();

                                // Arahkan ke halaman home setelah login berhasil
                                startActivity(new Intent(login_user.this, get_started.class));
                                finish();
                            } else {
                                Toast.makeText(login_user.this, "Username atau password tidak sesuai", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(login_user.this, "Terjadi kesalahan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(login_user.this, "Login gagal: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Menambahkan request ke queue Volley
        Volley.newRequestQueue(this).add(loginRequest);
    }
}
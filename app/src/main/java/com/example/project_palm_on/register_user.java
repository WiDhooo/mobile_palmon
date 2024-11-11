package com.example.project_palm_on;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_user);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameInput = findViewById(R.id.username_input_register);
        emailInput = findViewById(R.id.email_input_register);
        phoneInput = findViewById(R.id.number_phone_input_register);
        passwordInput = findViewById(R.id.password_input_register);
        registerButton = findViewById(R.id.button_create_account);
        loginButton = findViewById(R.id.login_account);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(register_user.this, login_user.class);
                startActivity(i);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                Intent i = new Intent(register_user.this, login_user.class);
                startActivity(i);
            }
        });
    }

    private void registerUser() {
        String url = Constants.URL_USER;
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.has("error")) {
                                Toast.makeText(register_user.this, jsonResponse.getString("error"), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(register_user.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(register_user.this, login_user.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(register_user.this, "Registration failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", usernameInput.getText().toString());
                params.put("email", emailInput.getText().toString());
                params.put("no_telp", phoneInput.getText().toString());
                params.put("password", passwordInput.getText().toString());
                return params;
            }
        };

        queue.add(stringRequest);
    }
}

package com.example.project_palm_on;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AkunInfo extends AppCompatActivity {
    Button keluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_account);

        keluar = findViewById(R.id.button_keluar_account);

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menghapus user_id dari SharedPreferences
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AkunInfo.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("user_id");  // Menghapus user_id
                editor.apply();

                // Menampilkan pesan logout dan mengarahkan pengguna ke halaman login
                Toast.makeText(AkunInfo.this, "Anda telah logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AkunInfo.this, login_user.class);
                startActivity(intent);
                finish();  // Menutup aktivitas AkunInfo
            }
        });
    }
}

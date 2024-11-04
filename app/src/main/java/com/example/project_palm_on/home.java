package com.example.project_palm_on;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {
    ImageButton icon_kalkulasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //def objek
        icon_kalkulasi = findViewById(R.id.icon_kalkulasi_home);

        //arah ke kalkulasi
        icon_kalkulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, kalkulasi_page.class);
                startActivity(i);
            }
        });

        //ini gw nyoba doang bisa aktif gk buttonnya
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    Toast.makeText(home.this, "Home Selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_toko) {
                    Toast.makeText(home.this, "Toko Selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_history) {
                    Toast.makeText(home.this, "History Selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_akun) {
                    Toast.makeText(home.this, "Akun Selected", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }

        });
    }
}

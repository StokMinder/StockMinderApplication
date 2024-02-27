package com.example.stockminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.stockminderapp.movement.Movement;
import com.example.stockminderapp.profile.Profile;
import com.example.stockminderapp.report.Report;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mengambil data username dari Intent
        Intent intent = getIntent();
        if (intent.hasExtra("username")) {
            String username = intent.getStringExtra("username");
            TextView textViewNamaBisnis = findViewById(R.id.nama_bisnis);
            textViewNamaBisnis.setText(username);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.navigation_movement) {
                            Intent intent = new Intent(MainActivity.this, Movement.class);
                            startActivity(intent);
                            return true;
                        } else if (id == R.id.navigation_report) {
                            Intent intent = new Intent(MainActivity.this, Report.class);
                            startActivity(intent);
                            return true;
                        }
                        return false;
                    }
                });
    }

    public void navigation_profile(View view) {
        Intent intent = new Intent(MainActivity.this, Profile.class);
        startActivity(intent);
    }

    public void join_comunity(View view) {
        String url = "https://t.me/komunitas_umkm_indonesia";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    //Button Article UMKM
    public void article1(View view) {
        String url = "https://www.liputan6.com/photo/read/5386498/rencana-penghapusan-kredit-macet-umkm?page=1";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    public void article2(View view) {
        String url = "https://www.detik.com/jabar/bisnis/d-7065422/upaya-bjb-syariah-bikin-sektor-umkm-naik-kelas";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    public void article3(View view) {
        String url = "https://finance.detik.com/bursa-dan-valas/d-7120059/wapres-ingin-bursa-saham-tak-lagi-eksklusif-ke-perusahaan-besar";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    public void article4(View view) {
        String url = "https://finance.detik.com/solusiukm/d-7110853/tokopedia-tiktok-beberkan-6-strategi-umkm-bisa-bersaing-di-ranah-digital";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    public void article5(View view) {
        String url = "https://www.liputan6.com/pemilu/read/5493650/atikoh-ganjar-serap-dua-aspirasi-pelaku-umkm-di-tegal";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
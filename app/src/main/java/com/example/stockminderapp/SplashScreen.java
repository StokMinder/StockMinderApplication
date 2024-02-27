package com.example.stockminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.stockminderapp.welcome.Welcome;

public class SplashScreen extends AppCompatActivity {

    // Waktu tampilan splash screen (dalam milidetik)
    private static int SPLASH_TIME_OUT = 1000; // 3 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Metode ini akan dijalankan setelah 3 detik
                Intent homeIntent = new Intent(SplashScreen.this, Welcome.class);
                startActivity(homeIntent);
                finish(); // Tutup activity splash screen agar tidak kembali saat menekan tombol kembali
            }
        }, SPLASH_TIME_OUT);
    }
}
package com.example.stockminderapp.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.stockminderapp.R;

public class Welcome2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome2);
    }
    public void btn_next (View view) {
        Intent intent = new Intent(Welcome2.this, Welcome3.class);
        startActivity(intent);
    }

    public void btn_back (View view) {
        Intent intent = new Intent(Welcome2.this, Welcome.class);
        startActivity(intent);
    }
}
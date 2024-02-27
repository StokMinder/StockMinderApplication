package com.example.stockminderapp.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.stockminderapp.MainActivity;
import com.example.stockminderapp.R;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void btn_next (View view) {
        Intent intent = new Intent(Welcome.this, Welcome2.class);
        startActivity(intent);
    }
}
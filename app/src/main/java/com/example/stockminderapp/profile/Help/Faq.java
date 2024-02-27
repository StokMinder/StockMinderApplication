package com.example.stockminderapp.profile.Help;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.stockminderapp.R;
import com.example.stockminderapp.profile.Profile;
import com.example.stockminderapp.welcome.Welcome;
import com.example.stockminderapp.welcome.Welcome2;

public class Faq extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
    }

    public void CONTACT (View view) {
        Intent intent = new Intent(Faq.this, Contact.class);
        startActivity(intent);
    }

    public void btn_back3 (View view) {
        Intent intent = new Intent(Faq.this, Profile.class);
        startActivity(intent);
    }
}
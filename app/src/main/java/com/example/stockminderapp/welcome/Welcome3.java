package com.example.stockminderapp.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.stockminderapp.MainActivity;
import com.example.stockminderapp.R;
import com.example.stockminderapp.login.Create;
import com.example.stockminderapp.login.Sign;

public class Welcome3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome3);
    }

    public void btn_back (View view) {
        Intent intent = new Intent(Welcome3.this, Welcome2.class);
        startActivity(intent);
    }

    public void navigation_sign (View view) {
        Intent intent = new Intent(Welcome3.this, Sign.class);
        startActivity(intent);
    }

    public void createbutton (View view) {
        Intent intent = new Intent(Welcome3.this, Create.class);
        startActivity(intent);
    }
}
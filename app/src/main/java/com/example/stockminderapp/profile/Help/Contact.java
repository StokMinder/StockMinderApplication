package com.example.stockminderapp.profile.Help;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.stockminderapp.R;
import com.example.stockminderapp.profile.Profile;
import com.example.stockminderapp.welcome.Welcome;
import com.example.stockminderapp.welcome.Welcome2;

public class Contact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }
    public void whatsapp(View view) {
        String url = "https://wa.me/6287760157670";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    public void Instagram(View view) {
        String url = "https://www.instagram.com/muhraflisolo/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    public void Facebook(View view) {
        String url = "https://web.facebook.com/dolphinsgraf.dolphinsgraf?locale=id_ID";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    public void btn_back4 (View view) {
        Intent intent = new Intent(Contact.this, Profile.class);
        startActivity(intent);
    }

    public void FAQ (View view) {
        Intent intent = new Intent(Contact.this, Faq.class);
        startActivity(intent);
    }
}
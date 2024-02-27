package com.example.stockminderapp.AddItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stockminderapp.MainActivity;
import com.example.stockminderapp.R;
import com.example.stockminderapp.login.Create;
import com.example.stockminderapp.login.Sign;
import com.example.stockminderapp.welcome.Welcome3;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegItemActivity extends AppCompatActivity {

    private EditText tambah_barang;
    private ImageButton btn_add;

    private TextView dataTextView;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_item);

        dataTextView = findViewById(R.id.viewItem);

        tambah_barang = findViewById(R.id.tambah_barang);
        btn_add = findViewById(R.id.btn_add);

        database = FirebaseDatabase.getInstance().getReference("Data Barang");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuilder dataBuilder = new StringBuilder();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String data = snapshot.child("tambah barang").getValue(String.class);
                    dataBuilder.append(data).append("\n");
                    Log.d("FirebaseData", "Data: " + data);
                }
                dataTextView.setText(dataBuilder.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle kesalahan di sini.
                Log.e("FirebaseData", "Error: " + error.getMessage());
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tambahbarang = tambah_barang.getText().toString();

                if (tambahbarang.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Anda Belum Memasukkan Data !!", Toast.LENGTH_SHORT).show();
                } else {
                    database.child(tambahbarang).child("tambah barang").setValue(tambahbarang);
                    Toast.makeText(getApplicationContext(), "Tambah Barang Berhasil", Toast.LENGTH_SHORT).show();
                    tambah_barang.setText(""); // Clear input setelah menambahkan data
                }
            }
        });
    }

    public void btn_submit(View view) {
        Intent intent = new Intent(RegItemActivity.this, Sign.class);
        startActivity(intent);
    }
}

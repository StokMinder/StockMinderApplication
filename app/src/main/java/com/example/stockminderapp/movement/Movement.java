package com.example.stockminderapp.movement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stockminderapp.MainActivity;
import com.example.stockminderapp.R;
import com.example.stockminderapp.check.CheckStock;
import com.example.stockminderapp.report.Report;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Movement extends AppCompatActivity {

    private int jumlahStokSaatIni = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);

        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    int id = item.getItemId();
                    if (id == R.id.navigation_home) {
                        Intent intent = new Intent(Movement.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    } else if (id == R.id.navigation_report) {
                        Intent intent = new Intent(Movement.this, Report.class);
                        startActivity(intent);
                        return true;
                    }
                    return false;
                });
    }

    public void add_stock(View view) {
        EditText editTextNamaBarang = findViewById(R.id.nama_barang);
        EditText editTextJumlahBarang = findViewById(R.id.jumlah);
        String namaBarangString = editTextNamaBarang.getText().toString();
        String jumlahBarangString = editTextJumlahBarang.getText().toString();

        if (!namaBarangString.isEmpty() && !jumlahBarangString.isEmpty()) {
            int jumlahBarang = Integer.parseInt(jumlahBarangString);
            updateStock("add_stock", namaBarangString, jumlahBarang);
        } else {
            showToast("Silakan masukkan nama barang dan jumlah yang valid.");
        }
    }

    public void lack_stock(View view) {
        EditText editTextNamaBarang = findViewById(R.id.nama_barang);
        EditText editTextJumlahBarang = findViewById(R.id.jumlah);
        String namaBarangString = editTextNamaBarang.getText().toString();
        String jumlahBarangString = editTextJumlahBarang.getText().toString();

        if (!namaBarangString.isEmpty() && !jumlahBarangString.isEmpty()) {
            int jumlahBarang = Integer.parseInt(jumlahBarangString);
            updateStock("lack_stock", namaBarangString, jumlahBarang);
        } else {
            showToast("Silakan masukkan nama barang dan jumlah yang valid.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateStock(String action, String namaBarang, int jumlah) {
        DatabaseReference barangRef = FirebaseDatabase.getInstance().getReference("Data Barang");
        DatabaseReference laporanMasukRef = FirebaseDatabase.getInstance().getReference("Laporan Barang Masuk");
        DatabaseReference laporanKeluarRef = FirebaseDatabase.getInstance().getReference("Laporan Barang Keluar");

        barangRef.child(namaBarang).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int jumlahSebelumnya = dataSnapshot.child("jumlah").getValue(Integer.class);

                    if (action.equals("add_stock")) {
                        jumlahSebelumnya += jumlah;
                        updateReport(laporanMasukRef, getCurrentDate(), namaBarang, jumlah);
                    } else if (action.equals("lack_stock")) {
                        if (jumlahSebelumnya >= jumlah) {
                            jumlahSebelumnya -= jumlah;
                            updateReport(laporanKeluarRef, getCurrentDate(), namaBarang, jumlah);
                        } else {
                            showToast("Stok tidak mencukupi untuk pengurangan ini.");
                            return;
                        }
                    }

                    barangRef.child(namaBarang).child("jumlah").setValue(jumlahSebelumnya);
                    jumlahStokSaatIni = jumlahSebelumnya;

                    showToast("Jumlah barang diperbarui. Stok saat ini: " + jumlahStokSaatIni);
                } else {
                    showToast("Data barang tidak ditemukan.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseData", "Error: " + databaseError.getMessage());
            }
        });
    }

    private void updateReport(DatabaseReference reportRef, String currentDate, String itemName, int quantity) {
        reportRef.child(currentDate).child(itemName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int previousQuantity = dataSnapshot.getValue(Integer.class);
                    reportRef.child(currentDate).child(itemName).setValue(previousQuantity + quantity);
                } else {
                    reportRef.child(currentDate).child(itemName).setValue(quantity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseData", "Error updating report: " + databaseError.getMessage());
            }
        });
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void check_stock(View view) {
        Intent intent = new Intent(Movement.this, CheckStock.class);
        startActivity(intent);
    }
}

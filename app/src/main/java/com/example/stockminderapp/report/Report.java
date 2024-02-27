package com.example.stockminderapp.report;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stockminderapp.MainActivity;
import com.example.stockminderapp.R;
import com.example.stockminderapp.movement.Movement;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Report extends AppCompatActivity {

    private TextView tglLaporan;
    private TextView namaBarangTextView;
    private TextView jumlahBarangMasuk;
    private TextView jumlahBarangKeluar;
    private TextView totalMasukTextView; // Added for total masuk
    private TextView totalKeluarTextView; // Added for total keluar
    private ImageButton btnSelectDate;
    private SimpleDateFormat dateFormatter;
    private DatabaseReference laporanMasukRef;
    private DatabaseReference laporanKeluarRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        tglLaporan = findViewById(R.id.etSelectDate);
        namaBarangTextView = findViewById(R.id.item);
        jumlahBarangMasuk = findViewById(R.id.Masuk);
        jumlahBarangKeluar = findViewById(R.id.Keluar);
        totalMasukTextView = findViewById(R.id.total_masuk); // Initialize total masuk TextView
        totalKeluarTextView = findViewById(R.id.total_keluar); // Initialize total keluar TextView
        btnSelectDate = findViewById(R.id.btnSelectDate);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        laporanMasukRef = FirebaseDatabase.getInstance().getReference("Laporan Barang Masuk");
        laporanKeluarRef = FirebaseDatabase.getInstance().getReference("Laporan Barang Keluar");

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.navigation_home) {
                            Intent intent = new Intent(Report.this, MainActivity.class);
                            startActivity(intent);
                            return true;
                        } else if (id == R.id.navigation_movement) {
                            Intent intent = new Intent(Report.this, Movement.class);
                            startActivity(intent);
                            return true;
                        }
                        return false;
                    }
                });
    }

    private void showDatePickerDialog() {
        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        String selectedDate = dateFormatter.format(newDate.getTime());
                        tglLaporan.setText(selectedDate);

                        getDataFromFirebase(selectedDate);
                    }
                },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void getDataFromFirebase(final String selectedDate) {
        laporanMasukRef.child(selectedDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long totalMasuk = 0; // Added this line to track totalMasuk

                StringBuilder sbNama = new StringBuilder();
                StringBuilder sbMasuk = new StringBuilder();

                for (DataSnapshot barangSnapshot : dataSnapshot.getChildren()) {
                    String barangKey = barangSnapshot.getKey();
                    long jumlahBarangMasuk = barangSnapshot.getValue(Long.class);

                    sbNama.append(barangKey).append("\n");
                    sbMasuk.append(jumlahBarangMasuk).append("\n");

                    totalMasuk += jumlahBarangMasuk; // Update totalMasuk
                }

                namaBarangTextView.setText(sbNama.toString());
                jumlahBarangMasuk.setText(sbMasuk.toString());
                totalMasukTextView.setText(String.valueOf(totalMasuk)); // Update total masuk TextView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error jika diperlukan
            }
        });

        laporanKeluarRef.child(selectedDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long totalKeluar = 0; // Added this line to track totalKeluar

                StringBuilder sbNama = new StringBuilder();
                StringBuilder sbKeluar = new StringBuilder();

                for (DataSnapshot barangSnapshot : dataSnapshot.getChildren()) {
                    String barangKey = barangSnapshot.getKey();
                    long jumlahBarangKeluar = barangSnapshot.getValue(Long.class);

                    sbNama.append(barangKey).append("\n");
                    sbKeluar.append(jumlahBarangKeluar).append("\n");

                    totalKeluar += jumlahBarangKeluar; // Update totalKeluar
                }

                namaBarangTextView.setText(sbNama.toString());
                jumlahBarangKeluar.setText(sbKeluar.toString());
                totalKeluarTextView.setText(String.valueOf(totalKeluar)); // Update total keluar TextView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseData", "Error fetching laporan keluar data: " + databaseError.getMessage());
            }
        });
    }
}

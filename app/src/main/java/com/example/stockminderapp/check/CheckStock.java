package com.example.stockminderapp.check;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.stockminderapp.MainActivity;
import com.example.stockminderapp.R;
import com.example.stockminderapp.movement.Movement;
import com.example.stockminderapp.welcome.Welcome2;
import com.example.stockminderapp.welcome.Welcome3;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckStock extends AppCompatActivity {

    private DatabaseReference database;

    private TextView[] itemTextViews;
    private TextView totalStockTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_stock);

        itemTextViews = new TextView[]{
                findViewById(R.id.Item1),
                findViewById(R.id.Item2),
                findViewById(R.id.Item3),
                findViewById(R.id.Item4),
                findViewById(R.id.Item5),
                findViewById(R.id.Item6),
                findViewById(R.id.Item7),
                findViewById(R.id.Item8),
                findViewById(R.id.Item9),
                findViewById(R.id.Item10),
                findViewById(R.id.Item11),
                findViewById(R.id.Item12),
                findViewById(R.id.Item13),
                findViewById(R.id.Item14),
                findViewById(R.id.Item15),
                findViewById(R.id.JItem1),
                findViewById(R.id.JItem2),
                findViewById(R.id.JItem3),
                findViewById(R.id.JItem4),
                findViewById(R.id.JItem5),
                findViewById(R.id.JItem6),
                findViewById(R.id.JItem7),
                findViewById(R.id.JItem8),
                findViewById(R.id.JItem9),
                findViewById(R.id.JItem10),
                findViewById(R.id.JItem11),
                findViewById(R.id.JItem12),
                findViewById(R.id.JItem13),
                findViewById(R.id.JItem14),
                findViewById(R.id.JItem15)
        };

        totalStockTextView = findViewById(R.id.total_stock);

        database = FirebaseDatabase.getInstance().getReference("Data Barang");

        ImageButton simpanButton = findViewById(R.id.Reset);
        simpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // simpanData(); // Removed this method call
            }
        });

        ImageButton resetButton = findViewById(R.id.Reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetQuantities();
            }
        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                int totalStock = 0;
                boolean notify = false;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String barangData = snapshot.child("tambah barang").getValue(String.class);
                    itemTextViews[i].setText(barangData);
                    Log.d("FirebaseData", "Barang " + snapshot.getKey() + ": " + barangData);

                    int jumlahBarang = snapshot.child("jumlah").getValue(Integer.class);
                    itemTextViews[i + 15].setText(String.valueOf(jumlahBarang));

                    totalStock += jumlahBarang;

                    // Tambahkan logika notifikasi di sini
                    if ((i == 0 && jumlahBarang < 7) ||
                            (i == 2 && jumlahBarang < 10) ||
                            (i == 3 && jumlahBarang < 5) ||
                            (i == 4 && jumlahBarang < 7) ||
                            (i == 5 && jumlahBarang < 5) ||
                            (i == 6 && jumlahBarang < 12) ||
                            (i == 7 && jumlahBarang < 10) ||
                            (i == 8 && jumlahBarang < 10) ||
                            (i == 9 && jumlahBarang < 10) ||
                            (i == 10 && jumlahBarang < 10) ||
                            (i == 11 && jumlahBarang < 5) ||
                            (i == 12 && jumlahBarang < 5) ||
                            (i == 13 && jumlahBarang < 10) ||
                            (i == 14 && jumlahBarang < 8) ||
                            (i == 15 && jumlahBarang < 15)) {
                        notify = true;
                        showNotification(barangData);
                    }

                    i++;
                }

                totalStockTextView.setText(String.valueOf(totalStock));

                // Jika notifikasi diperlukan, panggil metode untuk menampilkan notifikasi
                if (notify) {
                    // showNotification();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseData", "Error: " + databaseError.getMessage());
            }
        });
    }

    private void resetQuantities() {
        // Implement your reset logic here
        // For example, set quantities to 0 or reset to initial values

        for (int i = 15; i < itemTextViews.length; i++) {
            itemTextViews[i].setText("0");
            // Reset stok barang pada Firebase ke 0
            String barangKey = itemTextViews[i - 15].getText().toString();
            database.child(barangKey).child("jumlah").setValue(0);
        }
        totalStockTextView.setText("0");
    }


    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    // Tambahkan metode untuk menampilkan notifikasi
    private void showNotification(String barangNama) {

        String notificationMessage = "Stok barang " + barangNama + " masuk pada safety stock!";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default_channel_id")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Peringatan Stok")
                .setContentText(notificationMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Buat saluran notifikasi untuk Android Oreo dan yang lebih tinggi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default_channel_id",
                    "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Default Notification Channel");
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID notifikasi */, builder.build());
    }

    public void btn_back (View view) {
        Intent intent = new Intent(CheckStock.this, Movement.class);
        startActivity(intent);
    }
}

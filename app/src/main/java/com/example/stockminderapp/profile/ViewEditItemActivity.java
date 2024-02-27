package com.example.stockminderapp.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stockminderapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewEditItemActivity extends AppCompatActivity {

    private ListView listViewItems;
    private EditText editItem;
    private Button btnSave;

    private DatabaseReference database;

    private ArrayList<String> itemList;
    private ArrayAdapter<String> adapter;

    private boolean isEditMode = false;
    private String selectedItem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_item);

        listViewItems = findViewById(R.id.listViewItemsEdit);
        editItem = findViewById(R.id.editItemEdit);
        btnSave = findViewById(R.id.btnSaveEdit);

        database = FirebaseDatabase.getInstance().getReference("Data Barang");

        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        listViewItems.setAdapter(adapter);

        // Mendapatkan data dari Firebase
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String data = snapshot.child("tambah barang").getValue(String.class);
                    itemList.add(data);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle kesalahan di sini.
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Menanggapi item yang dipilih untuk diedit
        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedItem = itemList.get(position);
                editItem.setText(selectedItem);
                isEditMode = true;
            }
        });

        // Menanggapi tombol edit/save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editedItem = editItem.getText().toString();

                if (editedItem.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Anda Belum Memasukkan Data !!", Toast.LENGTH_SHORT).show();
                } else {
                    if (isEditMode) {
                        // Jika dalam mode edit, lakukan penyimpanan (save)
                        int selectedIndex = itemList.indexOf(selectedItem);
                        if (selectedIndex != -1) {
                            itemList.set(selectedIndex, editedItem);
                            adapter.notifyDataSetChanged();

                            // You may also update the data in Firebase if needed
                            database.child(selectedItem).child("tambah barang").setValue(editedItem);

                            Toast.makeText(getApplicationContext(), "Save Barang Berhasil", Toast.LENGTH_SHORT).show();
                            editItem.setText(""); // Bersihkan input setelah menyimpan data
                            isEditMode = false; // Keluar dari mode edit
                        } else {
                            Toast.makeText(getApplicationContext(), "Item not found in the list", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Pilih item yang ingin diedit", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
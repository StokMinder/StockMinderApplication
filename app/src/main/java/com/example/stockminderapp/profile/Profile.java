package com.example.stockminderapp.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.stockminderapp.MainActivity;
import com.example.stockminderapp.R;
import com.example.stockminderapp.movement.Movement;
import com.example.stockminderapp.profile.Help.Faq;
import com.example.stockminderapp.report.Report;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    private static final int REQUEST_IMAGE_GALLERY = 101;
    private static final int REQUEST_IMAGE_CAMERA = 102;

    private static final String PREF_NAME_KEY = "name";
    private static final String PREF_STATUS_KEY = "status";
    private static final String PREF_IMAGE_URI_KEY = "imageUri";

    private ImageView profileImageView;
    private TextView nameTextView;
    private TextView statusTextView;

    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = findViewById(R.id.profileImageView);
        nameTextView = findViewById(R.id.nameTextView);
        statusTextView = findViewById(R.id.statusTextView);

        ImageButton editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Memilih gambar dari galeri atau kamera
                showImagePickerDialog();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    int id = item.getItemId();
                    if (id == R.id.navigation_home) {
                        Intent intent = new Intent(Profile.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    } else if (id == R.id.navigation_report) {
                        Intent intent = new Intent(Profile.this, Report.class);
                        startActivity(intent);
                        return true;
                    } else if (id == R.id.navigation_movement) {
                        Intent intent = new Intent(Profile.this, Movement.class);
                        startActivity(intent);
                        return true;
                    } else if (id == R.id.navigation_help) {
                        Intent intent = new Intent(Profile.this, Faq.class);
                        startActivity(intent);
                        return true;
                    }
                    return false;
                });
    }

    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Profile");

        View view = getLayoutInflater().inflate(R.layout.dialog_edit_profile, null);
        final TextView editNameTextView = view.findViewById(R.id.editNameTextView);
        final TextView editStatusTextView = view.findViewById(R.id.editStatusTextView);

        builder.setView(view);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = editNameTextView.getText().toString();
                String newStatus = editStatusTextView.getText().toString();

                nameTextView.setText(newName);
                statusTextView.setText(newStatus);

                Toast.makeText(Profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");

        CharSequence[] options = {"Gallery", "Camera"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Pilih gambar dari galeri
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
                } else if (which == 1) {
                    // Ambil gambar dari kamera
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAMERA);
                }
            }
        });

        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_GALLERY && data != null) {
                // Ambil gambar dari galeri
                profileImageView.setImageURI(data.getData());
            } else if (requestCode == REQUEST_IMAGE_CAMERA && data != null) {
                // Ambil gambar dari kamera
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                profileImageView.setImageBitmap(photo);
            }
        }
    }

    private void loadProfileData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString(PREF_NAME_KEY, "");
        String status = preferences.getString(PREF_STATUS_KEY, "");
        String imageUriString = preferences.getString(PREF_IMAGE_URI_KEY, null);

        nameTextView.setText(name);
        statusTextView.setText(status);
        if (imageUriString != null) {
            imageUri = Uri.parse(imageUriString);
            profileImageView.setImageURI(imageUri);
        }
    }

    private void saveProfileData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_NAME_KEY, nameTextView.getText().toString());
        editor.putString(PREF_STATUS_KEY, statusTextView.getText().toString());
        if (imageUri != null) {
            editor.putString(PREF_IMAGE_URI_KEY, imageUri.toString());
        }
        editor.apply();
    }

    public void navigation_edititem(View view) {
        // Method untuk navigasi ke halaman edit item
        Intent intent = new Intent(Profile.this, ViewEditItemActivity.class);
        startActivity(intent);
    }

    public void btn_back(View view) {
        // Method untuk kembali ke halaman utama
        Intent intent = new Intent(Profile.this, MainActivity.class);
        startActivity(intent);
    }

    public void navigation_help(View view) {
        // Method untuk navigasi ke halaman bantuan (FAQ)
        Intent intent = new Intent(Profile.this, Faq.class);
        startActivity(intent);
    }
}
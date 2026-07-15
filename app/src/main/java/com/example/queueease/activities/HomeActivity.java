package com.example.queueease.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.queueease.R;

public class HomeActivity extends AppCompatActivity {

    Button btnGenerateToken, btnCurrentQueue, btnHistory, btnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnGenerateToken = findViewById(R.id.btnGenerateToken);
        btnCurrentQueue = findViewById(R.id.btnCurrentQueue);
        btnHistory = findViewById(R.id.btnHistory);
        btnAdmin = findViewById(R.id.btnAdmin);

        btnGenerateToken.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, GenerateTokenActivity.class)));

        btnCurrentQueue.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, CurrentQueueActivity.class)));

        btnHistory.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, HistoryActivity.class)));

        btnAdmin.setOnClickListener(v -> showAdminDialog());
    }

    private void showAdminDialog() {

        EditText pin = new EditText(this);
        pin.setHint("Enter Admin PIN");

        new AlertDialog.Builder(this)
                .setTitle("Admin Login")
                .setView(pin)
                .setPositiveButton("Login", (dialog, which) -> {

                    if(pin.getText().toString().equals("1234")){

                        startActivity(new Intent(HomeActivity.this, AdminActivity.class));

                    }else{

                        Toast.makeText(this,"Wrong PIN",Toast.LENGTH_SHORT).show();

                    }

                })
                .setNegativeButton("Cancel",null)
                .show();

    }
}
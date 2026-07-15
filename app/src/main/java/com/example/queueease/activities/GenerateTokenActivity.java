package com.example.queueease.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.queueease.R;
import com.example.queueease.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GenerateTokenActivity extends AppCompatActivity {

    private TextView txtToken;
    private Button btnGenerate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_token);

        txtToken = findViewById(R.id.txtToken);
        btnGenerate = findViewById(R.id.btnGenerate);

        btnGenerate.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(this);

            int nextToken = db.getNextTokenNumber();

            String token = String.format("Q%03d", nextToken);

            txtToken.setText(token);

            String currentDateTime = new SimpleDateFormat(
                    "dd-MM-yyyy hh:mm a",
                    Locale.getDefault()
            ).format(new Date());

            boolean inserted = db.insertToken(
                    token,
                    "Waiting",
                    currentDateTime
            );

            if (inserted) {
                Toast.makeText(this,
                        "Token Generated Successfully",
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this,
                        "Failed to Generate Token",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
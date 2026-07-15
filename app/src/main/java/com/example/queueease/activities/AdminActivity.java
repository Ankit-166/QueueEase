package com.example.queueease.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.queueease.R;
import com.example.queueease.database.DatabaseHelper;

public class AdminActivity extends AppCompatActivity {

    TextView txtTotal, txtWaiting, txtServing, txtServed;
    Button btnNext, btnServed, btnClear;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        txtTotal = findViewById(R.id.txtTotal);
        txtWaiting = findViewById(R.id.txtWaiting);
        txtServing = findViewById(R.id.txtServing);
        txtServed = findViewById(R.id.txtServed);

        btnNext = findViewById(R.id.btnNext);
        btnServed = findViewById(R.id.btnServed);
        btnClear = findViewById(R.id.btnClear);

        databaseHelper = new DatabaseHelper(this);

        loadStatistics();

        btnClear.setOnClickListener(v -> {

            databaseHelper.clearHistory();

            Toast.makeText(this,
                    "History Cleared",
                    Toast.LENGTH_SHORT).show();

            loadStatistics();


        });
        btnNext.setOnClickListener(v -> {

            if (databaseHelper.isTokenServing()) {

                Toast.makeText(this,
                        "Please mark the current token as Served first",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            String token = databaseHelper.getNextWaitingToken();

            if (token != null) {

                databaseHelper.startServing(token);

                Toast.makeText(this,
                        token + " is now Serving",
                        Toast.LENGTH_SHORT).show();

                loadStatistics();

            } else {

                Toast.makeText(this,
                        "No Waiting Token",
                        Toast.LENGTH_SHORT).show();
            }

        });
        btnServed.setOnClickListener(v -> {

            databaseHelper.markServed();

            Toast.makeText(this,
                    "Token Marked as Served",
                    Toast.LENGTH_SHORT).show();

            loadStatistics();

        });

    }

    private void loadStatistics() {

        txtTotal.setText("Total Tokens : " + databaseHelper.getTotalTokens());

        txtWaiting.setText("Waiting : " + databaseHelper.getWaitingTokens());

        txtServing.setText("Serving : " + databaseHelper.getServingTokens());

        txtServed.setText("Served : " + databaseHelper.getServedTokens());

    }

}
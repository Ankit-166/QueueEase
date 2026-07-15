package com.example.queueease.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.queueease.R;
import com.example.queueease.database.DatabaseHelper;

public class CurrentQueueActivity extends AppCompatActivity {

    TextView txtCurrentToken, txtStatus;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_queue);

        txtCurrentToken = findViewById(R.id.txtCurrentToken);
        txtStatus = findViewById(R.id.txtStatus);

        databaseHelper = new DatabaseHelper(this);

        loadCurrentQueue();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCurrentQueue();
    }

    private void loadCurrentQueue() {

        String token = databaseHelper.getCurrentServingToken();

        if (token.equals("--")) {
            txtCurrentToken.setText("--");
            txtStatus.setText("Status : No Token Serving");
        } else {
            txtCurrentToken.setText(token);
            txtStatus.setText("Status : Serving");
        }
    }
}
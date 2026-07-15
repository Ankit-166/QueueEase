package com.example.queueease.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.queueease.R;
import com.example.queueease.adapter.TokenAdapter;
import com.example.queueease.database.DatabaseHelper;
import com.example.queueease.model.Token;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    ArrayList<Token> tokenList;
    TokenAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerView);

        databaseHelper = new DatabaseHelper(this);

        tokenList = databaseHelper.getAllTokens();

        adapter = new TokenAdapter(tokenList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
package com.example.queueease.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.queueease.R;
import com.example.queueease.model.Token;

import java.util.ArrayList;

public class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.TokenViewHolder> {

    private ArrayList<Token> tokenList;

    public TokenAdapter(ArrayList<Token> tokenList) {
        this.tokenList = tokenList;
    }

    @NonNull
    @Override
    public TokenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_token, parent, false);

        return new TokenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TokenViewHolder holder, int position) {

        Token token = tokenList.get(position);

        holder.txtTokenNumber.setText(token.getTokenNumber());
        holder.txtStatus.setText("Status : " + token.getStatus());
        holder.txtDateTime.setText(token.getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return tokenList.size();
    }

    public static class TokenViewHolder extends RecyclerView.ViewHolder {

        TextView txtTokenNumber, txtStatus, txtDateTime;

        public TokenViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTokenNumber = itemView.findViewById(R.id.txtTokenNumber);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtDateTime = itemView.findViewById(R.id.txtDateTime);
        }
    }
}
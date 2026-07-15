package com.example.queueease.model;

public class Token {

    private int id;
    private String tokenNumber;
    private String status;
    private String createdAt;

    public Token(int id, String tokenNumber, String status, String createdAt) {
        this.id = id;
        this.tokenNumber = tokenNumber;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getTokenNumber() {
        return tokenNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
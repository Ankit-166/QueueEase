package com.example.queueease.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.ArrayList;

import com.example.queueease.model.Token;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QueueEase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_QUEUE = "queue";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TOKEN = "token_number";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_CREATED_AT = "created_at";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_QUEUE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TOKEN + " TEXT,"
                + COLUMN_STATUS + " TEXT,"
                + COLUMN_CREATED_AT + " TEXT"
                + ")";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEUE);
        onCreate(db);
    }

    // Insert a new token
    public boolean insertToken(String token, String status, String createdAt) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_TOKEN, token);
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_CREATED_AT, createdAt);

        long result = db.insert(TABLE_QUEUE, null, values);

        return result != -1;
    }
    public ArrayList<Token> getAllTokens() {

        ArrayList<Token> tokenList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_QUEUE + " ORDER BY id DESC",
                null
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String token = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOKEN));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS));
                String createdAt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT));

                tokenList.add(new Token(id, token, status, createdAt));

            } while (cursor.moveToNext());

        }

        cursor.close();

        return tokenList;
    }
    public int getTotalTokens() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_QUEUE,
                null
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }
    public int getWaitingTokens() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_QUEUE +
                        " WHERE status='Waiting'",
                null
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }
    public int getServedTokens() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_QUEUE +
                        " WHERE status='Served'",
                null
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }
    public void clearHistory() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_QUEUE, null, null);

        db.execSQL("DELETE FROM sqlite_sequence WHERE name='" + TABLE_QUEUE + "'");

    }
    public String getNextWaitingToken() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT token_number FROM " + TABLE_QUEUE +
                        " WHERE status='Waiting' ORDER BY id LIMIT 1",
                null);

        String token = null;

        if (cursor.moveToFirst()) {
            token = cursor.getString(0);
        }

        cursor.close();

        return token;
    }
    public void startServing(String token) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("status", "Serving");

        db.update(TABLE_QUEUE,
                values,
                "token_number=?",
                new String[]{token});
    }
    public void markServed() {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("status", "Served");

        db.update(TABLE_QUEUE,
                values,
                "status=?",
                new String[]{"Serving"});
    }
    public String getCurrentServingToken() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT token_number FROM " + TABLE_QUEUE +
                        " WHERE status='Serving' LIMIT 1",
                null);

        String token = "--";

        if (cursor.moveToFirst()) {
            token = cursor.getString(0);
        }

        cursor.close();

        return token;
    }
    public int getServingTokens() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_QUEUE +
                        " WHERE status='Serving'",
                null);

        cursor.moveToFirst();

        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }
    public int getNextTokenNumber() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT MAX(id) FROM " + TABLE_QUEUE,
                null
        );

        int next = 1;

        if (cursor.moveToFirst()) {

            if (!cursor.isNull(0)) {
                next = cursor.getInt(0) + 1;
            }

        }

        cursor.close();

        return next;
    }
    public boolean isTokenServing() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_QUEUE +
                        " WHERE status='Serving'",
                null
        );

        cursor.moveToFirst();

        boolean serving = cursor.getInt(0) > 0;

        cursor.close();

        return serving;
    }
}
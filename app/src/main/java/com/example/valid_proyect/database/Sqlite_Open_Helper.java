package com.example.valid_proyect.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Sqlite_Open_Helper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Music";
    private static final String TABLE1 = "table1";
    private static final String TABLE2 = "table2";

    public Sqlite_Open_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = "CREATE TABLE " + TABLE1 + "(id INTEGER PRIMARY KEY,nartist TEXT, views TXT, lisentme TXT, img BLOB)";
        String table2 = "CREATE TABLE " + TABLE2 + "(id INTEGER PRIMARY KEY,tracks TEXT, views TXT, listenme TXT, img BLOB)";
        db.execSQL(table1);
        db.execSQL(table2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

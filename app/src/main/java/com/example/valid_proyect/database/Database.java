package com.example.valid_proyect.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.valid_proyect.utils.Contants;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context) {
        super(context, Contants.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //database creation tables
        String table1 = "CREATE TABLE " + Contants.topArtists + "(" +
                Contants.topArtists_id +" INTEGER PRIMARY KEY," +
                Contants.topArtists_name + " TEXT, " +
                Contants.topArtists_image + " TEXT, " +
                Contants.topArtists_streamable + " TEXT, " +
                Contants.topArtists_playcount + " TEXT)";

        String table2 = "CREATE TABLE " + Contants.topTracks + "(" +
                Contants.topTracks_id +" INTEGER PRIMARY KEY," +
                Contants.topTracks_name+ " TEXT, " +
                Contants.topTracks_image+ " TEXT, " +
                Contants.topTracks_duration + " TEXT, " +
                Contants.topTracks_listeners+ " TEXT)";

        db.execSQL(table1);
        db.execSQL(table2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package com.example.valid_proyect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.valid_proyect.models.PojoTracks;
import com.example.valid_proyect.utils.Contants;

import java.util.List;


public class TracksSql {
    private Database open;
    private SQLiteDatabase database;

    public TracksSql(Context context){
        open = new Database(context);
    }

    public void insert(PojoTracks tracks){
        database = open.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contants.topTracks_name , tracks.name);
        values.put(Contants.topTracks_image, tracks.image.get(0).text);
        values.put(Contants.topTracks_duration, tracks.duration);
        values.put(Contants.topTracks_listeners, tracks.listeners);

        database.insert(Contants.topTracks, Contants.topTracks_id, values);
    }

    public void truncate(){
        database = open.getWritableDatabase();
        database.delete(Contants.topArtists,null, null);
    }

    public boolean insertAndClear(List<PojoTracks> pojoTracks){
        boolean isChanged = false;

        int count = validateCount();
        if(pojoTracks.size() < count|| pojoTracks.size() > count){
            //borrar registros
            truncate();
            //agregar registros
            for (PojoTracks item : pojoTracks ) {
                insert(item);
            }
            isChanged = true;
        }

        return isChanged;
    }

    public int validateCount(){
        database = open.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT COUNT("+Contants.topTracks_id+") FROM "+ Contants.topTracks, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public Cursor read(){
        database = open.getReadableDatabase();
        return database.rawQuery("SELECT * FROM " + Contants.topTracks, null);
    }
}

package com.example.valid_proyect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.valid_proyect.models.PojoArtists;
import com.example.valid_proyect.utils.Contants;

import java.util.List;

public class ArtistsSql {
    private Database open;
    private SQLiteDatabase database;

    public ArtistsSql(Context context){
        open = new Database(context);
    }

    public void insert(PojoArtists artist){
        database = open.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contants.topArtists_name , artist.name);
        values.put(Contants.topArtists_image, artist.image.get(0).text);
        values.put(Contants.topArtists_streamable, artist.streamable);
        values.put(Contants.topArtists_playcount, artist.playcount);
        values.put(Contants.topArtists_urlcount, artist.url);

        database.insert(Contants.topArtists, Contants.topArtists_id, values);
    }

    public void truncate(){
        database = open.getWritableDatabase();
        database.delete(Contants.topArtists,null, null);
    }

    public boolean insertAndClear(List<PojoArtists> pojoArtists){
        boolean isChanged = false;

        int count = validateCount();
        if(pojoArtists.size() < count|| pojoArtists.size() > count){
            //borrar registros
            truncate();
            //agregar registros
            for (PojoArtists item : pojoArtists ) {
                insert(item);
            }
            isChanged = true;
        }

        return isChanged;
    }

    public int validateCount(){
        database = open.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT COUNT("+Contants.topArtists_id+") FROM "+ Contants.topArtists, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public Cursor read(){
        database = open.getReadableDatabase();
        return database.rawQuery("SELECT * FROM "+Contants.topArtists, null);
    }
}

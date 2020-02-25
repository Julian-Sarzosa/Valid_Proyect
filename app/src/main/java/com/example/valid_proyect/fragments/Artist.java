package com.example.valid_proyect.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valid_proyect.MainActivity;
import com.example.valid_proyect.R;
import com.example.valid_proyect.adapter.ArtistAdapter;
import com.example.valid_proyect.database.ArtistsSql;
import com.example.valid_proyect.database.Database;
import com.example.valid_proyect.interfaces.Artist_Interface;
import com.example.valid_proyect.models.PojoArtists;
import com.example.valid_proyect.models.PojoImages;
import com.example.valid_proyect.models.PojoTopArtistsApi;
import com.example.valid_proyect.utils.Contants;
import com.example.valid_proyect.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Artist extends Fragment{

    //variables
    private View view;
    Context context;

    RecyclerView recyclerView;
    List<PojoArtists> artistList;
    ArtistAdapter artistAdapter;
    SearchView searchView = null;

    public Artist() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Toast.makeText(getContext(), "prueba", Toast.LENGTH_LONG).show();
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null)
        {
            searchView = (SearchView) searchItem.getActionView();
            Toast.makeText(getContext(), "prueba", Toast.LENGTH_LONG).show();
        }
        searchView.setQueryHint("Search...");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);
        if (searchView != null)
        {
            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d( query,"search");

                    Toast.makeText(getContext(), query, Toast.LENGTH_LONG).show();

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.d(newText,"search");

                    Toast.makeText(getContext(), newText, Toast.LENGTH_LONG).show();

                    String input = newText.toLowerCase();
                    String artistl;
                    ArtistsSql artistsSql = new ArtistsSql(getContext());
                    Cursor cursor = artistsSql.read();

                    List<PojoArtists> pojoArtists= new ArrayList<>();

                    while(cursor.moveToNext()){
                        PojoArtists artistsTemp = new PojoArtists();
                        artistsTemp.name = cursor.getString(Contants.topArtists_name_inx);
                        artistl = artistsTemp.name;
                        if (artistl.toLowerCase().contains(input.toLowerCase())){
                            pojoArtists.add(artistsTemp);
                        }
                    }

                    artistList = pojoArtists;
                    inflateRecycler();
                    //artistAdapter.filter(pojoArtists);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_artist, container, false);
        reference();
        eventClickRecycler();
        chargeJson();

        return view;
    }

    private void reference() {
        recyclerView = view.findViewById(R.id.listartist);
    }

    private void eventClickRecycler() {
    }

    private void inflateRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        artistAdapter = new ArtistAdapter( getActivity(),artistList);

        recyclerView.setAdapter(artistAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void chargeJson() {
        String url = "http://ws.audioscrobbler.com/2.0/";

        Artist_Interface retartist = Utils.getClient(url).create(Artist_Interface.class);
        Call<PojoTopArtistsApi> call = retartist.getUsersTopArtist();

        Toast.makeText(getContext(), "Consultando Registros. espere por favor", Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<PojoTopArtistsApi>() {
            @Override
            public void onResponse(Call<PojoTopArtistsApi> call, Response<PojoTopArtistsApi> response) {

                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                PojoTopArtistsApi artists = (PojoTopArtistsApi) response.body();
                artistList =  artists.topartists.artist;

                ArtistsSql artistsSql = new ArtistsSql(getContext());
                if(artistsSql.insertAndClear(artistList)){
                    Toast.makeText(getContext(), "Datos guardados de los Artistas en SQLITE.", Toast.LENGTH_SHORT).show();
                }

                inflateRecycler();
            }

            @Override
            public void onFailure(Call<PojoTopArtistsApi> call, Throwable error) {
                ArtistsSql artistsSql = new ArtistsSql(getContext());
                Cursor cursor = artistsSql.read();

                List<PojoArtists> pojoArtists= new ArrayList<>();

                while(cursor.moveToNext()){
                    //imagen
                    PojoImages imagesTemp = new PojoImages();
                    imagesTemp.text = cursor.getString(Contants.topArtists_image_inx);

                    //lista de imagens que solo se usa 1
                    List<PojoImages> pojoImages= new ArrayList<>();
                    pojoImages.add(imagesTemp);

                    PojoArtists artistsTemp = new PojoArtists();
                    artistsTemp.name = cursor.getString(Contants.topArtists_name_inx);
                    //artistsTemp.url = cursor.getString(Contants.topArtists_urlcount_inx);
                    artistsTemp.image = pojoImages;
                    artistsTemp.streamable = cursor.getString(Contants.topArtists_streamable_inx);
                    artistsTemp.playcount = cursor.getString(Contants.topArtists_playcount_inx);

                    pojoArtists.add(artistsTemp);
                }

                //setear lista global
                artistList = pojoArtists;
                inflateRecycler();
            }
        });
    }

}

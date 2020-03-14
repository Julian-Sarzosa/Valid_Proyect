package com.example.valid_proyect.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valid_proyect.R;
import com.example.valid_proyect.adapter.ArtistAdapter;
import com.example.valid_proyect.database.ArtistsSql;
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

public class Artist extends Fragment implements SearchView.OnQueryTextListener{

    //variables
    private View view;

    RecyclerView recyclerView;
    List<PojoArtists> artistList;
    ArtistAdapter artistAdapter;
    SearchView searchView;
    SearchView.OnQueryTextListener queryTextListener;

    public Artist() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        artistAdapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_search:
                return false;
                default:
                    break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_artist, container, false);
        reference();
        chargeJson();

        return view;
    }

    private void reference() {
        recyclerView = view.findViewById(R.id.listartist);
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

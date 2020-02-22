package com.example.valid_proyect.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valid_proyect.R;
import com.example.valid_proyect.adapter.ArtistAdapter;
import com.example.valid_proyect.interfaces.Artist_Interface;
import com.example.valid_proyect.pojo.NewPojoTopArtists;
import com.example.valid_proyect.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Artist extends Fragment {

    //variables
    private View view;

    RecyclerView recyclerView;
    List<NewPojoTopArtists.topartists.artist> artistList;
    ArtistAdapter artistAdapter;
    ArtistAdapter.IAdapterRecylcer click;

    public Artist() {
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
        artistAdapter = new ArtistAdapter(getActivity(), artistList,click);

        recyclerView.setAdapter(artistAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void chargeJson() {
        String url = "http://ws.audioscrobbler.com/2.0/";

        Artist_Interface retartist = Utils.getClient(url).create(Artist_Interface.class);
        Call<NewPojoTopArtists> call = retartist.getUsersTopArtist();

        Toast.makeText(getContext(), "Consultando Registros. espere por favor", Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<NewPojoTopArtists>() {
            @Override
            public void onResponse(Call<NewPojoTopArtists> call, Response<NewPojoTopArtists> response) {
                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                NewPojoTopArtists artists = (NewPojoTopArtists) response.body();
                artistList =  artists.topartists.artist;
                inflateRecycler();
            }

            @Override
            public void onFailure(Call<NewPojoTopArtists> call, Throwable error) {
                Log.e("error", error.toString());
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

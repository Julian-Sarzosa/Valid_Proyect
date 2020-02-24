package com.example.valid_proyect.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valid_proyect.R;
import com.example.valid_proyect.adapter.TrackAdapter;
import com.example.valid_proyect.database.TracksSql;
import com.example.valid_proyect.interfaces.Track_Interface;
import com.example.valid_proyect.models.PojoImages;
import com.example.valid_proyect.models.PojoStremeable;
import com.example.valid_proyect.models.PojoTopTracksApi;
import com.example.valid_proyect.models.PojoTracks;
import com.example.valid_proyect.utils.Contants;
import com.example.valid_proyect.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tracks extends Fragment {

    //variables
    private View view;

    RecyclerView recyclerView;
    List<PojoTracks> tracksList;
    TrackAdapter tracksAdapter;

    public Tracks() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tracks, container, false);

        reference();
        eventClickRecycler();
        chargeJson();

        return view;
    }

    private void reference() {
        recyclerView = view.findViewById(R.id.listtracks);
    }

    private void eventClickRecycler() {
    }

    private void inflateRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        tracksAdapter = new TrackAdapter(getActivity(), tracksList);

        recyclerView.setAdapter(tracksAdapter);
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

        Track_Interface rettrack = Utils.getClient(url).create(Track_Interface.class);
        Call<PojoTopTracksApi> call = rettrack.getUsersTopTraks();

        Toast.makeText(getContext(), "Consultando Registros. espere por favor", Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<PojoTopTracksApi>() {
            @Override
            public void onResponse(Call<PojoTopTracksApi> call, Response<PojoTopTracksApi> response) {
                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                PojoTopTracksApi track = (PojoTopTracksApi) response.body();
                tracksList =  track.tracks.track;

                TracksSql tracksSql = new TracksSql(getContext());
                if (tracksSql.insertAndClear(tracksList)){
                    Toast.makeText(getContext(), "Datos de Traks guardados en SQLITE.", Toast.LENGTH_SHORT).show();
                }
                inflateRecycler();
            }

            @Override
            public void onFailure(Call<PojoTopTracksApi> call, Throwable error) {
                TracksSql tracksSql = new TracksSql(getContext());
                Cursor cursor = tracksSql.read();

                List<PojoTracks> pojoTracks = new ArrayList<>();

                while (cursor.moveToNext()){

                    PojoImages imagesTemp = new PojoImages();
                    imagesTemp.text = cursor.getString(Contants.topTracks_image_inx);

                    List<PojoImages> pojoImages= new ArrayList<>();
                    pojoImages.add(imagesTemp);

                    PojoTracks tracksTemp = new PojoTracks();
                    tracksTemp.name = cursor.getString(Contants.topTracks_name_inx);
                    tracksTemp.url = cursor.getString(Contants.topTracks_urlcount_inx);
                    tracksTemp.image = pojoImages;
                    tracksTemp.duration = cursor.getString(Contants.topTracks_duration_inx);
                    tracksTemp.listeners = cursor.getString(Contants.topTracks_listeners_inx);

                    pojoTracks.add(tracksTemp);
                }
                //setear lista global
                tracksList = pojoTracks;
                inflateRecycler();
            }
        });
    }
}

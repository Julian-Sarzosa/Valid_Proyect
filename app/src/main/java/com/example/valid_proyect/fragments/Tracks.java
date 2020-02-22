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
import com.example.valid_proyect.adapter.TrackAdapter;
import com.example.valid_proyect.interfaces.Track_Interface;
import com.example.valid_proyect.pojo.NewPojoTopTracks;
import com.example.valid_proyect.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tracks extends Fragment {

    //variables
    private View view;

    RecyclerView recyclerView;
    List<NewPojoTopTracks.tracks.track> tracksList;
    TrackAdapter tracksAdapter;
    TrackAdapter.IAdaptersRecylcer click;

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
        tracksAdapter = new TrackAdapter(getActivity(), tracksList,click);

        recyclerView.setAdapter(tracksAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void chargeJson() {
        String url = "http://ws.audioscrobbler.com/2.0/";

        Track_Interface rettrack = Utils.getClient(url).create(Track_Interface.class);
        Call<NewPojoTopTracks> call = rettrack.getUsersTopTraks();

        Toast.makeText(getContext(), "Consultando Registros. espere por favor", Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<NewPojoTopTracks>() {
            @Override
            public void onResponse(Call<NewPojoTopTracks> call, Response<NewPojoTopTracks> response) {
                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                NewPojoTopTracks track = (NewPojoTopTracks) response.body();
                tracksList =  track.tracks.track;
                inflateRecycler();
            }

            @Override
            public void onFailure(Call<NewPojoTopTracks> call, Throwable error) {
                Log.e("error", error.toString());
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

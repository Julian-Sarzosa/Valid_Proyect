package com.example.valid_proyect.interfaces;


import com.example.valid_proyect.models.PojoTopTracksApi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Track_Interface {
    @GET("?method=geo.gettoptracks&country=spain&api_key=829751643419a7128b7ada50de590067&format=json")
    Call<PojoTopTracksApi> getUsersTopTraks();
}

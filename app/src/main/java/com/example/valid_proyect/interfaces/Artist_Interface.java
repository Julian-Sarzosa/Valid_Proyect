package com.example.valid_proyect.interfaces;

import com.example.valid_proyect.models.PojoTopArtistsApi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Artist_Interface {
    @GET("?method=user.gettopartists&user=rj&api_key=829751643419a7128b7ada50de590067&format=json")
    Call<PojoTopArtistsApi> getUsersTopArtist();
}

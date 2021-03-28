package com.example.spacex.network;

import com.example.spacex.data.SpaceData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SpaceXapi {
    @GET("crew")
    Call<List<SpaceData>> crewData();
}

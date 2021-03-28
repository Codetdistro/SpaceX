package com.example.spacex.network;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class Retrofit {
    retrofit2.Retrofit retrofit=new retrofit2.Retrofit.Builder()
            .baseUrl("https://api.spacexdata.com/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public SpaceXapi api=retrofit.create(SpaceXapi.class);
}

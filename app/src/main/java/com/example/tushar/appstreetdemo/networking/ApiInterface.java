package com.example.tushar.appstreetdemo.networking;

import com.example.tushar.appstreetdemo.model.ImageResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("top-headlines")
    Call<ImageResponse> getMovieDetails(@Query("country") String country, @Query("apikey") String apiKey);
}

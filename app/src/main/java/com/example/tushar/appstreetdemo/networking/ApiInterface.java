package com.example.tushar.appstreetdemo.networking;

import com.example.tushar.appstreetdemo.model.ImageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface ApiInterface {

    /**
     * This is the url to generate
     *https://api.cognitive.microsoft.com/bing/v7.0/images/search?q=cats&count=10
     *Ocp-Apim-Subscription-Key: 0742f95137d64d0fb7870ce7947c8978
     ***/
    @Headers("Ocp-Apim-Subscription-Key: 0742f95137d64d0fb7870ce7947c8978")
    @GET("images/search")
    Call<ImageResponse> getImageDetails(@Query("q") String searched, @Query("count") String count, @Query("offset") String offset);
}

package com.example.tushar.appstreetdemo;

import android.util.Log;

import com.example.tushar.appstreetdemo.database.ImageDatabase;
import com.example.tushar.appstreetdemo.database.Images;
import com.example.tushar.appstreetdemo.model.ImageResponse;
import com.example.tushar.appstreetdemo.networking.ApiClient;
import com.example.tushar.appstreetdemo.networking.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUrl {
    private static ImageResponse imageResponse;

    static void apiCall(final ImageDatabase movieDatabase) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ImageResponse> call = apiService.getMovieDetails("in","da7e7db5fed64d41b19af9ff1a3d98a6");
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse>call, Response<ImageResponse> response) {
                Log.d("Dashboard", "Number of movies received: "+response.body());
                imageResponse = response.body();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Images> img = new ArrayList<>();
                        for(int i=0;i<imageResponse.getArticles().size();i++){
                            Images images =  new Images();
                            images.setImageName(imageResponse.getArticles().get(i).getUrlToImage());
                            img.add(images);
                        }
                        movieDatabase.daoAccess().inertImages(img);
                    }
                }).start();
            }

            @Override
            public void onFailure(Call<ImageResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("Dashboard", t.toString());

            }
        });
    }

}

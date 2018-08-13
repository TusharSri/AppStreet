package com.example.tushar.appstreetdemo.networking;

import android.util.Log;

import com.example.tushar.appstreetdemo.database.ImageDatabase;
import com.example.tushar.appstreetdemo.interfaces.JobCallBack;
import com.example.tushar.appstreetdemo.model.ImageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUrlJob {
   // private static JobCallBack jobCallBack;
    public static void getImageUrl(String searchedString, final ImageDatabase imageDatabase,final JobCallBack jobCallBack) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ImageResponse> call = apiService.getImageDetails(searchedString,"10");
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse>call, Response<ImageResponse> response) {
                jobCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ImageResponse>call, Throwable t) {
                jobCallBack.onFailure();
                Log.e("Dashboard", t.toString());
            }
        });
    }

}

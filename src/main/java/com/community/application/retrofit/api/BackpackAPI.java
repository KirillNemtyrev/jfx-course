package com.community.application.retrofit.api;

import com.community.application.retrofit.response.BackpackResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BackpackAPI {

    @GET("/api/backpack")
    Call<BackpackResponse> getBackpack(@Header("Authorization") String token);

    @POST("/api/backpack")
    Call<ResponseBody> getDividend(@Header("Authorization") String token);

    @POST("/api/backpack/transfer")
    Call<ResponseBody> transfer(@Header("Authorization") String token);

}

package com.community.application.retrofit.api;

import com.community.application.retrofit.response.ProfileResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProfileApi {

    @GET("/api/profile")
    Call<ProfileResponse> profile(@Header("Authorization") String token);

}

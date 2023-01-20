package com.community.application.retrofit.api;

import com.community.application.retrofit.request.PasswordRequest;
import com.community.application.retrofit.response.SettingsResponse;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface SettingsApi {

    @GET("/api/settings")
    Call<SettingsResponse> get(@Header("Authorization") String token);

    @Multipart
    @POST("/api/settings/photo")
    Call<ResponseBody> photo(@Header("Authorization") String token, @Part MultipartBody.Part file);

    @POST("/api/settings/name/{name}")
    Call<ResponseBody> name(@Header("Authorization") String token, @Path("name") String name);

    @POST("/api/settings/password")
    Call<ResponseBody> password(@Header("Authorization") String token, @Body PasswordRequest passwordRequest);

}

package com.community.application.retrofit.api;

import com.community.application.retrofit.request.AuthRequest;
import com.community.application.retrofit.request.RegisterRequest;
import com.community.application.retrofit.response.TokenResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("/api/auth/signing")
    Call<TokenResponse> signing(@Body AuthRequest authRequest);

    @POST("/api/auth/signup")
    Call<ResponseBody> signup(@Body RegisterRequest registerRequest);

}

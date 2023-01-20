package com.community.application.retrofit.api;

import com.community.application.retrofit.request.BuyStockRequest;
import com.community.application.retrofit.request.SellStockRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface StockApi {
    @POST("/api/stock")
    Call<ResponseBody> buy(@Header("Authorization") String token, @Body BuyStockRequest buyStockRequest);

    @POST("/api/stock/sell")
    Call<ResponseBody> sell(@Header("Authorization") String token, @Body SellStockRequest sellStockRequest);
}

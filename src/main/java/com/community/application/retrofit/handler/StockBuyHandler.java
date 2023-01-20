package com.community.application.retrofit.handler;


import com.community.application.controller.MainController;
import com.community.application.elements.draw.CompanyDraw;
import com.community.application.elements.draw.MessageDraw;
import com.community.application.elements.draw.ProfileDraw;
import com.community.application.handler.TokenHandler;
import com.community.application.retrofit.api.ProfileApi;
import com.community.application.retrofit.response.CompanyResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

import static com.community.application.Runner.retrofit;

public class StockBuyHandler implements Callback<ResponseBody> {

    private final MessageDraw messageDraw  = MainController.messageDraw;
    private final CompanyDraw companyDraw = MainController.companyDraw;
    private final CompanyResponse companyResponse;
    private final Long count;

    public StockBuyHandler(CompanyResponse companyResponse, Long count) {
        this.companyResponse = companyResponse;
        this.count = count;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

        if (!response.isSuccessful()) {
            try {
                messageDraw.setMessage("Не удалось купить", ((response.errorBody() != null && response.errorBody().string().equals("Not enough funds!")) ? "Не достаточно средств для оплаты!" : "Не удалось купить данное количество акций! Попробуйте позже.."));
                messageDraw.error();
                messageDraw.show();
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        ProfileApi profileApi = retrofit.create(ProfileApi.class);
        profileApi.profile(TokenHandler.token).enqueue(new ProfileHandler());

        companyResponse.setStock(companyResponse.getStock() - count);
        companyDraw.show(companyResponse);
        messageDraw.setMessage("Успешно", "Поздравляем! Вы приобрели акции данной компании!");
        messageDraw.info();
        messageDraw.show();
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
        messageDraw.setMessage("Произошла ошибка!", "Не удалось дождаться ответа от сервера... Попробуйте позже");
        messageDraw.error();
        messageDraw.show();
    }
}

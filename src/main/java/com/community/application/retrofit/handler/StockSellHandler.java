package com.community.application.retrofit.handler;

import com.community.application.controller.MainController;
import com.community.application.elements.draw.CompanyDraw;
import com.community.application.elements.draw.MessageDraw;
import com.community.application.elements.draw.ProfileDraw;
import com.community.application.handler.TokenHandler;
import com.community.application.retrofit.api.ProfileApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

import static com.community.application.Runner.retrofit;

public class StockSellHandler implements Callback<ResponseBody> {

    private final MessageDraw messageDraw = MainController.messageDraw;

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (!response.isSuccessful()) {
            try {
                messageDraw.setMessage("Не удалось продать", ((response.errorBody() != null && response.errorBody().string().equals("You don't have that many shares!")) ? "У вас не имеется данного количества акций!" : "Не удалось продать данное количество акций! Попробуйте позже.."));
                messageDraw.error();
                messageDraw.show();
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        ProfileApi profileApi = retrofit.create(ProfileApi.class);
        profileApi.profile(TokenHandler.token).enqueue(new ProfileHandler());

        messageDraw.setMessage("Успешно", "Вы успешно совершили транзакцию! Ваш баланс пополнен!");
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

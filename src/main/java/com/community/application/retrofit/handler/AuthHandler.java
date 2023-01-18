package com.community.application.retrofit.handler;

import com.community.application.controller.MainController;
import com.community.application.elements.draw.MessageDraw;
import com.community.application.handler.TokenHandler;
import com.community.application.retrofit.response.TokenResponse;
import javafx.application.Platform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class AuthHandler implements Callback<TokenResponse> {

    private final MessageDraw messageDraw;

    public AuthHandler(MessageDraw messageDraw) {
        this.messageDraw = messageDraw;
    }

    @Override
    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

        if (!response.isSuccessful()){
            messageDraw.setMessage("Ошибка авторизации!", "Неверный логин или пароль!");
            messageDraw.show();
            return;
        }

        TokenResponse tokenResponse = response.body();
        if (tokenResponse != null) {
            TokenHandler.token = tokenResponse.getTokenType() + " " + tokenResponse.getAccessToken();
            Platform.runLater(() -> {
                try {
                    MainController mainController = new MainController();
                    mainController.start(messageDraw.getStage());
                } catch (IOException e) {
                    messageDraw.setMessage("Произошла ошибка!", "Не удалось загрузить сцену!");
                    messageDraw.show();
                }
            });
        } else {
            messageDraw.setMessage("Произошла ошибка!", "Попробуйте позже..");
            messageDraw.show();
        }


    }

    @Override
    public void onFailure(Call<TokenResponse> call, Throwable throwable) {
        messageDraw.setMessage("Произошла ошибка!", "Не удалось дождаться ответа от сервера... Попробуйте позже");
        messageDraw.show();
    }

}

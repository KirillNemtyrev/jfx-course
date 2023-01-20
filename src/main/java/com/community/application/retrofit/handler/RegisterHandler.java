package com.community.application.retrofit.handler;

import com.community.application.controller.SigningController;
import com.community.application.elements.draw.MessageDraw;
import com.community.application.handler.RegToAuthHandler;
import javafx.application.Platform;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class RegisterHandler implements Callback<ResponseBody> {

    private final MessageDraw messageDraw;

    public RegisterHandler(MessageDraw messageDraw) {
        this.messageDraw = messageDraw;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
            if (!response.isSuccessful()) {

                RegToAuthHandler.username = null;
                RegToAuthHandler.password = null;

                String message = response.errorBody().string();
                messageDraw.setMessage("Ошибка регистрации!",
                        message.equals("Username is already taken!") ? "Данное имя пользователя занято!" :
                                message.equals("Email Address already in use!") ? "Данный E-mail адресс уже занят!" : "Попробуйте позже...");
                messageDraw.error();
                messageDraw.show();
                return;
            }

            SigningController signingController = new SigningController();
            Platform.runLater(() -> {
                try {
                    signingController.start(messageDraw.getStage());
                } catch (IOException e) {
                    messageDraw.setMessage("Произошла ошибка!", "Произошла ошибка в смене сцены! Попробуйте позже...");
                    messageDraw.error();
                    messageDraw.show();
                }
            });

        } catch (IOException e) {
            messageDraw.setMessage("Произошла ошибка!", "Произошла ошибка! Попробуйте позже...");
            messageDraw.error();
            messageDraw.show();
        }

    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable throwable) {

        RegToAuthHandler.username = null;
        RegToAuthHandler.password = null;

        messageDraw.setMessage("Произошла ошибка!", "Не удалось дождаться ответа от сервера... Попробуйте позже");
        messageDraw.error();
        messageDraw.show();
    }
}

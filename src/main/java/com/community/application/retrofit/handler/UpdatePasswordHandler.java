package com.community.application.retrofit.handler;

import com.community.application.controller.MainController;
import com.community.application.elements.draw.MessageDraw;
import com.community.application.handler.TokenHandler;
import com.community.application.retrofit.api.SettingsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.community.application.Runner.retrofit;

public class UpdatePasswordHandler implements Callback<ResponseBody> {

    private final MessageDraw messageDraw = MainController.messageDraw;

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

        if (!response.isSuccessful()) {
            messageDraw.setMessage("Ошибка", "Неверно указан старый пароль..");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        SettingsApi settingsApi = retrofit.create(SettingsApi.class);
        settingsApi.get(TokenHandler.token).enqueue(new SettingsHandler());

        messageDraw.setMessage("Успешно", "Вы успешно изменили пароль!");
        messageDraw.info();
        messageDraw.show();

        MainController mainController = new MainController();
        mainController.updateInfo();
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
        messageDraw.setMessage("Произошла ошибка!", "Не удалось дождаться ответа от сервера... Попробуйте позже");
        messageDraw.error();
        messageDraw.show();
    }

}

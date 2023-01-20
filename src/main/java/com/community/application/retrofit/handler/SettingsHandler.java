package com.community.application.retrofit.handler;

import com.community.application.controller.MainController;
import com.community.application.elements.draw.MessageDraw;
import com.community.application.elements.draw.SettingsDraw;
import com.community.application.retrofit.response.BackpackResponse;
import com.community.application.retrofit.response.SettingsResponse;
import javafx.application.Platform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsHandler implements Callback<SettingsResponse> {

    private final MessageDraw messageDraw = MainController.messageDraw;
    private final SettingsDraw settingsDraw = MainController.settingsDraw;

    @Override
    public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {
        if(!response.isSuccessful()){
            messageDraw.setMessage("Произошла ошибка!", "Не удалось получить ответ, попробуйте позже..");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        SettingsResponse settingsResponse = response.body();
        if (settingsResponse == null){
            messageDraw.setMessage("Произошла ошибка!", "Ответ оказался пустым..");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        Platform.runLater(() -> {
            settingsDraw.setSettingsEmail(settingsResponse.getEmail());
            settingsDraw.setSettingsName(settingsResponse.getName());
            settingsDraw.setSettingsPhoto(settingsResponse.getUsername());
            settingsDraw.setSettingsDividend(settingsResponse.getLastDividend());

            settingsDraw.show();
        });
    }

    @Override
    public void onFailure(Call<SettingsResponse> call, Throwable throwable) {
        messageDraw.setMessage("Произошла ошибка!", "Не удалось дождаться ответа от сервера... Попробуйте позже");
        messageDraw.error();
        messageDraw.show();
    }
}

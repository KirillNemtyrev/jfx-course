package com.community.application.retrofit.handler;

import com.community.application.controller.MainController;
import com.community.application.elements.draw.CompanyDraw;
import com.community.application.elements.draw.MessageDraw;
import com.community.application.elements.draw.ProfileDraw;
import com.community.application.retrofit.response.ProfileResponse;
import javafx.application.Platform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileHandler implements Callback<ProfileResponse> {

    private final ProfileDraw profileDraw = MainController.profileDraw;
    private final MessageDraw messageDraw = MainController.messageDraw;
    private final CompanyDraw companyDraw = MainController.companyDraw;

    @Override
    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

        if (!response.isSuccessful()) {
            messageDraw.setMessage("Произошла ошибка!", "Не удалось получить данные!");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        ProfileResponse profileResponse = response.body();
        if (profileResponse == null) {
            messageDraw.setMessage("Произошла ошибка!", "Невозможно получить данные, попробуйте позже..");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        Platform.runLater(() -> {
            profileDraw.setProfileElement(profileResponse.getUsername(), profileResponse.getName(), profileResponse.getBalance());
            profileDraw.setBackpackElement(profileResponse.getBackpack());
            companyDraw.init(profileResponse.getCompany());
        });
    }

    @Override
    public void onFailure(Call<ProfileResponse> call, Throwable throwable) {
        messageDraw.setMessage("Произошла ошибка!", "Не удалось дождаться ответа от сервера... Попробуйте позже");
        messageDraw.error();
        messageDraw.show();
    }
}

package com.community.application.retrofit.handler;

import com.community.application.elements.draw.BackPackDraw;
import com.community.application.elements.draw.MessageDraw;
import com.community.application.retrofit.response.BackpackResponse;
import javafx.application.Platform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackpackHandler implements Callback<BackpackResponse> {

    private final MessageDraw messageDraw;
    private final BackPackDraw backPackDraw;

    public BackpackHandler(MessageDraw messageDraw, BackPackDraw backPackDraw) {
        this.messageDraw = messageDraw;
        this.backPackDraw = backPackDraw;
    }

    @Override
    public void onResponse(Call<BackpackResponse> call, Response<BackpackResponse> response) {

        if(!response.isSuccessful()){
            messageDraw.setMessage("Произошла ошибка!", "Не удалось получить ответ, попробуйте позже..");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        BackpackResponse backpackResponse = response.body();
        if (backpackResponse == null){
            messageDraw.setMessage("Произошла ошибка!", "Ответ оказался пустым..");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        Platform.runLater(() -> backPackDraw.init(backpackResponse));
    }

    @Override
    public void onFailure(Call<BackpackResponse> call, Throwable throwable) {
        messageDraw.setMessage("Произошла ошибка!", "Не удалось дождаться ответа от сервера... Попробуйте позже");
        messageDraw.error();
        messageDraw.show();
    }
}

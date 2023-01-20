package com.community.application.retrofit.handler;

import com.community.application.controller.MainController;
import com.community.application.elements.draw.MessageDraw;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferHandler implements Callback<ResponseBody> {

    private final MessageDraw messageDraw = MainController.messageDraw;

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (!response.isSuccessful()) {
            messageDraw.setMessage("Ошибка", "Ваш брокерский счёт пустой..");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        messageDraw.setMessage("Успешно", "Вы успешно перевели деньги с брокерского счёта на свой баланс!");
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

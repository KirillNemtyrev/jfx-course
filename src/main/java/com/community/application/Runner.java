package com.community.application;

import com.community.application.controller.SigningController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Runner extends Application {

    public static Retrofit retrofit;

    @Override
    public void start(Stage stage) throws IOException {

        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8745")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        stage.setTitle("Фондовая биржа");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        SigningController signingController = new SigningController();
        signingController.start(stage);

    }

    public static void main(String[] args) {
        launch();
    }

}

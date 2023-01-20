package com.community.application;

import com.community.application.controller.SigningController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Runner extends Application {

    public static Retrofit retrofit;

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {

        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8745")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        stage.setTitle("Фондовая биржа");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(Objects.requireNonNull(Runner.class.getResource("images/logo1.png")).toURI().toString()));

        SigningController signingController = new SigningController();
        signingController.start(stage);

    }

    public static void main(String[] args) {
        launch();
    }

}

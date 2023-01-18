package com.community.application.controller;

import com.community.application.Runner;
import com.community.application.elements.draw.CompanyDraw;
import com.community.application.elements.draw.MessageDraw;
import com.community.application.elements.draw.ProfileDraw;
import com.community.application.elements.image.CloseImage;
import com.community.application.elements.image.CollapseImage;
import com.community.application.elements.pane.TopPane;
import com.community.application.handler.TokenHandler;
import com.community.application.retrofit.api.ProfileApi;
import com.community.application.retrofit.handler.ProfileHandler;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.community.application.Runner.retrofit;

public class MainController extends Application {

    @FXML private Label backpackBalanceLabel;
    @FXML private Pane backpackPane;
    @FXML private Label balanceLabel;
    @FXML private ImageView collapseImg;
    @FXML private ImageView exitImg;
    @FXML private Pane loadPane;
    @FXML private ImageView messageCloseImg;
    @FXML private Label messageDescriptionLabel;
    @FXML private Pane messagePane;
    @FXML private Label messageTitleLabel;
    @FXML private Circle photoCircle;
    @FXML private Pane profilePane;
    @FXML private Pane topPane;
    @FXML private Label welcomeLabel;
    @FXML private AnchorPane companyAnchor;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Runner.class.getResource("scene/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {

        List<Pane> list = new ArrayList<>();
        list.add(loadPane);

        MessageDraw messageDraw = new MessageDraw(messageCloseImg, messageDescriptionLabel, messagePane, messageTitleLabel, list);
        messageDraw.setOnMouseEntered();
        messageDraw.setOnMouseExited();
        messageDraw.setOnMouseClicked();

        TopPane top = new TopPane(topPane);
        top.setOnMouseDragged();
        top.setOnMousePressed();

        CloseImage closeImage = new CloseImage(exitImg);
        closeImage.setOnMouseEntered();
        closeImage.setOnMouseExited();
        closeImage.setOnMouseClicked();

        CollapseImage collapseImage = new CollapseImage(collapseImg);
        collapseImage.setOnMouseEntered();
        collapseImage.setOnMouseExited();
        collapseImage.setOnMouseClicked();

        ProfileDraw profileDraw = new ProfileDraw(photoCircle, profilePane, welcomeLabel, balanceLabel, backpackPane, backpackBalanceLabel);
        CompanyDraw companyDraw = new CompanyDraw(companyAnchor);

        ProfileApi profileApi = retrofit.create(ProfileApi.class);
        profileApi.profile(TokenHandler.token).enqueue(new ProfileHandler(profileDraw, messageDraw, companyDraw));
    }
}

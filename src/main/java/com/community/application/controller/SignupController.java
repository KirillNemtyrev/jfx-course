package com.community.application.controller;

import com.community.application.Runner;
import com.community.application.elements.draw.MessageDraw;
import com.community.application.elements.field.RegisterField;
import com.community.application.elements.image.CloseImage;
import com.community.application.elements.image.CollapseImage;
import com.community.application.elements.label.AuthLabel;
import com.community.application.elements.pane.TopPane;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignupController extends Application {

    @FXML private Label authLabel;
    @FXML private ImageView collapseImg;
    @FXML private TextField emailField;
    @FXML private ImageView exitImg;
    @FXML private TextField loginField;
    @FXML private TextField nameField;
    @FXML private ImageView messageCloseImg;
    @FXML private Label messageDescriptionLabel;
    @FXML private Pane messagePane;
    @FXML private Label messageTitleLabel;
    @FXML private PasswordField passField;
    @FXML private Button registerBtn;
    @FXML private Pane loadPane;
    @FXML private Pane topPane;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Runner.class.getResource("scene/register.fxml"));
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

        RegisterField registerField = new RegisterField(loginField, nameField, emailField, passField, registerBtn, loadPane, messageDraw);
        registerField.setOnKeyReleased();
        registerField.setOnMouseEntered();
        registerField.setOnMouseExited();
        registerField.setOnMouseClicked();

        AuthLabel auth = new AuthLabel(authLabel);
        auth.setOnMouseEntered();
        auth.setOnMouseExited();
        auth.setOnMouseClicked();

    }
}

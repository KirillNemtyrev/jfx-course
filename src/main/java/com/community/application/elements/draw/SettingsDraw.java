package com.community.application.elements.draw;

import com.community.application.controller.MainController;
import com.community.application.handler.TokenHandler;
import com.community.application.retrofit.api.BackpackAPI;
import com.community.application.retrofit.api.SettingsApi;
import com.community.application.retrofit.handler.*;
import com.community.application.retrofit.request.PasswordRequest;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.community.application.Runner.retrofit;

public class SettingsDraw {

    private final Pane loadPane;
    private final Circle settingsCircle;
    private final ImageView settingsCloseImg;
    private final Button settingsDividendsBtn;
    private final Label settingsDividendsLabel;
    private final Label settingsEmailLabel;
    private final Button settingsNameBtn;
    private final Label settingsNameLabel;
    private final TextField settingsNameTextField;
    private final PasswordField settingsNewPasswordField;
    private final PasswordField settingsOldPasswordField;
    private final Pane settingsPane;
    private final Button settingsPasswordBtn;
    private final Button settingsPhotoBtn;
    private final Button settingsTransferBtn;

    public SettingsDraw(Pane loadPane, Circle settingsCircle, ImageView settingsCloseImg, Button settingsDividendsBtn, Label settingsDividendsLabel, Label settingsEmailLabel, Button settingsNameBtn, Label settingsNameLabel, TextField settingsNameTextField, PasswordField settingsNewPasswordField, PasswordField settingsOldPasswordField, Pane settingsPane, Button settingsPasswordBtn, Button settingsPhotoBtn, Button settingsTransferBtn) {
        this.loadPane = loadPane;
        this.settingsCircle = settingsCircle;
        this.settingsCloseImg = settingsCloseImg;
        this.settingsDividendsBtn = settingsDividendsBtn;
        this.settingsDividendsLabel = settingsDividendsLabel;
        this.settingsEmailLabel = settingsEmailLabel;
        this.settingsNameBtn = settingsNameBtn;
        this.settingsNameLabel = settingsNameLabel;
        this.settingsNameTextField = settingsNameTextField;
        this.settingsNewPasswordField = settingsNewPasswordField;
        this.settingsOldPasswordField = settingsOldPasswordField;
        this.settingsPane = settingsPane;
        this.settingsPasswordBtn = settingsPasswordBtn;
        this.settingsPhotoBtn = settingsPhotoBtn;
        this.settingsTransferBtn = settingsTransferBtn;
    }

    public void setOnMouseEntered() {
        settingsCloseImg.setOnMouseEntered(event -> settingsCloseImg.setOpacity(1.0));

        settingsDividendsBtn.setOnMouseEntered(event -> settingsDividendsBtn.setTextFill(Paint.valueOf("#d7d4d4")));
        settingsNameBtn.setOnMouseEntered(event -> settingsNameBtn.setTextFill(Paint.valueOf("#d7d4d4")));
        settingsPasswordBtn.setOnMouseEntered(event -> settingsPasswordBtn.setTextFill(Paint.valueOf("#d7d4d4")));
        settingsPhotoBtn.setOnMouseEntered(event -> settingsPhotoBtn.setTextFill(Paint.valueOf("#d7d4d4")));
        settingsTransferBtn.setOnMouseEntered(event -> settingsTransferBtn.setTextFill(Paint.valueOf("#d7d4d4")));
    }

    public void setOnMouseExited() {
        settingsCloseImg.setOnMouseExited(event -> settingsCloseImg.setOpacity(0.75));

        settingsDividendsBtn.setOnMouseExited(event -> settingsDividendsBtn.setTextFill(Paint.valueOf("white")));
        settingsNameBtn.setOnMouseExited(event -> settingsNameBtn.setTextFill(Paint.valueOf("white")));
        settingsPasswordBtn.setOnMouseExited(event -> settingsPasswordBtn.setTextFill(Paint.valueOf("white")));
        settingsPhotoBtn.setOnMouseExited(event -> settingsPhotoBtn.setTextFill(Paint.valueOf("white")));
        settingsTransferBtn.setOnMouseExited(event -> settingsTransferBtn.setTextFill(Paint.valueOf("white")));
    }

    public void setOnMouseClicked() {
        settingsCloseImg.setOnMouseClicked(event -> settingsPane.setVisible(false));

        settingsDividendsBtn.setOnMouseClicked(event -> getDividend());
        settingsTransferBtn.setOnMouseClicked(event -> transfer());
        settingsPhotoBtn.setOnMouseClicked(event -> setOnFileChooser());
        settingsNameBtn.setOnMouseClicked(event -> changeName());
        settingsPasswordBtn.setOnMouseClicked(event -> changePassword());
    }

    public void setSettingsName(String name){
        settingsNameLabel.setText(name);
        settingsNameTextField.setText(name);
    }

    public void setSettingsEmail(String email){
        settingsEmailLabel.setText(email);
    }

    public void setSettingsPhoto(String username){
        settingsCircle.setFill(new ImagePattern(new Image("http://localhost:8745/resources/" + username + ".png")));
    }

    public void setSettingsDividend(Long time) {
        if (time == null){
            settingsDividendsLabel.setText("Нет данных");
            return;
        }

        Date date = new Date(time);
        String lastDividend = new SimpleDateFormat("dd MMMM yyyy г.", new Locale("ru", "RU")).format(date);

        settingsDividendsLabel.setText(lastDividend);
    }

    public void setOnFileChooser() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(settingsPhotoBtn.getScene().getWindow());
        if (file != null) {
            loadPane.setVisible(true);

            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

            SettingsApi settingsApi = retrofit.create(SettingsApi.class);
            settingsApi.photo(TokenHandler.token, filePart).enqueue(new UpdatePhotoHandler());
        }
    }

    public void getDividend(){
        loadPane.setVisible(true);

        BackpackAPI backpackAPI = retrofit.create(BackpackAPI.class);
        backpackAPI.getDividend(TokenHandler.token).enqueue(new DividendHandler());
    }

    public void changeName() {
        String name = settingsNameTextField.getText().trim();
        if (name.length() < 6 || name.length() > 40) {
            MessageDraw messageDraw = MainController.messageDraw;
            messageDraw.setMessage("Ошибка в формате!", "Длина имени должна быть от 6 до 40 символов!");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        loadPane.setVisible(true);
        SettingsApi settingsApi = retrofit.create(SettingsApi.class);
        settingsApi.name(TokenHandler.token, name).enqueue(new UpdateNameHandler());
    }

    public void transfer(){
        loadPane.setVisible(true);
        BackpackAPI backpackAPI = retrofit.create(BackpackAPI.class);
        backpackAPI.transfer(TokenHandler.token).enqueue(new TransferHandler());
    }

    public void changePassword(){

        String oldPassword = settingsOldPasswordField.getText().trim();
        String newPassword = settingsNewPasswordField.getText().trim();
        MessageDraw messageDraw = MainController.messageDraw;

        if (!oldPassword.matches("^{6,40}[a-zA-Z0-9]+$")){
            messageDraw.setMessage("Ошибка формата старого пароля", "Пароль должен содержать от 6 до 40 символов, английские символы и цифры!");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        if (!newPassword.matches("^{6,40}[a-zA-Z0-9]+$")){
            messageDraw.setMessage("Ошибка формата нового пароля", "Пароль должен содержать от 6 до 40 символов, английские символы и цифры!");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        PasswordRequest passwordRequest = new PasswordRequest(oldPassword, newPassword);

        loadPane.setVisible(true);
        SettingsApi settingsApi = retrofit.create(SettingsApi.class);
        settingsApi.password(TokenHandler.token, passwordRequest).enqueue(new UpdatePasswordHandler());
    }

    public void show() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), settingsPane);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setByValue(1.0);
        fadeTransition.setAutoReverse(true);

        loadPane.setVisible(false);
        settingsPane.setVisible(true);
        fadeTransition.play();
    }
}

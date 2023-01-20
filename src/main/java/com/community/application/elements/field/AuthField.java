package com.community.application.elements.field;

import com.community.application.elements.draw.MessageDraw;
import com.community.application.retrofit.api.AuthApi;
import com.community.application.retrofit.handler.AuthHandler;
import com.community.application.retrofit.request.AuthRequest;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import static com.community.application.Runner.retrofit;

public class AuthField {
    private final TextField loginField;
    private final PasswordField passField;
    private final Button button;
    private final Pane loadPane;
    private final MessageDraw messageDraw;

    public AuthField(TextField loginField, PasswordField passField, Button button, Pane loadPane, MessageDraw messageDraw) {
        this.loginField = loginField;
        this.passField = passField;
        this.button = button;
        this.loadPane = loadPane;
        this.messageDraw = messageDraw;
    }
    
    public void setOnKeyReleased() {
        
        loginField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.TAB){
                loginField.setFocusTraversable(false);
                passField.setFocusTraversable(true);
            }

            if (event.getCode() == KeyCode.ENTER){
                signing();
            }
        });

        passField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.TAB){
                passField.setFocusTraversable(false);
                loginField.setFocusTraversable(true);
            }

            if (event.getCode() == KeyCode.ENTER){
                signing();
            }
        });
        
    }

    public void setOnMouseEntered(){
        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #d0d0d0"));
    }

    public void setOnMouseExited(){
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: white"));
    }

    public void setOnMouseClicked(){
        button.setOnMouseClicked(event -> signing());
    }

    public void signing(){

        String username = loginField.getText().trim();
        String password = passField.getText().trim();

        if (!username.matches("^{6,40}[a-zA-Z0-9]+$")){
            messageDraw.setMessage("Некоректный формат данных!", "Логин должен содержать от 6 до 40 символов, и иметь только англ. символы");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        if (!password.matches("^{6,40}[a-zA-Z0-9]+$")){
            messageDraw.setMessage("Некоректный формат данных!", "Пароль должен содержать от 6 до 40 символов, и иметь только англ. символы");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        loadPane.setVisible(true);
        AuthRequest authRequest = new AuthRequest(username, password);
        AuthApi authApi = retrofit.create(AuthApi.class);
        authApi.signing(authRequest).enqueue(new AuthHandler(messageDraw));

    }
}

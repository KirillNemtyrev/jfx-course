package com.community.application.elements.field;

import com.community.application.elements.draw.MessageDraw;
import com.community.application.handler.RegToAuthHandler;
import com.community.application.retrofit.api.AuthApi;
import com.community.application.retrofit.handler.RegisterHandler;
import com.community.application.retrofit.request.RegisterRequest;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import static com.community.application.Runner.retrofit;

public class RegisterField {
    private final TextField loginField;
    private final TextField nameField;
    private final TextField emailField;
    private final PasswordField passField;
    private final Button button;
    private final Pane pane;
    private final MessageDraw messageDraw;

    public RegisterField(TextField loginField, TextField name, TextField emailField, PasswordField pass, Button button, Pane pane, MessageDraw messageDraw) {
        this.loginField = loginField;
        this.nameField = name;
        this.emailField = emailField;
        this.passField = pass;
        this.button = button;
        this.pane = pane;
        this.messageDraw = messageDraw;
    }

    public void setOnKeyReleased() {

        loginField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.TAB){
                loginField.setFocusTraversable(false);
                nameField.setFocusTraversable(true);
            }

            if (event.getCode() == KeyCode.ENTER){
                signup();
            }
        });

        nameField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.TAB){
                nameField.setFocusTraversable(false);
                emailField.setFocusTraversable(true);
            }

            if (event.getCode() == KeyCode.ENTER){
                signup();
            }
        });

        emailField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.TAB){
                emailField.setFocusTraversable(false);
                passField.setFocusTraversable(true);
            }

            if (event.getCode() == KeyCode.ENTER){
                signup();
            }
        });

        passField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.TAB){
                passField.setFocusTraversable(false);
                loginField.setFocusTraversable(true);
            }

            if (event.getCode() == KeyCode.ENTER){
                signup();
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
        button.setOnMouseClicked(event -> signup());
    }

    public void signup() {

        String username = loginField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passField.getText().trim();

        if (!username.matches("^{6,40}[a-zA-Z0-9]+$")){
            messageDraw.setMessage("Некоректный формат данных!", "Логин должен содержать от 6 до 40 символов, и иметь только англ. символы");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        if (name.length() < 6 || name.length() > 40){
            messageDraw.setMessage("Некоректный формат данных!", "Имя должно содержать от 6 до 40 символов..");
            messageDraw.error();
            messageDraw.show();
            return;
        }

        if (!email.matches("^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$")){
            messageDraw.setMessage("Некоректный формат данных!", "Некоректно указана почта..");
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

        RegToAuthHandler.username = username;
        RegToAuthHandler.password = password;

        pane.setVisible(true);
        RegisterRequest registerRequest = new RegisterRequest(username, name, email, password);
        AuthApi authApi = retrofit.create(AuthApi.class);
        authApi.signup(registerRequest).enqueue(new RegisterHandler(messageDraw));

    }
}

package com.community.application.elements.label;

import com.community.application.controller.SignupController;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterLabel {
    private final Label label;

    public RegisterLabel(Label label) {
        this.label = label;
    }

    public void setOnMouseEntered(){
        label.setOnMouseEntered(event -> label.setTextFill(Paint.valueOf("#aeaeae")));
    }

    public void setOnMouseExited(){
        label.setOnMouseExited(event -> label.setTextFill(Paint.valueOf("#dadada")));
    }

    public void setOnMouseClicked(){
        label.setOnMouseClicked(event -> {
            try {
                Stage stage = (Stage) label.getScene().getWindow();
                SignupController signupController = new SignupController();
                signupController.start(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

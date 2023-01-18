package com.community.application.elements.label;

import com.community.application.controller.SigningController;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthLabel {
    private final Label label;

    public AuthLabel(Label label) {
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
                SigningController signupController = new SigningController();
                signupController.start(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

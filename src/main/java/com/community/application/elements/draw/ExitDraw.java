package com.community.application.elements.draw;

import com.community.application.controller.MainController;
import com.community.application.controller.SigningController;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class ExitDraw {
    private final Pane exitPane;
    private final Pane exitAccountPane;
    private final Button exitBtn;
    private final Button cancelBtn;

    public ExitDraw(Pane exitPane, Pane exitAccountPane, Button exitBtn, Button cancelBtn) {
        this.exitPane = exitPane;
        this.exitAccountPane = exitAccountPane;
        this.exitBtn = exitBtn;
        this.cancelBtn = cancelBtn;
    }

    public void setOnMouseEntered(){
        exitPane.setOnMouseEntered(event -> exitPane.setStyle("-fx-background-color: #850606; -fx-background-radius: 25px"));
        exitBtn.setOnMouseEntered(event -> exitBtn.setStyle("-fx-background-color: #9b2020"));
        cancelBtn.setOnMouseEntered(event -> cancelBtn.setStyle("-fx-background-color: #355f64"));
    }

    public void setOnMouseExited() {
        exitPane.setOnMouseExited(event -> exitPane.setStyle("-fx-background-color: #ae0707; -fx-background-radius: 25px"));
        exitBtn.setOnMouseExited(event -> exitBtn.setStyle("-fx-background-color: #c92b2b"));
        cancelBtn.setOnMouseExited(event -> cancelBtn.setStyle("-fx-background-color: #4f8d94"));
    }

    public void setOnMouseClicked() {
        exitPane.setOnMouseClicked(event -> {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), exitAccountPane);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setByValue(1.0);
            fadeTransition.setAutoReverse(true);

            exitAccountPane.setVisible(true);
            fadeTransition.play();
        });

        exitBtn.setOnMouseClicked(event -> Platform.runLater(() -> {
            try {
                SigningController signingController = new SigningController();
                signingController.start((Stage) exitBtn.getScene().getWindow());
            } catch (IOException e) {
                MessageDraw messageDraw = MainController.messageDraw;
                messageDraw.setMessage("Ошибка загрузки", "Произошла ошибка при смене сцены...");
                messageDraw.error();
                messageDraw.show();
            }
        }));

        cancelBtn.setOnMouseClicked(event -> exitAccountPane.setVisible(false));
    }
}

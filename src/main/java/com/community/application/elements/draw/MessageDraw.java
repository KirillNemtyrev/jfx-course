package com.community.application.elements.draw;

import com.community.application.Runner;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class MessageDraw {
    private final ImageView messageImg;
    private final ImageView messageCloseImg;
    private final Label messageDescriptionLabel;
    private final Pane messagePane;
    private final Label messageTitleLabel;

    private final List<Pane> list;

    public MessageDraw(ImageView messageImg, ImageView messageCloseImg, Label messageDescriptionLabel, Pane messagePane, Label messageTitleLabel, List<Pane> list) {
        this.messageImg = messageImg;
        this.messageCloseImg = messageCloseImg;
        this.messageDescriptionLabel = messageDescriptionLabel;
        this.messagePane = messagePane;
        this.messageTitleLabel = messageTitleLabel;
        this.list = list;
    }

    public void setOnMouseEntered(){
        messageCloseImg.setOnMouseEntered(event -> messageCloseImg.setOpacity(1.0));
    }

    public void setOnMouseExited(){
        messageCloseImg.setOnMouseExited(event -> messageCloseImg.setOpacity(0.75));
    }

    public void setOnMouseClicked(){
        messageCloseImg.setOnMouseClicked(event -> messagePane.setVisible(false));
    }

    public void setMessage(String title, String description) {
        Platform.runLater(() -> {
            messageTitleLabel.setText(title);
            messageDescriptionLabel.setText(description);
        });
    }

    public void error(){
        Platform.runLater(() -> messageImg.setImage(new Image(new File(Objects.requireNonNull(Runner.class.getResource("images/sad.png")).getPath()).toURI().toString())));
    }

    public void info(){
        Platform.runLater(() -> messageImg.setImage(new Image(new File(Objects.requireNonNull(Runner.class.getResource("images/logo.png")).getPath()).toURI().toString())));
    }

    public void show() {
        Platform.runLater(() -> {
            for (Pane pane : list) pane.setVisible(false);

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), messagePane);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setByValue(1.0);
            fadeTransition.setAutoReverse(true);

            messagePane.setVisible(true);
            fadeTransition.play();
        });
    }

    public Stage getStage(){
        return (Stage) messagePane.getScene().getWindow();
    }
}

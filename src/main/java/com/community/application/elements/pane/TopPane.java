package com.community.application.elements.pane;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TopPane {

    private double offsetX;
    private double offsetY;

    private final Pane pane;

    public TopPane(Pane pane) {
        this.pane = pane;
    }

    public void setOnMousePressed(){
        pane.setOnMousePressed(event -> {
            Stage stage = (Stage) pane.getScene().getWindow();
            offsetX = stage.getX() - event.getScreenX();
            offsetY = stage.getY() - event.getScreenY();
        });
    }

    public void setOnMouseDragged() {
        pane.setOnMouseDragged(event -> {
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.setX(event.getScreenX() + offsetX);
            stage.setY(event.getScreenY() + offsetY);
        });
    }
}

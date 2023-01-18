package com.community.application.elements.image;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CollapseImage {

    private final ImageView imageView;


    public CollapseImage(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setOnMouseEntered(){
        imageView.setOnMouseEntered(event -> imageView.setOpacity(1.0));
    }

    public void setOnMouseExited(){
        imageView.setOnMouseExited(event -> imageView.setOpacity(0.75));
    }

    public void setOnMouseClicked() {
        imageView.setOnMouseClicked(event -> {
            Stage stage = (Stage) imageView.getScene().getWindow();
            stage.setIconified(true);
        });
    }

}

package com.community.application.elements.image;

import javafx.scene.image.ImageView;

public class CloseImage {
    
    private final ImageView imageView;


    public CloseImage(ImageView imageView) {
        this.imageView = imageView;
    }
    
    public void setOnMouseEntered(){
        imageView.setOnMouseEntered(event -> imageView.setOpacity(1.0));
    }

    public void setOnMouseExited(){
        imageView.setOnMouseExited(event -> imageView.setOpacity(0.75));
    }

    public void setOnMouseClicked() {
        imageView.setOnMouseClicked(event -> System.exit(1));
    }
}

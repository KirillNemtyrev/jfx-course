package com.community.application.elements.draw;

import com.community.application.retrofit.response.CompanyResponse;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.math.BigDecimal;
import java.util.List;

public class CompanyDraw {
    private final AnchorPane anchorPane;

    public CompanyDraw(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public void init(List<CompanyResponse> list) {

        anchorPane.getChildren().clear();
        if (list == null || list.size() == 0){
            return;
        }


        for (int temp = 0; temp < list.size(); temp++) {

            CompanyResponse companyResponse = list.get(temp);

            Pane pane = getPane();
            pane.setLayoutY(4 + 57*temp);

            Circle photo = getCircle(companyResponse.getId());
            Label name = getLabelName(companyResponse.getName());
            Label owner = getLabelOwner(companyResponse.getOwner());
            Label count = getLabelCount(companyResponse.getStock());
            Label countDesc = getLabelForCount();
            Label cost = getLabelCost(companyResponse.getCost());
            Label stock = getLabelStock(companyResponse.getMuch(), companyResponse.isUpper(), companyResponse.getProcent());

            pane.getChildren().addAll(photo, name, owner, count, countDesc, cost, stock);
            anchorPane.getChildren().add(pane);

        }
        anchorPane.setPrefHeight(Math.max(485, 61*list.size()));

    }

    public Pane getPane() {

        Pane pane = new Pane();
        pane.setLayoutX(20);
        pane.setLayoutY(14);
        pane.setPrefSize(757, 50);
        pane.setEffect(getDropShadow());
        pane.setStyle("-fx-background-color: white");

        pane.setOnMouseEntered(event -> pane.setStyle("-fx-background-color: #dadada"));
        pane.setOnMouseExited(event -> pane.setStyle("-fx-background-color: white"));
        return pane;
    }

    private DropShadow getDropShadow(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.valueOf("#3c3b3b"));
        //dropShadow.setBlurType(BlurType.ONE_PASS_BOX);
        dropShadow.setWidth(21);
        dropShadow.setHeight(21);
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setSpread(0);
        return dropShadow;
    }

    private Circle getCircle(Long companyId){
        Circle circle = new Circle();

        Image image = new Image("http://localhost:8745/resources/company/" + companyId + ".png");
        circle.setFill(new ImagePattern(image));
        circle.setLayoutX(27);
        circle.setLayoutY(25);
        circle.setRadius(21);
        circle.setStroke(Paint.valueOf("white"));
        return circle;
    }

    private Label getLabelName(String name) {
        Label label = new Label(name);
        label.setPrefSize(228, 22);
        label.setLayoutX(55);
        label.setLayoutY(6);
        label.setFont(Font.font("Franklin Gothic Medium", 18));
        label.setTextFill(Paint.valueOf("#2c2c2c"));
        return label;
    }

    private Label getLabelOwner(String owner) {
        Label label = new Label("@" + owner);
        label.setPrefSize(228, 22);
        label.setLayoutX(55);
        label.setLayoutY(22);
        label.setFont(Font.font("Franklin Gothic Medium", 18));
        label.setTextFill(Paint.valueOf("#808080"));
        return label;
    }

    private Label getLabelCount(Long count) {
        Label label = new Label(count + " шт.");
        label.setPrefSize(237, 22);
        label.setLayoutX(281);
        label.setLayoutY(6);
        label.setFont(Font.font("Franklin Gothic Medium", 18));
        label.setTextFill(Paint.valueOf("#2c2c2c"));
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private Label getLabelForCount() {
        Label label = new Label("Доступно акций компаний");
        label.setPrefSize(232, 22);
        label.setLayoutX(284);
        label.setLayoutY(22);
        label.setFont(Font.font("Franklin Gothic Medium", 18));
        label.setTextFill(Paint.valueOf("#808080"));
        return label;
    }

    private Label getLabelCost(BigDecimal cost) {
        Label label = new Label(String.format("%.2f Р", cost));
        label.setPrefSize(166, 22);
        label.setLayoutX(573);
        label.setLayoutY(6);
        label.setFont(Font.font("Franklin Gothic Medium", 18));
        label.setTextFill(Paint.valueOf("#2c2c2c"));
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private Label getLabelStock(BigDecimal much, boolean upper, float procent) {
        Label label = new Label((upper ? "↑ " : "↓ ") + String.format("%.2f Р", much) + " (" + String.format("%.2f", procent) + "%)");
        label.setPrefSize(166, 22);
        label.setLayoutX(573);
        label.setLayoutY(22);
        label.setFont(Font.font("Franklin Gothic Medium", 18));
        label.setTextFill(Paint.valueOf(upper ? "#0f8d24" : "#c91407"));
        label.setAlignment(Pos.CENTER);
        return label;
    }
}

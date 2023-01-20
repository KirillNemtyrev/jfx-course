package com.community.application.elements.draw;

import com.community.application.Runner;
import com.community.application.controller.MainController;
import com.community.application.handler.TokenHandler;
import com.community.application.retrofit.api.BackpackAPI;
import com.community.application.retrofit.handler.BackpackHandler;
import com.community.application.retrofit.response.BackpackResponse;
import com.community.application.retrofit.response.CompanyResponse;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.community.application.Runner.retrofit;

public class BackPackDraw {
    private final Pane backpackPane;
    private final AnchorPane backpackAnchor;
    private final ImageView backpackCloseImg;
    private final Label backpackCostLabel;
    private final Pane backpackMainPane;
    private final Label scoreBalanceLabel;
    private final Pane loadPane;

    public BackPackDraw(Pane backpackPane, AnchorPane backpackAnchor, ImageView backpackCloseImg, Label backpackCostLabel, Pane backpackMainPane, Label scoreBalanceLabel, Pane loadPane) {
        this.backpackPane = backpackPane;
        this.backpackAnchor = backpackAnchor;
        this.backpackCloseImg = backpackCloseImg;
        this.backpackCostLabel = backpackCostLabel;
        this.backpackMainPane = backpackMainPane;
        this.scoreBalanceLabel = scoreBalanceLabel;
        this.loadPane = loadPane;
    }

    public void setOnMouseEntered() {
        backpackCloseImg.setOnMouseEntered(event -> backpackCloseImg.setOpacity(1.0));
        backpackPane.setOnMouseEntered(event -> backpackPane.setStyle("-fx-background-color: #5e1c05; -fx-background-radius: 25px"));
    }

    public void setOnMouseExited() {
        backpackCloseImg.setOnMouseExited(event -> backpackCloseImg.setOpacity(0.75));
        backpackPane.setOnMouseExited(event -> backpackPane.setStyle("-fx-background-color: #8a2a08; -fx-background-radius: 25px"));
    }

    public void setOnMouseClicked() {
        backpackCloseImg.setOnMouseClicked(event -> backpackMainPane.setVisible(false));
        backpackPane.setOnMouseClicked(event -> {

            loadPane.setVisible(true);

            BackpackAPI backpackAPI = retrofit.create(BackpackAPI.class);
            backpackAPI.getBackpack(TokenHandler.token).enqueue(new BackpackHandler(MainController.messageDraw, this));
        });
    }

    public void init(BackpackResponse backpackResponse) {

        backpackCostLabel.setText(String.valueOf(backpackResponse.getBalance()));
        scoreBalanceLabel.setText(String.valueOf(backpackResponse.getBrokerage()));
        companyDraw(backpackResponse.getCompanyList());

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), backpackMainPane);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setByValue(1.0);
        fadeTransition.setAutoReverse(true);

        loadPane.setVisible(false);
        backpackMainPane.setVisible(true);
        fadeTransition.play();
    }

    private void companyDraw(List<CompanyResponse> companyResponses){

        backpackAnchor.getChildren().clear();
        if (companyResponses == null || companyResponses.size() == 0){

            ImageView imageView = new ImageView(new Image(new File(Objects.requireNonNull(Runner.class.getResource("images/sad.png")).getPath()).toURI().toString()));
            imageView.setLayoutX(167);
            imageView.setLayoutY(28);

            Label label = new Label("У вас нет купленных акций.");
            label.setLayoutX(121);
            label.setLayoutY(132);
            label.setFont(Font.font("Franklin Gothic Medium", 17));
            label.setTextFill(
                    Paint.valueOf("black")
            );

            backpackAnchor.getChildren().addAll(imageView, label);
            return;
        }

        int counter = 0;
        for(CompanyResponse companyResponse : companyResponses){

            Pane pane = getPane(companyResponse);
            pane.setLayoutY(14 + 50*counter);

            Circle circle = getCircle(companyResponse.getId());
            Label name = getLabelName(companyResponse.getName());
            Label count = getLabelCount(companyResponse.getCountHave());
            Label cost = getLabelCost(companyResponse.getCost());
            Label stock = getLabelStock(companyResponse.getMuch(), companyResponse.isUpper(), companyResponse.getProcent());

            pane.getChildren().addAll(circle, name, count, cost, stock);
            backpackAnchor.getChildren().add(pane);

            counter++;
        }
        backpackAnchor.setPrefHeight(Math.max(433, companyResponses.size()*64));
    }

    private Pane getPane(CompanyResponse companyResponse) {

        Pane pane = new Pane();
        pane.setLayoutX(14);
        pane.setPrefSize(397, 42);
        pane.setEffect(getDropShadow());
        pane.setStyle("-fx-background-color: white");

        pane.setOnMouseEntered(event -> pane.setStyle("-fx-background-color: #dadada"));
        pane.setOnMouseExited(event -> pane.setStyle("-fx-background-color: white"));
        pane.setOnMouseClicked(event -> MainController.sellStockDraw.show(companyResponse));
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
        circle.setLayoutX(26);
        circle.setLayoutY(21);
        circle.setRadius(18);
        circle.setStroke(Paint.valueOf("white"));
        return circle;
    }

    private Label getLabelName(String name) {
        Label label = new Label(name);
        label.setPrefSize(140, 20);
        label.setLayoutX(50);
        label.setLayoutY(4);
        label.setFont(Font.font("Franklin Gothic Medium", 16));
        label.setTextFill(Paint.valueOf("#2c2c2c"));
        return label;
    }

    private Label getLabelCount(Long count) {
        Label label = new Label(count + " шт.");
        label.setPrefSize(140, 20);
        label.setLayoutX(50);
        label.setLayoutY(18);
        label.setFont(Font.font("Franklin Gothic Medium", 11));
        label.setTextFill(Paint.valueOf("#2c2c2c"));
        return label;
    }

    private Label getLabelCost(BigDecimal cost) {
        Label label = new Label(String.format("%.2f Р", cost));
        label.setPrefSize(193, 20);
        label.setLayoutX(196);
        label.setLayoutY(4);
        label.setFont(Font.font("Franklin Gothic Medium", 15));
        label.setTextFill(Paint.valueOf("#2c2c2c"));
        label.setAlignment(Pos.CENTER_RIGHT);
        return label;
    }

    private Label getLabelStock(BigDecimal much, boolean upper, float procent) {
        Label label = new Label((upper ? "↑ " : "↓ ") + String.format("%.2f Р", much) + " (" + String.format("%.2f", procent) + "%)");
        label.setPrefSize(193, 20);
        label.setLayoutX(193);
        label.setLayoutY(18);
        label.setFont(Font.font("Franklin Gothic Medium", 15));
        label.setTextFill(Paint.valueOf(upper ? "#0f8d24" : "#c91407"));
        label.setAlignment(Pos.CENTER_RIGHT);
        return label;
    }
}

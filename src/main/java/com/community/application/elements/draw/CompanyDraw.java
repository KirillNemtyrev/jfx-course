package com.community.application.elements.draw;

import com.community.application.handler.TokenHandler;
import com.community.application.retrofit.api.StockApi;
import com.community.application.retrofit.handler.StockBuyHandler;
import com.community.application.retrofit.request.BuyStockRequest;
import com.community.application.retrofit.response.CompanyResponse;
import com.community.application.retrofit.response.StockResponse;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

import java.math.BigDecimal;
import java.util.List;

import static com.community.application.Runner.retrofit;

public class CompanyDraw {

    private final AnchorPane anchorPane;

    private final Button companyBuyBtn;
    private final Label companyBuyLabel;
    private final ImageView companyCloseImg;
    private final Label companyCostLabel;
    private final Label companyCountLabel;
    private final Label companyDescriptionLabel;
    private final LineChart<Number, Double> companyLineChart;
    private final Label companyNameLabel;
    private final Pane companyPane;
    private final Pane loadPane;
    private final TextField countTextField;

    public CompanyDraw(AnchorPane anchorPane, Button companyBuyBtn, Label companyBuyLabel, ImageView companyCloseImg, Label companyCostLabel, Label companyCountLabel, Label companyDescriptionLabel, LineChart<Number, Double> companyLineChart, Label companyNameLabel, Pane companyPane, Pane loadPane, TextField countTextField) {
        this.anchorPane = anchorPane;
        this.companyBuyBtn = companyBuyBtn;
        this.companyBuyLabel = companyBuyLabel;
        this.companyCloseImg = companyCloseImg;
        this.companyCostLabel = companyCostLabel;
        this.companyCountLabel = companyCountLabel;
        this.companyDescriptionLabel = companyDescriptionLabel;
        this.companyLineChart = companyLineChart;
        this.companyNameLabel = companyNameLabel;
        this.companyPane = companyPane;
        this.loadPane = loadPane;
        this.countTextField = countTextField;
    }

    public void init(List<CompanyResponse> list) {

        anchorPane.getChildren().clear();
        if (list == null || list.size() == 0){
            return;
        }


        for (int temp = 0; temp < list.size(); temp++) {

            CompanyResponse companyResponse = list.get(temp);

            Circle photo = getCircle(companyResponse.getId());
            Label name = getLabelName(companyResponse.getName());
            Label owner = getLabelOwner(companyResponse.getOwner());
            Label count = getLabelCount(companyResponse.getStock());
            Label countDesc = getLabelForCount();
            Label cost = getLabelCost(companyResponse.getCost());
            Label stock = getLabelStock(companyResponse.getMuch(), companyResponse.isUpper(), companyResponse.getProcent());

            Pane pane = getPane(companyResponse);
            pane.setLayoutY(4 + 57*temp);

            pane.getChildren().addAll(photo, name, owner, count, countDesc, cost, stock);
            anchorPane.getChildren().add(pane);

        }
        anchorPane.setPrefHeight(Math.max(485, 61*list.size()));

    }

    public Pane getPane(CompanyResponse companyResponse) {

        Pane pane = new Pane();
        pane.setLayoutX(20);
        pane.setLayoutY(14);
        pane.setPrefSize(757, 50);
        pane.setEffect(getDropShadow());
        pane.setStyle("-fx-background-color: white");

        pane.setOnMouseEntered(event -> pane.setStyle("-fx-background-color: #dadada"));
        pane.setOnMouseExited(event -> pane.setStyle("-fx-background-color: white"));
        pane.setOnMouseClicked(event -> show(companyResponse));
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

    public void setOnMouseEntered() {
        companyCloseImg.setOnMouseEntered(event -> companyCloseImg.setOpacity(1.0));
        companyBuyBtn.setOnMouseEntered(event -> companyBuyBtn.setTextFill(Paint.valueOf("#d7d4d4")));
    }

    public void setOnMouseExited() {
        companyCloseImg.setOnMouseExited(event -> companyCloseImg.setOpacity(0.75));
        companyBuyBtn.setOnMouseExited(event -> companyBuyBtn.setTextFill(Paint.valueOf("white")));
    }

    public void setOnMouseClicked() {
        companyCloseImg.setOnMouseClicked(event -> companyPane.setVisible(false));
    }

    public void show(CompanyResponse companyResponse) {
        Platform.runLater(() -> {

            XYChart.Series<Number, Double> dataSeries = new XYChart.Series<>();
            companyLineChart.getData().clear();

            for (StockResponse stockResponse : companyResponse.getStocks()) {
                dataSeries.getData().add(new XYChart.Data<>(stockResponse.getDate().intValue(), stockResponse.getCost().doubleValue()));
            }
            companyLineChart.getData().clear();
            companyLineChart.getData().add(dataSeries);

            companyNameLabel.setText(companyResponse.getName());
            companyDescriptionLabel.setText(companyResponse.getDescription());
            companyCountLabel.setText(String.valueOf(companyResponse.getStock()));
            companyCostLabel.setText(String.valueOf(companyResponse.getCost().doubleValue()));
            countTextField.setText("1");
            companyBuyLabel.setText("Купить 1 шт. за " + companyResponse.getCost() + "?");

            companyBuyBtn.setOnMouseClicked(event -> buyStock(companyResponse));

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), companyPane);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setByValue(1.0);
            fadeTransition.setAutoReverse(true);

            companyPane.setVisible(true);
            fadeTransition.play();
        });
    }

    public void actionOnTextField() {
        countTextField.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (!newValue.matches("[-+]?\\d+")) {
                countTextField.setText(newValue);
                return;
            }

            int value = Integer.parseInt(newValue);
            int max = Integer.parseInt(companyCountLabel.getText());
            if (value > max) {
                countTextField.setText(oldValue);
                return;
            }

            BigDecimal cost = BigDecimal.valueOf(Double.parseDouble(companyCostLabel.getText()));
            companyBuyLabel.setText("Купить " + value + " шт. за " + cost.multiply(BigDecimal.valueOf(value)) + "?");
        }));
    }

    public void buyStock(CompanyResponse companyResponse) {

        String string = countTextField.getText().trim();
        if (string.isEmpty()) return;
        Long count = Long.valueOf(string);

        loadPane.setVisible(true);
        StockApi stockApi = retrofit.create(StockApi.class);

        BuyStockRequest buyStockRequest = new BuyStockRequest(companyResponse.getId(), count);
        stockApi.buy(TokenHandler.token, buyStockRequest).enqueue(new StockBuyHandler(companyResponse, count));
    }
}

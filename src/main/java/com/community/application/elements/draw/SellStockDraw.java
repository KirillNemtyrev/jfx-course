package com.community.application.elements.draw;

import com.community.application.handler.TokenHandler;
import com.community.application.retrofit.api.StockApi;
import com.community.application.retrofit.handler.StockSellHandler;
import com.community.application.retrofit.request.SellStockRequest;
import com.community.application.retrofit.response.CompanyResponse;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.math.BigDecimal;

import static com.community.application.Runner.retrofit;

public class SellStockDraw {
    private final Pane loadPane;
    private final Button sellStockBtn;
    private final Circle sellStockCircle;
    private final ImageView sellStockCloseImg;
    private final Label sellStockCostLabel;
    private final Label sellStockCountLabel;
    private final Label sellStockInfoLabel;
    private final Label sellStockMuchLabel;
    private final Label sellStockNameLabel;
    private final Pane sellStockPane;
    private final TextField sellStockTextField;

    public SellStockDraw(Pane loadPane, Button sellStockBtn, Circle sellStockCircle, ImageView sellStockCloseImg, Label sellStockCostLabel, Label sellStockCountLabel, Label sellStockInfoLabel, Label sellStockMuchLabel, Label sellStockNameLabel, Pane sellStockPane, TextField sellStockTextField) {
        this.loadPane = loadPane;
        this.sellStockBtn = sellStockBtn;
        this.sellStockCircle = sellStockCircle;
        this.sellStockCloseImg = sellStockCloseImg;
        this.sellStockCostLabel = sellStockCostLabel;
        this.sellStockCountLabel = sellStockCountLabel;
        this.sellStockInfoLabel = sellStockInfoLabel;
        this.sellStockMuchLabel = sellStockMuchLabel;
        this.sellStockNameLabel = sellStockNameLabel;
        this.sellStockPane = sellStockPane;
        this.sellStockTextField = sellStockTextField;
    }

    public void setOnMouseEntered() {
        sellStockCloseImg.setOnMouseEntered(event -> sellStockCloseImg.setOpacity(1.0));
        sellStockBtn.setOnMouseEntered(event -> sellStockBtn.setTextFill(Paint.valueOf("gray")));
    }

    public void setOnMouseExited() {
        sellStockCloseImg.setOnMouseExited(event -> sellStockCloseImg.setOpacity(0.75));
        sellStockBtn.setOnMouseExited(event -> sellStockBtn.setTextFill(Paint.valueOf("white")));
    }

    public void setOnMouseClicked() {
        sellStockCloseImg.setOnMouseClicked(event -> sellStockPane.setVisible(false));
    }

    public void show(CompanyResponse companyResponse){
        Platform.runLater(() -> {
            sellStockCircle.setFill(new ImagePattern(new Image("http://localhost:8745/resources/company/" + companyResponse.getId() + ".png")));
            sellStockNameLabel.setText(companyResponse.getName());
            sellStockCountLabel.setText(companyResponse.getCountHave() + " шт.");
            sellStockCostLabel.setText(companyResponse.getCost() + " руб.");

            sellStockMuchLabel.setText((companyResponse.isUpper() ? "↑ " : "↓ ") + String.format("%.2f Р", companyResponse.getMuch()) + " (" + String.format("%.2f", companyResponse.getProcent()) + "%)");
            sellStockMuchLabel.setTextFill(Paint.valueOf((companyResponse.isUpper() ? "#0f8d24" : "#c91407")));


            sellStockTextField.setText("1");
            sellStockInfoLabel.setText("При продаже 1 шт. вы получите на баланс " + companyResponse.getCost() + " руб.");
            actionOnTextField(companyResponse.getCost(), companyResponse.getCountHave());

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), sellStockPane);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setByValue(1.0);
            fadeTransition.setAutoReverse(true);

            sellStockPane.setVisible(true);
            fadeTransition.play();

            sellStockBtn.setOnMouseClicked(event -> sell(companyResponse.getId()));
        });
    }

    public void actionOnTextField(BigDecimal cost, Long count) {
        sellStockTextField.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (!newValue.matches("[-+]?\\d+")) {
                sellStockTextField.setText(newValue);
                return;
            }

            int value = Integer.parseInt(newValue);
            if (value > count) {
                sellStockTextField.setText(oldValue);
                return;
            }

            sellStockInfoLabel.setText("При продаже " + value + " шт. вы получите на баланс " + cost.multiply(BigDecimal.valueOf(value)) + "руб.");
        }));
    }

    public void sell(Long id) {
        String string = sellStockTextField.getText().trim();
        if (string.isEmpty()) return;
        Long count = Long.valueOf(string);

        loadPane.setVisible(true);
        StockApi stockApi = retrofit.create(StockApi.class);

        SellStockRequest sellStockRequest = new SellStockRequest(id, count);
        stockApi.sell(TokenHandler.token, sellStockRequest).enqueue(new StockSellHandler());
    }
}

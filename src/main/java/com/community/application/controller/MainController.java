package com.community.application.controller;

import com.community.application.Runner;
import com.community.application.elements.draw.*;
import com.community.application.elements.image.CloseImage;
import com.community.application.elements.image.CollapseImage;
import com.community.application.elements.pane.TopPane;
import com.community.application.handler.TokenHandler;
import com.community.application.retrofit.api.ProfileApi;
import com.community.application.retrofit.handler.ProfileHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.community.application.Runner.retrofit;

public class MainController extends Application {

    @FXML private Label backpackBalanceLabel;
    @FXML private Pane backpackPane;
    @FXML private Label balanceLabel;
    @FXML private ImageView collapseImg;
    @FXML private ImageView exitImg;
    @FXML private Pane loadPane;
    @FXML private ImageView messageImg;
    @FXML private ImageView messageCloseImg;
    @FXML private Label messageDescriptionLabel;
    @FXML private Pane messagePane;
    @FXML private Label messageTitleLabel;
    @FXML private Circle photoCircle;
    @FXML private Pane profilePane;
    @FXML private Pane topPane;
    @FXML private Label welcomeLabel;
    @FXML private AnchorPane companyAnchor;
    @FXML private Button companyBuyBtn;
    @FXML private Label companyBuyLabel;
    @FXML private ImageView companyCloseImg;
    @FXML private Label companyCostLabel;
    @FXML private Label companyCountLabel;
    @FXML private Label companyDescriptionLabel;
    @FXML private LineChart<Number, Double> companyLineChart;
    @FXML private Label companyNameLabel;
    @FXML private Pane companyPane;
    @FXML private TextField countTextField;

    @FXML private AnchorPane backpackAnchor;
    @FXML private ImageView backpackCloseImg;
    @FXML private Label backpackCostLabel;
    @FXML private Pane backpackMainPane;
    @FXML private Label scoreBalanceLabel;

    @FXML private Button sellStockBtn;
    @FXML private Circle sellStockCircle;
    @FXML private ImageView sellStockCloseImg;
    @FXML private Label sellStockCostLabel;
    @FXML private Label sellStockCountLabel;
    @FXML private Label sellStockInfoLabel;
    @FXML private Label sellStockMuchLabel;
    @FXML private Label sellStockNameLabel;
    @FXML private Pane sellStockPane;
    @FXML private TextField sellStockTextField;

    @FXML private Circle settingsCircle;
    @FXML private ImageView settingsCloseImg;
    @FXML private Button settingsDividendsBtn;
    @FXML private Label settingsDividendsLabel;
    @FXML private Label settingsEmailLabel;
    @FXML private Button settingsNameBtn;
    @FXML private Label settingsNameLabel;
    @FXML private TextField settingsNameTextField;
    @FXML private PasswordField settingsNewPasswordField;
    @FXML private PasswordField settingsOldPasswordField;
    @FXML private Pane settingsPane;
    @FXML private Button settingsPasswordBtn;
    @FXML private Button settingsPhotoBtn;
    @FXML private Button settingsTransferBtn;

    @FXML private Pane exitPane;
    @FXML private Pane exitAccountPane;
    @FXML private Button exitBtn;
    @FXML private Button cancelBtn;

    public static CompanyDraw companyDraw;
    public static ProfileDraw profileDraw;
    public static MessageDraw messageDraw;
    public static SettingsDraw settingsDraw;
    public static SellStockDraw sellStockDraw;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Runner.class.getResource("scene/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {

        List<Pane> list = new ArrayList<>();
        list.add(loadPane);
        list.add(companyPane);
        list.add(backpackMainPane);
        list.add(sellStockPane);

        messageDraw = new MessageDraw(
                messageImg, messageCloseImg,
                messageDescriptionLabel,
                messagePane,
                messageTitleLabel,
                list
        );
        messageDraw.setOnMouseEntered();
        messageDraw.setOnMouseExited();
        messageDraw.setOnMouseClicked();

        TopPane top = new TopPane(topPane);
        top.setOnMouseDragged();
        top.setOnMousePressed();

        CloseImage closeImage = new CloseImage(exitImg);
        closeImage.setOnMouseEntered();
        closeImage.setOnMouseExited();
        closeImage.setOnMouseClicked();

        CollapseImage collapseImage = new CollapseImage(collapseImg);
        collapseImage.setOnMouseEntered();
        collapseImage.setOnMouseExited();
        collapseImage.setOnMouseClicked();

        settingsDraw = new SettingsDraw(
                loadPane,
                settingsCircle,
                settingsCloseImg,
                settingsDividendsBtn,
                settingsDividendsLabel,
                settingsEmailLabel,
                settingsNameBtn,
                settingsNameLabel,
                settingsNameTextField,
                settingsNewPasswordField,
                settingsOldPasswordField,
                settingsPane,
                settingsPasswordBtn,
                settingsPhotoBtn,
                settingsTransferBtn
        );
        settingsDraw.setOnMouseEntered();
        settingsDraw.setOnMouseExited();
        settingsDraw.setOnMouseClicked();

        profileDraw = new ProfileDraw(
                loadPane,
                photoCircle,
                profilePane,
                welcomeLabel,
                balanceLabel,
                backpackPane,
                backpackBalanceLabel
        );
        profileDraw.setOnMouseEntered();
        profileDraw.setOnMouseExited();
        profileDraw.setOnMouseClicked();

        companyDraw = new CompanyDraw(
                companyAnchor,
                companyBuyBtn,
                companyBuyLabel,
                companyCloseImg,
                companyCostLabel,
                companyCountLabel,
                companyDescriptionLabel,
                companyLineChart,
                companyNameLabel,
                companyPane,
                loadPane,
                countTextField
        );
        companyDraw.actionOnTextField();
        companyDraw.setOnMouseEntered();
        companyDraw.setOnMouseExited();
        companyDraw.setOnMouseClicked();

        sellStockDraw = new SellStockDraw(
                loadPane,
                sellStockBtn,
                sellStockCircle,
                sellStockCloseImg,
                sellStockCostLabel,
                sellStockCountLabel,
                sellStockInfoLabel,
                sellStockMuchLabel,
                sellStockNameLabel,
                sellStockPane,
                sellStockTextField
        );
        sellStockDraw.setOnMouseEntered();
        sellStockDraw.setOnMouseExited();
        sellStockDraw.setOnMouseClicked();

        BackPackDraw backPackDraw = new BackPackDraw(
                backpackPane,
                backpackAnchor,
                backpackCloseImg,
                backpackCostLabel,
                backpackMainPane,
                scoreBalanceLabel,
                loadPane
        );
        backPackDraw.setOnMouseEntered();
        backPackDraw.setOnMouseExited();
        backPackDraw.setOnMouseClicked();

        ExitDraw exitDraw = new ExitDraw(
                exitPane,
                exitAccountPane,
                exitBtn,
                cancelBtn
        );
        exitDraw.setOnMouseEntered();
        exitDraw.setOnMouseExited();
        exitDraw.setOnMouseClicked();

        updateInfo();
    }

    public void updateInfo() {
        Platform.runLater(() -> {
            ProfileApi profileApi = retrofit.create(ProfileApi.class);
            profileApi.profile(TokenHandler.token).enqueue(new ProfileHandler());
        });
    }
}

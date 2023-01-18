package com.community.application.elements.draw;

import com.community.application.Runner;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;

public class ProfileDraw {

    private final Circle photoCircle;
    private final Pane profilePane;
    private final Label welcomeLabel;
    private final Label balanceLabel;

    private final Pane backpackPane;
    private final Label backpackBalanceLabel;

    public ProfileDraw(Circle photoCircle, Pane profilePane, Label welcomeLabel, Label balanceLabel, Pane backpackPane, Label backpackBalanceLabel) {
        this.photoCircle = photoCircle;
        this.profilePane = profilePane;
        this.welcomeLabel = welcomeLabel;
        this.balanceLabel = balanceLabel;
        this.backpackPane = backpackPane;
        this.backpackBalanceLabel = backpackBalanceLabel;
    }

    public void setProfileElement(String username, String name, BigDecimal balance) {

        Image image = new Image("http://localhost:8745/resources/" + username + ".png");
        photoCircle.setFill(new ImagePattern(image));

        ZoneId zoneId = ZoneId.systemDefault();
        LocalTime time = LocalTime.now(zoneId);
        int hour = time.getHour();
        String value = (hour < 6 ? "Доброй ночи" : hour < 12 ? "Доброго утро" :
                hour < 18 ? "Доброго дня" : "Доброго вечера") + ", " + name;

        Text text = new Text(value);
        text.setFont(Font.font("Franklin Gothic Medium", 15));

        welcomeLabel.setText(value);
        profilePane.setPrefWidth(text.getLayoutBounds().getWidth() + 76);
        backpackPane.setLayoutX(profilePane.getPrefWidth() + 27);

        text.setText("Баланс: " + String.format(Locale.US, "%.2f", balance));
        balanceLabel.setText(text.getText());
        balanceLabel.setPrefSize(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());

        ImageView imageView = new ImageView(new Image(new File(Objects.requireNonNull(Runner.class.getResource("images/ruble.png")).getPath()).toURI().toString()));
        imageView.setLayoutX(text.getLayoutBounds().getWidth() + 59);
        imageView.setLayoutY(22);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        profilePane.getChildren().add(imageView);
    }

    public void setBackpackElement(BigDecimal backpack){

        Text text = new Text("Брокерский счёт: " + String.format(Locale.US, "%.2f", backpack));
        text.setFont(Font.font("Franklin Gothic Medium", 15));

        backpackBalanceLabel.setText(text.getText());
        backpackBalanceLabel.setPrefSize(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight());
        backpackPane.setPrefWidth(text.getLayoutBounds().getWidth() + 95);

        ImageView imageView = new ImageView(new Image(new File(Objects.requireNonNull(Runner.class.getResource("images/ruble.png")).getPath()).toURI().toString()));
        imageView.setLayoutX(text.getLayoutBounds().getWidth() + 59);
        imageView.setLayoutY(22);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        backpackPane.getChildren().add(imageView);

    }
}

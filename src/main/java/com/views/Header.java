package com.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class Header {
    private static HBox header = new HBox();
    private static HBox innerLeft = new HBox();
    private static HBox innerRight = new HBox();
    public static Label usernameLabel = new Label();
    public static Tooltip userTooltip = new Tooltip();
    public static Label highWPM = new Label();
    public static Label avgWPMLabel = new Label();
    public static Button newQuote = new Button("Create Quote");
    public static Button createUser = new Button("Create User");
    public static Button loadUser = new Button("Load User");

    public static void load() {
        header.setPadding(new Insets(15, 40, 15, 40));
        header.setSpacing(50);
        header.setStyle("-fx-background-color: #336699;");
        header.getChildren().addAll(innerLeft, innerRight);

        innerLeft.setSpacing(50);
        innerLeft.setAlignment(Pos.CENTER_LEFT);
        innerLeft.setPrefWidth(720);
        setLabels();

        header.widthProperty().addListener((observable, oldWidth, newWidth) -> {
            if(innerLeft.getWidth() == 0.0) return;

            double desiredWidthRight = 0;
            double desiredWidthLeft = 0;
            if(newWidth.doubleValue() > 1100.0) {
                desiredWidthRight = Math.floor(newWidth.doubleValue() * 0.4 - 80);
                if(!innerLeft.getChildren().contains(highWPM)) {
                    innerLeft.getChildren().addAll(highWPM, avgWPMLabel);
                }
            } else {
                desiredWidthRight = Math.floor(newWidth.doubleValue() * 0.7 - 80);
                innerLeft.getChildren().removeAll(highWPM, avgWPMLabel);
            }

                desiredWidthLeft = Math.floor(newWidth.doubleValue() - 80 - desiredWidthRight);

            innerRight.setPrefWidth(desiredWidthRight);
            innerLeft.setPrefWidth(desiredWidthLeft);
        });

        innerRight.setAlignment(Pos.CENTER_RIGHT);
        innerRight.setSpacing(50);
        innerRight.setPrefWidth(400);
        innerRight.getChildren().addAll(newQuote, createUser, loadUser);
    }
    private static void setLabels() {
        userTooltip.setShowDelay(Duration.seconds(.01));
        userTooltip.setText("Highest WPM: N/A" +
                            "\nAverage WPM: N/A");
        userTooltip.setStyle("-fx-background-color: rgba(51, 103, 153, 0.8);");
        usernameLabel.setTooltip(userTooltip);
        usernameLabel.setText("Default User");
        usernameLabel.setFont(Font.font("Helvatica", FontWeight.BOLD, 25));
        usernameLabel.setStyle("-fx-text-fill: #FFFFFF");

        highWPM.setText("Highest WPM: " + 0.0);
        highWPM.setFont(Font.font("Helvatica", FontWeight.EXTRA_BOLD, 20));
        highWPM.setStyle("-fx-text-fill: #FFFFFF");

        avgWPMLabel.setText("Average WPM: " + 0.0);
        avgWPMLabel.setFont(Font.font("Helvatica", FontWeight.EXTRA_BOLD, 20));
        avgWPMLabel.setStyle("-fx-text-fill: #FFFFFF");

        innerLeft.getChildren().addAll(usernameLabel, highWPM, avgWPMLabel);
    }


    public static HBox getHeader() {
        return header;
    }
}

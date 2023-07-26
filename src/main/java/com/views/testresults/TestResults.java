package com.views.testresults;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TestResults {
    private static Scene scene;
    public static BorderPane border;
    private static VBox body = new VBox();
    private static HBox resultsContainer = new HBox();
    private static HBox buttonContainer = new HBox();

    public static Button restartButton = new Button("Restart Test");

    public static Button selectQuoteButton = new Button("Select Quote");
    public static Button nextTestButton = new Button("Next Test");

    public static TextArea userResults = new TextArea();

    public static TextArea leaderboard = new TextArea();


    public TestResults() {
        border = new BorderPane();
        border.setCenter(body);

        scene = new Scene(border, 1200, 600);

        body.setSpacing(20);
        body.setPadding(new Insets(30.0, 30.0, 30.0, 30.0));
        body.setAlignment(Pos.TOP_CENTER);
        body.getChildren().addAll(resultsContainer, buttonContainer);

        setupResultsContainer();

        buttonContainer.setSpacing(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(restartButton, nextTestButton, selectQuoteButton);

    }

    private static void setupResultsContainer() {
        Label resultsLabel = new Label("Your results");
        resultsLabel.setFont(Font.font("Helvatica", FontWeight.BOLD, 24.0));
        Label leaderboardLabel = new Label("Leaderboards");
        leaderboardLabel.setFont(Font.font("Helvatica", FontWeight.BOLD, 24.0));

        VBox userResultsVBox = new VBox();
        userResultsVBox.setAlignment(Pos.TOP_CENTER);
        userResultsVBox.setSpacing(20);
        userResultsVBox.getChildren().addAll(resultsLabel, userResults);

        VBox leaderboardVbox = new VBox();
        leaderboardVbox.setAlignment(Pos.TOP_CENTER);
        leaderboardVbox.setSpacing(20);
        leaderboardVbox.getChildren().addAll(leaderboardLabel, leaderboard);

        resultsContainer.setSpacing(30);
        resultsContainer.setAlignment(Pos.CENTER);
        resultsContainer.getChildren().addAll(userResultsVBox, leaderboardVbox);

        userResults.setEditable(false);
        userResults.setFont(Font.font("Helvatica", FontWeight.BOLD, 16.0));
        userResults.setPrefHeight(340);

        leaderboard.setEditable(false);
        leaderboard.setFont(Font.font("Helvatica", FontWeight.BOLD, 16.0));
        leaderboard.setPrefHeight(340);

        body.heightProperty().addListener((observable, oldHeight, newHeight) -> {
            if(userResults.getHeight() != 0.0) {
                double height = Math.floor(newHeight.doubleValue() * .75 -60);
                userResults.setPrefHeight(height);
            }
            if(leaderboard.getHeight() != 0.0) {
                double height = Math.floor(newHeight.doubleValue() * .75 -60);
                leaderboard.setPrefHeight(height);
                System.out.println(height);
            }
        });
    }


    public static VBox getBody() {
       return body;
    }

    public static Scene getScene() { return scene; }
}

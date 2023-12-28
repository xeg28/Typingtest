package com.views;

import com.models.LeaderboardRow;
import com.typingtest.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;

public class TestResults {
    public static BorderPane border;
    private static VBox body = new VBox();
    private static HBox resultsContainer = new HBox();
    private static HBox buttonContainer = new HBox();

    public static Button restartButton = new Button("Restart Test");

    public static Button selectQuoteButton = new Button("Select Quote");
    public static Button nextTestButton = new Button("Next Test");
    public static Label wpmLabel = new Label("You typed 0.0 wpm");
    public static Label userTopFiveLabel = new Label("Your results");
    public static Label leaderboardLabel = new Label("");

    public static TableView<Pair<Integer, Double>> userResults = new TableView<>();
    public static TableView<LeaderboardRow> leaderboard = new TableView<>();


    public static void load() {
        border = new BorderPane();
        border.setCenter(body);

        wpmLabel.setFont(Font.font("Helvatica", FontWeight.BOLD, 24.0));


        body.setSpacing(20);
        body.setPadding(new Insets(30.0, 30.0, 30.0, 30.0));
        body.setAlignment(Pos.TOP_CENTER);
        body.getChildren().addAll(wpmLabel, resultsContainer, buttonContainer);

        setupResultsContainer();

        buttonContainer.setSpacing(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(restartButton, nextTestButton, selectQuoteButton);
    }

    private static void setupResultsContainer() {

        userTopFiveLabel.setFont(Font.font("Helvatica", FontWeight.BOLD, 16.0));

        leaderboardLabel.setFont(Font.font("Helvatica", FontWeight.BOLD, 16.0));

        VBox userResultsVBox = new VBox();
        userResultsVBox.setAlignment(Pos.TOP_CENTER);
        userResultsVBox.setSpacing(20);
        userResultsVBox.getChildren().addAll(userTopFiveLabel, userResults);

        VBox leaderboardVbox = new VBox();
        leaderboardVbox.setAlignment(Pos.TOP_CENTER);
        leaderboardVbox.setSpacing(20);
        leaderboardVbox.getChildren().addAll(leaderboardLabel, leaderboard);

        resultsContainer.setSpacing(30);
        resultsContainer.setAlignment(Pos.CENTER);
        resultsContainer.getChildren().addAll(userResultsVBox, leaderboardVbox);

        userResults.setEditable(false);
        TableColumn<Pair<Integer, Double>, Integer> posCol = new TableColumn<>("Position");
        TableColumn<Pair<Integer, Double>, Double> wpmCol = new TableColumn<>("WPM");
        posCol.setCellValueFactory(new PropertyValueFactory<>("key"));
        wpmCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        userResults.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userResults.getColumns().add(posCol);
        userResults.getColumns().add(wpmCol);
        userResults.setPrefWidth(Main.primaryScene.getWidth() * .40);


        TableColumn<LeaderboardRow, Integer> posColLeaderboard = new TableColumn<>("Position");
        posColLeaderboard.setCellValueFactory(new PropertyValueFactory<>("position"));
        TableColumn<LeaderboardRow, String> userColLeaderboard = new TableColumn<>("Name");
        userColLeaderboard.setCellValueFactory(new PropertyValueFactory<>("userName"));
        TableColumn<LeaderboardRow, Double> wpmColLeaderboard = new TableColumn<>("WPM");
        wpmColLeaderboard.setCellValueFactory(new PropertyValueFactory<>("wpm"));

        leaderboard.getColumns().addAll(posColLeaderboard, userColLeaderboard, wpmColLeaderboard);
        leaderboard.setEditable(false);
        leaderboard.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        leaderboard.setPrefWidth(Main.primaryScene.getWidth() * .40);

        setListeners();
    }

    private static void setListeners() {
        body.heightProperty().addListener((observable, oldHeight, newHeight) -> {
            if(userResults.getHeight() != 0.0) {
                double height = Math.floor(newHeight.doubleValue() * .75 -60);
                userResults.setPrefHeight(height);
            }
            if(leaderboard.getHeight() != 0.0) {
                double height = Math.floor(newHeight.doubleValue() * .75 -60);
                leaderboard.setPrefHeight(height);
            }
        });

        body.widthProperty().addListener((observable, oldWidth, newWidth) -> {
            if(userResults.getWidth() != 0.0) {
                double width = Math.floor(newWidth.doubleValue() * .40);
                userResults.setPrefWidth(width);
            }
            if(leaderboard.getWidth() != 0.0) {
                double width = Math.floor(newWidth.doubleValue() * .40);
                leaderboard.setPrefWidth(width);
            }
        });
    }

    public static void setHeightForTextArea() {
        userResults.setPrefHeight(Main.primaryScene.getHeight() * .65 - 60);
        leaderboard.setPrefHeight(Main.primaryScene.getHeight() * .65 - 60);
    }

    public static BorderPane getBorder() {
        return border;
    }


}

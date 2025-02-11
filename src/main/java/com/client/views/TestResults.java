package com.client.views;

import com.client.controllers.ServerController;
import com.client.helpers.QuoteHelper;
import com.client.helpers.TypingTestHelper;
import com.client.helpers.UIUtility;
import com.client.helpers.UserHelper;
import com.client.models.Quote;
import com.client.models.Test;
import com.client.typingtest.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.List;

public class TestResults {
    public static BorderPane border;
    private static VBox body = new VBox();
    private static HBox resultsContainer = new HBox();
    private static HBox buttonContainer = new HBox();

    public static Button restartButton = new Button("Restart Test");

    public static Button selectQuoteButton = new Button("Select Quote");
    public static Button nextTestButton = new Button("Next Test");
    public static Label wpmLabel = new Label("You typed 0.0 wpm");
    public static Label userTopTestLabel = new Label("Your results");
    public static Label leaderboardLabel = new Label("");

    public static TableView<Test> userResults = new TableView<>();
    public static TableView<Test> leaderboard = new TableView<>();

    private static StackPane spinner;

    private static Node previousUserTest;

    private static StackPane leaderboardStack;

    private static StackPane userResultStack;


    public static void load() {
        border = new BorderPane();
        border.setCenter(body);

        wpmLabel.setFont(Font.font("Helvatica", FontWeight.BOLD, 24.0));


        body.setSpacing(20);
        body.setPadding(new Insets(30.0, 30.0, 30.0, 30.0));
        body.setAlignment(Pos.TOP_CENTER);
        body.getChildren().addAll(wpmLabel, resultsContainer, buttonContainer);


        buttonContainer.setSpacing(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(restartButton, nextTestButton, selectQuoteButton);
        spinner = UIUtility.getSpinner(Main.primaryScene.getWidth() * .40);
        leaderboardStack = new StackPane();
        leaderboardStack.setPrefWidth(Main.primaryScene.getWidth()* .40);

        userResultStack = new StackPane();
        userResultStack.setPrefWidth(Main.primaryScene.getWidth()* .40);

        setupResultsContainer();
        createTimeline();
    }

    public static void createTimeline() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(50), e -> {
                    List<Test> tests = UserHelper.userTestsForCurrentQuote;
                    boolean hasResultSpinner = userResultStack.getChildren().size() == 2;
                    Quote currentQuote = QuoteHelper.currentQuote;

                    if(ServerController.isConnected() && tests == null && !hasResultSpinner
                    && UserHelper.currentUser != null && currentQuote != null
                    ) {
                        userResultStack.getChildren().add(UIUtility.getSpinner());
                    }
                    else if((tests != null && hasResultSpinner || hasResultSpinner && currentQuote == null) ||
                            tests == null && hasResultSpinner && UserHelper.currentUser == null) {
                        userResultStack.getChildren().remove(1);
                        TypingTestHelper.setTextInResultTextArea();
                    }

                    List<Test> leaderboardTests = QuoteHelper.leaderBoard;
                    boolean hasSpinner = leaderboardStack.getChildren().size() == 2;
                    if(ServerController.isConnected() && leaderboardTests == null && !hasSpinner
                        && currentQuote != null) {
                        leaderboardStack.getChildren().add(UIUtility.getSpinner());
                    }
                    else if(leaderboardTests != null && hasSpinner || hasSpinner && currentQuote == null) {
                        leaderboardStack.getChildren().remove(1);
                        TypingTestHelper.setTextInLeaderboardsTextArea();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private static void setupResultsContainer() {

        userTopTestLabel.setFont(Font.font("Helvatica", FontWeight.BOLD, 16.0));

        leaderboardLabel.setFont(Font.font("Helvatica", FontWeight.BOLD, 16.0));

        VBox userResultsVBox = new VBox();
        userResultsVBox.setAlignment(Pos.TOP_CENTER);
        userResultsVBox.setSpacing(20);
        userResultsVBox.getChildren().addAll(userTopTestLabel, userResultStack);

        VBox leaderboardVbox = new VBox();
        leaderboardVbox.setAlignment(Pos.TOP_CENTER);
        leaderboardVbox.setSpacing(20);
        leaderboardVbox.getChildren().addAll(leaderboardLabel, leaderboardStack);

        resultsContainer.setSpacing(30);
        resultsContainer.setAlignment(Pos.CENTER);
        resultsContainer.getChildren().addAll(userResultsVBox, leaderboardVbox);

        userResults.setEditable(false);
        userResults.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userResults.setPrefWidth(Main.primaryScene.getWidth() * .40);
        userResults.setPlaceholder(new Label(""));


        TableColumn<Test, Number> posColResult = new TableColumn<>("Position");
        posColResult.setCellValueFactory(cellData -> {
            int rowIndex = userResults.getItems().indexOf(cellData.getValue()) + 1;
            return new SimpleIntegerProperty(rowIndex);
        });

        posColResult.prefWidthProperty().bind(userResults.prefWidthProperty().multiply(0.25));


        TableColumn<Test, Number> wpmColResult = new TableColumn<>("WPM");
        wpmColResult.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getWpm()));

        wpmColResult.prefWidthProperty().bind(userResults.prefWidthProperty().multiply(0.25));


        TableColumn<Test, String> testTimeColResult = new TableColumn<>("Test Date");
        testTimeColResult.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTestTime()));

        testTimeColResult.prefWidthProperty().bind(userResults.prefWidthProperty().multiply(0.50));


        userResults.getColumns().addAll(posColResult, wpmColResult, testTimeColResult);



        TableColumn<Test, Number> posColLeaderboard = new TableColumn<>("Position");
        posColLeaderboard.setCellValueFactory(cellData -> {
            int rowIndex = leaderboard.getItems().indexOf(cellData.getValue()) + 1;
            return new SimpleIntegerProperty(rowIndex);
        });
        posColLeaderboard.prefWidthProperty().bind(leaderboard.prefWidthProperty().multiply(0.15));

        TableColumn<Test, String> userColLeaderboard = new TableColumn<>("Username");
        userColLeaderboard.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        userColLeaderboard.prefWidthProperty().bind(leaderboard.prefWidthProperty().multiply(0.30));

        TableColumn<Test, Number> wpmColLeaderboard = new TableColumn<>("WPM");
        wpmColLeaderboard.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getWpm()));
        wpmColLeaderboard.prefWidthProperty().bind(leaderboard.prefWidthProperty().multiply(0.15));

        TableColumn<Test, String> testTimeColLeaderboard = new TableColumn<>("Test Date");
        testTimeColLeaderboard.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTestTime()));
        testTimeColLeaderboard.prefWidthProperty().bind(leaderboard.prefWidthProperty().multiply(0.40));

        leaderboard.getColumns().addAll(posColLeaderboard, userColLeaderboard, wpmColLeaderboard, testTimeColLeaderboard);
        leaderboard.setEditable(false);
        leaderboard.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        leaderboard.setPrefWidth(Main.primaryScene.getWidth() * .40);
        leaderboard.setPlaceholder(new Label(""));
        userResultStack.getChildren().add(userResults);
        leaderboardStack.getChildren().add(leaderboard);

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

            if(spinner.getHeight() != 0.0) {
                double height = Math.floor(newHeight.doubleValue() * .75 -60);
                spinner.setPrefHeight(height);
            }
            if(spinner.getHeight() != 0.0) {
                double height = Math.floor(newHeight.doubleValue() * .75 -60);
                spinner.setPrefHeight(height);
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
            if(spinner.getWidth() != 0.0) {
                double width = Math.floor(newWidth.doubleValue() * .40);
                spinner.setPrefWidth(width);
            }
            if(spinner.getWidth() != 0.0) {
                double width = Math.floor(newWidth.doubleValue() * .40);
                spinner.setPrefWidth(width);
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

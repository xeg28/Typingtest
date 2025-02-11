package com.client.typingtest;

import com.client.controllers.*;
import com.client.helpers.AlertHelper;
import com.client.helpers.QuoteHelper;
import com.client.views.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application  {
    public static Stage primaryStage;

    public static Scene primaryScene;
    private static ServerController serverController;
    private static RequestController requestController;
    @Override
    public void start(Stage stage) {
        Image icon = new Image(this.getClass().getResourceAsStream("icon.png"));
        TitleScreen.load();
        TitleScreenController.setHandlersForTitleScreen();
        BorderPane border = TitleScreen.getBorder();
        Scene scene = new Scene(border, 1200, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.getIcons().add(icon);
        stage.setTitle("Typing Test");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();

        stage.setOnHiding(event -> {
            Register.getStage().close();
            Login.getStage().close();
            serverController.stop();
            requestController.stop();
            System.exit(0);
        });

        primaryStage = stage;
        primaryScene = scene;

        connectToServer();
        loadServerListener();

        loadHeader(border);
        loadTitleResults();
        loadAddQuote();
        loadListQuotes();
        loadCreateUser();
        loadLoadUser();
        loadErrorTimeline();
    }

    public static void main(String[] args) {
        Application.launch();
    }

    public static void loadErrorTimeline() {
        AlertHelper.loadAlertTimeline();
    }

    public void connectToServer() {
        serverController = new ServerController();
        serverController.start();
    }

    public void loadServerListener() {
        requestController =  new RequestController();
        requestController.start();
    }

    public void loadAddQuote() {
        AddQuote.load();
        AddQuoteController.setHandlersForAddQuote();
    }
    public void loadHeader(BorderPane border) {
        Header.load();
        border.setTop(Header.getHeader());
        HeaderController.setHandlersForHeader();
    }

    public void loadListQuotes() {
        QuoteHelper.updateQuotes();
        ListQuotes.load();
        ListQuotesController.setHandlersForListQuotes();
    }


    public void loadTitleResults() {
        TestResults.load();
        TestResultsController.setHandlersForTestResults();
    }

    public void loadCreateUser() {
        Register.load();
        CreateUserController.setHandlersForCreateUser();
    }

    public void loadLoadUser() {
        Login.load();
        LoadUserController.setHandlersForLoadUsers();
    }


}

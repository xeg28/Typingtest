package com.typingtest;

import com.controllers.TestResults.TestResultsController;
import com.controllers.addquote.AddQuoteController;
import com.controllers.listquotes.ListQuotesController;
import com.controllers.template.HeaderController;
import com.controllers.titlescreen.TitleScreenController;
import com.helpers.WriteAndReadHelper;
import com.views.addquote.AddQuote;
import com.views.listquotes.ListQuotes;
import com.views.testresults.TestResults;
import com.views.titlescreen.TitleScreen;
import com.views.template.Header;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application  {
    public static Stage primaryStage;

    public static Scene primaryScene;

    @Override
    public void start(Stage stage) {
        Image icon = new Image(this.getClass().getResourceAsStream("icon.png"));
        TitleScreen.load();
        TitleScreenController.setHandlersForTitleScreen();
        BorderPane border = TitleScreen.getBorder();
        Scene scene = new Scene(border, 1200, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        loadHeader(border);
        loadTitleResults();
        loadAddQuote();
        loadListQuotes();
        WriteAndReadHelper.setQuoteIdSeed();

        stage.getIcons().add(icon);
        stage.setTitle("Typing Test");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();

        primaryStage = stage;
        primaryScene = scene;
    }

    public static void main(String[] args) {
        Application.launch();
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
        ListQuotes.load();
        ListQuotesController.setHandlersForListQuotes();

    }

    public void loadTitleResults() {
        TestResults.load();
        TestResultsController.setHandlersForTestResults();
    }
}

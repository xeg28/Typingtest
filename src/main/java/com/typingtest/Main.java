package com.typingtest;

import com.controllers.TestResults.TestResultsController;
import com.controllers.addquote.AddQuoteController;
import com.controllers.template.HeaderController;
import com.controllers.titlescreen.TitleScreenController;
import com.helpers.WriteAndReadHelper;
import com.views.addquote.AddQuote;
import com.views.testresults.TestResults;
import com.views.titlescreen.TitleScreen;
import com.views.template.Header;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application  {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        Image icon = new Image(this.getClass().getResourceAsStream("icon.png"));
        TitleScreen titleScreen = new TitleScreen();
        TitleScreenController.setHandlersForTitleScreen();
        BorderPane border = titleScreen.getBorder();
        Scene scene = titleScreen.getScene();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        loadHeader(border);
        loadTitleResults();
        loadAddQuote();
        WriteAndReadHelper.setQuoteIdSeed();

        stage.getIcons().add(icon);
        stage.setTitle("Typing Test");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();

        primaryStage = stage;
    }

    public static void main(String[] args) {
        Application.launch();
    }

    public void loadAddQuote() {

        AddQuote addQuote = new AddQuote();
        AddQuoteController.setHandlersForAddQuote();
    }
    public void loadHeader(BorderPane border) {
        Header header = new Header();
        border.setTop(Header.getHeader());
        HeaderController.setHandlersForHeader();
    }

    public void loadTitleResults() {
        TestResults results = new TestResults();
        TestResultsController.setHandlersForTestResults();
    }
}

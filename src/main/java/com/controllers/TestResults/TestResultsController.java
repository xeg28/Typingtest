package com.controllers.TestResults;

import com.helpers.QuoteHelper;
import com.typingtest.Main;
import com.views.listquotes.ListQuotes;
import com.views.template.Header;
import com.views.testresults.TestResults;
import com.views.titlescreen.TitleScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;

public class TestResultsController {
    public static void setHandlersForTestResults() {
        setHandlerForRestartButton();
        setHandlerForNextTest();
        setHandlerForSelectQuote();
    }

    private static void setHandlerForRestartButton() {
        TestResults.restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TitleScreen.getBorder().setTop(Header.getHeader());
                TitleScreen.wpmLabel.setText("0.0 wpm");
                Main.primaryStage.setScene(TitleScreen.getScene());
            }
        });
    }

    public static void setHandlerForNextTest() {
        TestResults.nextTestButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TitleScreen.getBorder().setTop(Header.getHeader());
                TitleScreen.wpmLabel.setText("0.0 wpm");
                QuoteHelper.setRandomQuote();
                Main.primaryStage.setScene(TitleScreen.getScene());
            }
        });
    }

    public static void setHandlerForSelectQuote() {
        TestResults.selectQuoteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                QuoteHelper.addQuotesToList();
                ListQuotes.setLastScene(Main.primaryStage.getScene());
                ListQuotes.getBorder().setTop(Header.getHeader());
                Main.primaryStage.setScene(ListQuotes.getScene());
            }
        });
    }
}

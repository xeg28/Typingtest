package com.controllers;

import com.helpers.QuoteHelper;
import com.typingtest.Main;
import com.views.ListQuotes;
import com.views.Header;
import com.views.TestResults;
import com.views.TitleScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

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
                TitleScreen.quoteTextArea.setScrollTop(0);
                TitleScreen.getBorder().setTop(Header.getHeader());
                Main.primaryScene.setRoot(TitleScreen.getBorder());
            }
        });
    }

    public static void setHandlerForNextTest() {
        TestResults.nextTestButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TitleScreen.getBorder().setTop(Header.getHeader());
                QuoteHelper.setRandomQuote();
                Main.primaryScene.setRoot(TitleScreen.getBorder());
            }
        });
    }

    public static void setHandlerForSelectQuote() {
        TestResults.selectQuoteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                QuoteHelper.addQuotesToList();

                ListQuotes.setLastRoot(TestResults.getBorder());
                ListQuotes.getBorder().setTop(Header.getHeader());
                Main.primaryScene.setRoot(ListQuotes.getBorder());
            }
        });
    }
}

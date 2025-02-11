package com.client.controllers;

import com.client.helpers.QuoteHelper;
import com.client.helpers.UserHelper;
import com.client.typingtest.Main;
import com.client.views.ListQuotes;
import com.client.views.Header;
import com.client.views.TestResults;
import com.client.views.TitleScreen;
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
                QuoteHelper.leaderBoard = null;
                UserHelper.userTestsForCurrentQuote = null;
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
                QuoteHelper.leaderBoard = null;
                UserHelper.userTestsForCurrentQuote = null;
            }
        });
    }

    public static void setHandlerForSelectQuote() {
        TestResults.selectQuoteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(ServerController.isConnected()) QuoteHelper.updateQuotes();
                ListQuotes.setLastRoot(TestResults.getBorder());
                ListQuotes.getBorder().setTop(Header.getHeader());
                Main.primaryScene.setRoot(ListQuotes.getBorder());
            }
        });
    }
}

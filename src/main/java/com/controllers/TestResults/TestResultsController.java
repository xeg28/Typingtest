package com.controllers.TestResults;

import com.helpers.QuoteHelper;
import com.typingtest.Main;
import com.views.template.Header;
import com.views.testresults.TestResults;
import com.views.titlescreen.TitleScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TestResultsController {
    public static void setHandlersForTestResults() {
        setHandlerForRestartButton();
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
}

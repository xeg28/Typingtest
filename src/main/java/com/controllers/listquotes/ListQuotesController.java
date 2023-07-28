package com.controllers.listquotes;

import com.helpers.QuoteHelper;
import com.models.Quote;
import com.typingtest.Main;
import com.views.listquotes.ListQuotes;
import com.views.template.Header;
import com.views.testresults.TestResults;
import com.views.titlescreen.TitleScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import java.util.List;

public class ListQuotesController {
    public static void setHandlersForListQuotes() {
        backBtnHandler();
        selectBtnHandler();
    }

    public static void backBtnHandler() {
        ListQuotes.backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Scene targetScene = ListQuotes.getLastScene();
                if(targetScene == TitleScreen.getScene()) {
                    TitleScreen.getBorder().setTop(Header.getHeader());
                    Main.primaryStage.setScene(targetScene);
                }
                if(targetScene == TestResults.getScene()) {
                    TestResults.border.setTop(Header.getHeader());
                    Main.primaryStage.setScene(targetScene);
                }
            }
        });
    }

    public static void selectBtnHandler() {
        ListQuotes.selectQuoteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int itemIndex = ListQuotes.getQuoteList().getSelectionModel().getSelectedIndex();
                if(itemIndex >= 0 && itemIndex < QuoteHelper.getQuotesSize()){
                    Quote quoteSelected = QuoteHelper.getQuote(itemIndex);
                    TitleScreen.quoteTextArea.setText(quoteSelected.getQuote());

                    TitleScreen.getBorder().setTop(Header.getHeader());
                    Main.primaryStage.setScene(TitleScreen.getScene());
                }
            }
        });
    }


}

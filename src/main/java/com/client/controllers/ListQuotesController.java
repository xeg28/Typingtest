package com.client.controllers;

import com.client.helpers.*;
import com.client.models.Quote;
import com.client.typingtest.Main;
import com.client.views.ListQuotes;
import com.client.views.Header;
import com.client.views.TitleScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;


public class ListQuotesController {
    public static void setHandlersForListQuotes() {
        backBtnHandler();
        selectBtnHandler();
        deleteBtnHandler();
    }

    public static void backBtnHandler() {
        ListQuotes.backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                BorderPane targetRoot = ListQuotes.getLastRoot();
                targetRoot.setTop(Header.getHeader());
                Main.primaryScene.setRoot(targetRoot);
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
                    QuoteHelper.currentQuote = quoteSelected;
                    TitleScreen.quoteTextArea.setText(quoteSelected.getQuote());
                    TypingTestHelper.setScrollPoints(TitleScreen.quoteTextArea);

                    TitleScreen.getBorder().setTop(Header.getHeader());
                    Main.primaryScene.setRoot(TitleScreen.getBorder());
                    QuoteHelper.leaderBoard = null;
                    UserHelper.userTestsForCurrentQuote = null;
                }
            }
        });
    }

    public static void deleteBtnHandler() {
        ListQuotes.deleteQuoteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                int itemIndex = ListQuotes.getQuoteList().getSelectionModel().getSelectedIndex();

                if(itemIndex >= 0 && itemIndex < QuoteHelper.getQuotesSize()) {
                    Quote quote = QuoteHelper.getQuote(itemIndex);
                    boolean userConfirmation = AlertHelper.createAlertConformation("Delete Quote Warning",
                            "Are you sure you want to delete '" + quote.getTitle() + "'?");
                    if(!userConfirmation) return;


//                    WriteAndReadHelper.deleteQuote(itemIndex);
//                    UserHelper.deleteTopFiveForQuote(quote.getId());

//                    ListQuotes.getQuoteList().getSelectionModel().clearSelection();
                    RequestController.sendQuoteDelete(quote.getId());
                }
            }
        });
    }
}

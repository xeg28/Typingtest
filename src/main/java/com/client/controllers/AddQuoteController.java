package com.client.controllers;

import com.client.helpers.AlertHelper;
import com.client.helpers.QuoteHelper;
import com.client.helpers.UserHelper;
import com.client.helpers.WriteAndReadHelper;
import com.client.models.Quote;
import com.client.typingtest.Main;
import com.client.views.AddQuote;
import com.client.views.Header;
import com.client.views.TitleScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;

public class AddQuoteController {

    public static void setHandlersForAddQuote() {
        AddQuote.backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // We access this page from the header, so we look there for the previous page
                BorderPane lastRoot = AddQuote.getLastRoot();

                lastRoot.setTop(Header.getHeader());
                Main.primaryScene.setRoot(lastRoot);
            }
        });

        AddQuote.addQuoteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String title = AddQuote.quoteTitle.getText();
                String quoteText = AddQuote.quote.getText().trim();

                if(UserHelper.currentUser == null) {
                    AlertHelper.errors.add("Not logged in|Login to create a quote.");
                    return;
                }
                if(title.isEmpty() || quoteText.isEmpty()) {
                    AlertHelper.errors.add("Missing Title or Quote|Enter a title and quote.");
                    return;
                }
                if(quoteText.length() < 45) {
                    AlertHelper.errors.add("Quote Length Alert|The quote must contain 45 or more characters.");
                    return;
                }

                Quote quote = new Quote(title, quoteText, UserHelper.currentUser.getName());

                if(QuoteHelper.getQuotes().isEmpty()) {
//                    WriteAndReadHelper.writeQuote(quote);
                    if(ServerController.isConnected()) RequestController.sendQuoteInsert(quote);
                    QuoteHelper.updateQuotes();
                    QuoteHelper.setRandomQuote();
                }
                else {
                    if(ServerController.isConnected()) RequestController.sendQuoteInsert(quote);
//                    WriteAndReadHelper.writeQuote(quote);
                    QuoteHelper.updateQuotes();
                }

                TitleScreen.getBorder().setTop(Header.getHeader());
                Main.primaryScene.setRoot(TitleScreen.getBorder());
                AddQuote.quoteTitle.clear();
                AddQuote.quote.clear();
            }
        });
    }
}

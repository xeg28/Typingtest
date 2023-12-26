package com.controllers;

import com.helpers.AlertHelper;
import com.helpers.QuoteHelper;
import com.helpers.WriteAndReadHelper;
import com.models.Quote;
import com.typingtest.Main;
import com.views.AddQuote;
import com.views.Header;
import com.views.TitleScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AddQuoteController {

    public static void setHandlersForAddQuote() {
        AddQuote.backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // We access this page from the header so we look there for the previous page
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

                if(title.isEmpty() || quoteText.isEmpty()) {
                    AlertHelper.createAlert("Missing Title or Quote",
                            "Enter a title and a quote.");
                    return;
                }
                if(quoteText.length() < 45) {
                    AlertHelper.createAlert("Quote Length Alert",
                            "The quote's length must contain 45 or more characters.");
                    return;
                }

                Quote quote = new Quote(title, quoteText);

                if(QuoteHelper.getQuotes().isEmpty()) {
                    WriteAndReadHelper.writeQuote(quote);
                    QuoteHelper.updateQuotes();
                    QuoteHelper.setRandomQuote();
                }
                else {
                    WriteAndReadHelper.writeQuote(quote);
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

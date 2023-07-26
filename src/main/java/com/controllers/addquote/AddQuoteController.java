package com.controllers.addquote;

import com.controllers.template.HeaderController;
import com.helpers.QuoteHelper;
import com.helpers.WriteAndReadHelper;
import com.models.Quote;
import com.typingtest.Main;
import com.views.addquote.AddQuote;
import com.views.template.Header;
import com.views.testresults.TestResults;
import com.views.titlescreen.TitleScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import java.util.List;

public class AddQuoteController {

    public static void setHandlersForAddQuote() {
        AddQuote.backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // We access this page from the header so we look there for the previous page
                Scene lastScene = HeaderController.getLastScene();
                if(lastScene == TitleScreen.getScene()) {
                    TitleScreen.getBorder().setTop(Header.getHeader());
                    Main.primaryStage.setScene(TitleScreen.getScene());
                }
                else if(lastScene == TestResults.getScene()) {
                    TestResults.border.setTop(Header.getHeader());
                    Main.primaryStage.setScene(TestResults.getScene());
                }
            }
        });

        AddQuote.addQuoteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String title = AddQuote.quoteTitle.getText();
                String quoteText = AddQuote.quote.getText();

                if(!title.isEmpty() && !quoteText.isEmpty()) {
                    Quote quote = new Quote(title, quoteText);
                    System.out.println("Id seed: " + quote.getIdSeed());
                    WriteAndReadHelper.writeQuote(quote);
                    QuoteHelper.updateQuotes();
                }
            }
        });
    }

}

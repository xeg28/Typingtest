package com.client.helpers;

import com.client.controllers.RequestController;
import com.client.controllers.ServerController;
import com.client.models.LeaderboardPair;
import com.client.models.Quote;
import com.client.models.Test;
import com.client.views.ListQuotes;
import com.client.views.TitleScreen;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class QuoteHelper {
    private static List<Quote> quotes;

    public static Quote currentQuote;

    public static List<Test> leaderBoard;

    public static void updateQuotes() {
        if(ServerController.isConnected()) {
            RequestController.sendQuotesRequest();
        }
    }

    public static void setQuotes(ArrayList<Quote> quoteList) {
        quotes = quoteList;
    }

    public static void setRandomQuote() {
        if(quotes != null && !quotes.isEmpty()) {
            Quote randQuote = quotes.get((int)(Math.random() * quotes.size()));
            TitleScreen.quoteTextArea.setText(randQuote.getQuote());
            TypingTestHelper.setScrollPoints(TitleScreen.quoteTextArea);
            currentQuote = randQuote;
        }
//        else {
//            currentQuote = null;
//            TitleScreen.quoteTextArea.setText("");
//        }
    }

    public static void addQuotesToList() {
        ListView<String> quoteList = ListQuotes.getQuoteList();
        quoteList.getItems().clear();
        for(int i = 0; i < quotes.size(); i++) {
            quoteList.getItems().add(quotes.get(i).getTitle());
        }
    }

    public static void deleteQuote(int quoteId) {
        for(int i = 0; i < quotes.size(); i++) {
            Quote quote = quotes.get(i);
            if(quote.getId() == quoteId) {
                quotes.remove(i);
                if(quote.getId() == currentQuote.getId()) setRandomQuote();
                return;
            }
        }
    }

    public static Quote getQuote(int index) {
        return quotes.get(index);
    }

    public static List<Quote> getQuotes() {
        return quotes;
    }

    public static int getQuotesSize() {
        return quotes.size();
    }

}

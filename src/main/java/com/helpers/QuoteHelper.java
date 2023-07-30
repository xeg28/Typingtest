package com.helpers;

import com.models.Quote;
import com.views.listquotes.ListQuotes;
import com.views.titlescreen.TitleScreen;
import javafx.scene.control.ListView;

import java.util.List;

public class QuoteHelper {
    private static List<Quote> quotes = WriteAndReadHelper.readQuotes();

    public static void updateQuotes() {
        quotes = WriteAndReadHelper.readQuotes();
    }

    public static void setRandomQuote() {
        if(!quotes.isEmpty()) {
            Quote randQuote = quotes.get((int)(Math.random() * quotes.size()));
            TitleScreen.quoteTextArea.setText(randQuote.getQuote());
        }
    }

    public static void addQuotesToList() {
        ListView<String> quoteList = ListQuotes.getQuoteList();
        quoteList.getItems().clear();
        for(int i = 0; i < quotes.size(); i++) {
            quoteList.getItems().add(quotes.get(i).getTitle());
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

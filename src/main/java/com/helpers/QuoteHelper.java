package com.helpers;

import com.models.Quote;
import com.views.titlescreen.TitleScreen;

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
}

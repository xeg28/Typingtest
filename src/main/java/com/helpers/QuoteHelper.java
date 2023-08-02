package com.helpers;

import com.models.LeaderboardPair;
import com.models.Quote;
import com.views.ListQuotes;
import com.views.TitleScreen;
import javafx.scene.control.ListView;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class QuoteHelper {
    private static List<Quote> quotes = WriteAndReadHelper.readQuotes();

    public static Quote currentQuote;

    public static void updateQuotes() {
        quotes = WriteAndReadHelper.readQuotes();
    }

    public static void updateTopFive(double wpm) {
        List<LeaderboardPair> topFive = currentQuote.getTopFiveTests();
        LeaderboardPair newPair = new LeaderboardPair(UserHelper.currentUser.getId(), wpm);
        if(topFive == null) {
            currentQuote.setTopFiveTests(new ArrayList<>());
            topFive = currentQuote.getTopFiveTests();
        }
        topFive.add(newPair);

        int j = topFive.size() - 1;
        while(j > 0 && wpm > topFive.get(j - 1).getWpm()) {
            topFive.set(j, topFive.get(j - 1));
            j--;
        }
        topFive.set(j, newPair);

        WriteAndReadHelper.updateQuotes(quotes);
    }

    public static void deleteUserInLeaderboards(int userId) {
        for(Quote quote : quotes) {
            List<LeaderboardPair> topFive = quote.getTopFiveTests();
            for(int i = 0; i < topFive.size(); i++) {
                if(topFive.get(i).getUserId() == userId) {
                    topFive.remove(i--);
                }
            }
        }
        WriteAndReadHelper.updateQuotes(quotes);
    }

    public static void setRandomQuote() {
        if(!quotes.isEmpty()) {
            Quote randQuote = quotes.get((int)(Math.random() * quotes.size()));
            TitleScreen.quoteTextArea.setText(randQuote.getQuote());
            currentQuote = randQuote;
        }
        else {
            currentQuote = null;
            TitleScreen.quoteTextArea.setText("There are no more quotes avaiable, click on the create quote button to add quotes.");
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

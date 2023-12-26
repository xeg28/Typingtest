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
        int userId = UserHelper.currentUser.getId();
        LeaderboardPair newPair = new LeaderboardPair(userId, wpm);
        if(topFive == null) {
            currentQuote.setTopFiveTests(new ArrayList<>());
            topFive = currentQuote.getTopFiveTests();
        }
        boolean userInLeaderboards = false;
        boolean userBeatPR = false;
        for(int i = 0; i < topFive.size(); i++) {
            if(topFive.get(i).getUserId() == userId) userInLeaderboards = true;
        }

        int j = topFive.size() - 1;
        while(j >= 0 && wpm > topFive.get(j).getWpm()) {
            if(topFive.get(j).getUserId() == userId) {
                topFive.remove(j);
                userBeatPR = true;
            }
            j--;
        }

        if(userInLeaderboards && userBeatPR) insertToTopFive(j + 1, newPair);
        else if(userInLeaderboards) return;
        else insertToTopFive(j + 1, newPair);


        WriteAndReadHelper.updateQuotes(quotes);
    }

    private static void insertToTopFive(int index, LeaderboardPair pair) {
        List<LeaderboardPair> topFive = currentQuote.getTopFiveTests();
        if(topFive.isEmpty()) {
            topFive.add(pair);
            return;
        }
        topFive.add(pair);
        int j = topFive.size() - 1;
        while(j > index) {
            topFive.set(j, topFive.get(j - 1));
            j--;
        }
        topFive.set(j, pair);
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
            TypingTestHelper.setScrollPoints(TitleScreen.quoteTextArea);
            currentQuote = randQuote;
        }
        else {
            currentQuote = null;
            TitleScreen.quoteTextArea.setText("There are no quotes available, click on the create quote button to add quotes.");
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

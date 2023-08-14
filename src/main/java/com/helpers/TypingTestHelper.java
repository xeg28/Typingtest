package com.helpers;

import com.models.LeaderboardPair;
import com.models.Quote;
import com.models.User;
import com.views.TestResults;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TypingTestHelper {
    private static long startTime;

    public static List<Integer> scrollPoints = new ArrayList<>();


    public static void setTextInResultTextArea(double wpm) {
        StringBuilder text = new StringBuilder();
        text.append("You typed " + wpm + " wpm\n\n");

        if(UserHelper.currentUser == null) {
            text.append("Top 5 not available");
        }

        else if(QuoteHelper.currentQuote != null) {
            List<Double> topFive = UserHelper.currentUser.getTopFive().get(QuoteHelper.currentQuote.getId());
            text.append("Your Top 5 for \'"+QuoteHelper.currentQuote.getTitle()+"\'\n");
            for(int i = 0; i < topFive.size(); i++) {
                text.append((i+1) + ". " + topFive.get(i) + " wpm\n");
            }
        }
        else {
            text.append("Your Top 5 is not available for test quotes");
        }

        TestResults.userResults.setText(text.toString());
    }

    public static void setTextInLeaderboardsTextArea() {
        StringBuilder text = new StringBuilder();
        Quote currQuote = QuoteHelper.currentQuote;
        if(QuoteHelper.currentQuote != null) {
            List<LeaderboardPair> topFive = currQuote.getTopFiveTests();
            text.append("Leaderboards for \'" + currQuote.getTitle() + "\'\n");
            if(topFive == null || topFive.isEmpty()) {
                text.append("No tests found.");
            }
            else {
                for(int i = 0; i < 5; i++) {
                    if(i >= topFive.size()) break;

                    User user = UserHelper.getUserById(topFive.get(i).getUserId());

                    double wpm = topFive.get(i).getWpm();
                    text.append((i+1) + ". " + user.getName() + " at " + wpm + " wpm\n");
                }
            }
        }
        else {
            text.append("Leaderboards not available for test quotes");
        }
        TestResults.leaderboard.setText(text.toString());
    }

    public static void setScrollPoints(TextArea textArea) {
        scrollPoints = new ArrayList<>();
        double width = (textArea.getWidth() == 0.0) ? textArea.getPrefWidth() :  textArea.getWidth();

        String text = textArea.getText();
        String[] words = text.split(" ");
        Font font = textArea.getFont();
        int sum = 0;
        StringBuilder currentLine = new StringBuilder();
        for(int i = 0; i < words.length; i++) {
            StringBuilder potentialLine = new StringBuilder(currentLine);
            if (!currentLine.toString().isEmpty()) {
                potentialLine.append(" ");
            }
            potentialLine.append(words[i]);

            Text temp = new Text(potentialLine.toString());
            temp.setFont(Font.font("Helvatica", FontWeight.BOLD, 21.0));

            if(temp.getBoundsInLocal().getWidth() <= width) {
                currentLine = potentialLine;
            } else {
                sum += currentLine.length() - 1;
                scrollPoints.add(sum);
                currentLine = new StringBuilder(words[i]);
            }
        }
    }


    public static void highlightText(StringBuilder userTyped, TextArea quoteTextArea) {
        String quote = quoteTextArea.getText();
        String testQuote = "";

        for(int i = 0; i < userTyped.length(); i++) {
            if(scrollPoints.contains(i)) {
                scrollPoints.remove(scrollPoints.indexOf(i));
                quoteTextArea.setScrollTop(quoteTextArea.getScrollTop() + 30);
            }
            if(userTyped.length() < quote.length()) {
                testQuote += quote.charAt(i);
            }

            else {
                testQuote = quote;
            }
        }

        if(testQuote.equals(userTyped.toString())) {
            quoteTextArea.deselect();
            Object[] styles = quoteTextArea.getStyleClass().toArray();
            quoteTextArea.getStyleClass().clear();
            quoteTextArea.getStyleClass().add((String)styles[0]);
            quoteTextArea.getStyleClass().add((String)styles[1]);
            quoteTextArea.getStyleClass().add("correctHighlight");
            quoteTextArea.selectRange(0, userTyped.length());
        }
        else {
            quoteTextArea.deselect();
            quoteTextArea.getStyleClass().remove("correctHighlight");
            quoteTextArea.getStyleClass().add("errorHighlight");
            quoteTextArea.selectRange(numOfChars(userTyped, quoteTextArea), userTyped.length());

        }
    }

    public static int numOfChars(StringBuilder userTyped, TextArea quoteTextArea) {
        int numOfChars = 0;
        String quote = quoteTextArea.getText();
        for(int i = 0; i < userTyped.length(); i++) {
            if(userTyped.charAt(i) != quote.charAt(i)) {
                break;
            }
            else if(userTyped.charAt(i) == quote.charAt(i))
                numOfChars++;
        }
        return numOfChars;
    }

    public static double getWPM(int textLength) {
        DecimalFormat format1 = new DecimalFormat("0.#");
        double userWPM = Double.parseDouble(format1.format(((textLength/5.0)/elapsedTime())*60));
        return userWPM;
    }

    public static void startTime() {
        startTime = System.nanoTime();
    }

    private static double elapsedTime() {
        long now = System.nanoTime();
        return (now - startTime) / 1000000000.0;
    }
}

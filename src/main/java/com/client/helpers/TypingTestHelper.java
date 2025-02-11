package com.client.helpers;

import com.client.models.*;
import com.client.views.TestResults;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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


    public static void setTextInResultTextArea() {
        TableView<Test> userResults = TestResults.userResults;
        if(!userResults.getItems().isEmpty()) userResults.getItems().clear();

        double wpm = UserHelper.lastWPM;
        if(UserHelper.currentUser == null) {
            TestResults.userTopTestLabel.setText("Your quote tests are not available");
            return;
        }

        List<Test> tests = UserHelper.userTestsForCurrentQuote;
        if(tests == null) return;

        if(QuoteHelper.currentQuote != null && tests != null) {
            for(int i = 0; i < tests.size(); i++) {
                userResults.getItems().add(tests.get(i));
            }
        }
        TestResults.wpmLabel.setText("You typed " + wpm + " wpm");
        TestResults.userTopTestLabel.setText("Your top tests for '" + UserHelper.currentUser.getName() +"'");
    }

    public static void setTextInLeaderboardsTextArea() {
        Quote currQuote = QuoteHelper.currentQuote;
        TableView<Test> leaderTable = TestResults.leaderboard;
        List<Test> leaderBoard = QuoteHelper.leaderBoard;
        if(!leaderTable.getItems().isEmpty()) leaderTable.getItems().clear();

        if(QuoteHelper.currentQuote != null) {
            if(leaderBoard == null)
                TestResults.leaderboardLabel.setText("Leaderbord for '" + currQuote.getTitle() + "'");
            else if(leaderBoard.isEmpty()) {
                TestResults.leaderboardLabel.setText("Leaderbord for '" + currQuote.getTitle() + "'");
                leaderTable.setPlaceholder(new Label("No leaderboard for current quote"));
            }
            else {
                TestResults.leaderboardLabel.setText("Leaderbord for '" + currQuote.getTitle() + "'");
                for(Test test : leaderBoard) {
                    leaderTable.getItems().add(test);
                }
            }
        }
        else {
            TestResults.leaderboardLabel.setText("Leaderboards not available");
        }

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

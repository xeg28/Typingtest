package com.controllers;

import com.helpers.QuoteHelper;
import com.helpers.TypingTestHelper;
import com.helpers.UserHelper;
import com.typingtest.Main;
import com.views.ListQuotes;
import com.views.Header;
import com.views.TestResults;
import com.views.TitleScreen;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;


public class TitleScreenController {

    private static StringBuilder userTyped = new StringBuilder();
    private static PauseTransition wpmPause = new PauseTransition(Duration.millis(100));
    private static PauseTransition highlightPause = new PauseTransition(Duration.millis(1));

    private static TextArea quoteTextArea = TitleScreen.quoteTextArea;
    private static Label wpmLabel = TitleScreen.wpmLabel;
    private static Button quoteListBtn =  TitleScreen.quoteListBtn;
    private static TextField testQuoteTextField = TitleScreen.testQuote;
    private static Button testQuoteBtn = TitleScreen.testQuoteBtn;
    private static boolean testIsOngoing = false;

    private static boolean backspacePressed = false;

    private static boolean ctrBksPressed = false;
    private static boolean tabPressed = false;

    private static boolean enterPressed = false;


    public static void setHandlersForTitleScreen() {
        quoteTextAreaHandler();
        testQuoteHandler();
        quoteListBtnHandler();
        showShortCutsHandler();
    }

    public static void testQuoteHandler() {
        testQuoteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(testIsOngoing)
                    stopTest();
                String newQuote = testQuoteTextField.getText();
                if(newQuote.length() == 0) {
                    testQuoteTextField.setPromptText("You need to add text here.");
                }
                else {
                    QuoteHelper.currentQuote = null;
                    quoteTextArea.setText(newQuote);
                    TypingTestHelper.setScrollPoints(quoteTextArea);
                    System.out.println(TypingTestHelper.scrollPoints);
                    testQuoteTextField.clear();
                }

            }
        });
    }

    public static void quoteListBtnHandler() {
        quoteListBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(testIsOngoing) {
                    stopTest();
                }
                QuoteHelper.addQuotesToList();
                ListQuotes.setLastRoot(TitleScreen.getBorder());
                ListQuotes.getBorder().setTop(Header.getHeader());
                Main.primaryScene.setRoot(ListQuotes.getBorder());
            }
        });
    }

    public static void showShortCutsHandler() {
        Button btn = TitleScreen.shortcutsBtn;
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(btn.getText().equals("Show Shortcuts")) {
                    TitleScreen.showShortcuts();
                    btn.setText("Hide Shortcuts");
                } else {
                    TitleScreen.hideShortcuts();
                    btn.setText("Show Shortcuts");
                }
            }
        });
    }

    public static void quoteTextAreaHandler() {

        KeyCombination nextTestCombo = new KeyCodeCombination( KeyCode.K, KeyCodeCombination.ALT_DOWN, KeyCodeCombination.CONTROL_DOWN);
        KeyCombination restartTestCombo = new KeyCodeCombination( KeyCode.L, KeyCodeCombination.ALT_DOWN, KeyCodeCombination.CONTROL_DOWN);

        quoteTextArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.BACK_SPACE) {
                event.consume();
                ctrBksPressed = true;
                deleteLastWord();
            }
            else if(event.getCode() == KeyCode.TAB) {
                tabPressed = true;
            }
            else if(event.getCode() == KeyCode.BACK_SPACE) {
                event.consume();
                backspacePressed = true;
            }
            else if(event.getCode() == KeyCode.ENTER) {
                event.consume();
                enterPressed = true;
            }
        });

        quoteTextArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(nextTestCombo.match(keyEvent)) {
                    stopTest();
                    QuoteHelper.setRandomQuote();
                }
                if(restartTestCombo.match(keyEvent)) {
                    stopTest();
                    quoteTextArea.setScrollTop(0.0);
                }
            }
        });


        quoteTextArea.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(!testIsOngoing && !tabPressed) {
                    testIsOngoing = true;
                    updateTextHighlight();
                    TypingTestHelper.startTime();
                    updateWPM();
                }
                if(enterPressed) {
                    userTyped.append((char)10);
                    enterPressed = false;
                    quoteTextArea.setScrollTop(quoteTextArea.getScrollTop() + 30);
//                    double contentHeight = quoteTextArea.lookup(".content").getBoundsInLocal().getHeight();
//                    double viewportHeight = quoteTextArea.getHeight();
//                    System.out.println(Math.max(0, contentHeight - viewportHeight) / 30);
                }
                else if(ctrBksPressed) {
                    ctrBksPressed = false;
                }
                else if(tabPressed) {
                    tabPressed = false;
                }
                else if(backspacePressed) {
                    if(!userTyped.isEmpty()) {
                        userTyped.deleteCharAt(userTyped.length() - 1);
                        userTyped.trimToSize();
                    }
                    backspacePressed = false;
                }
                else {
                    userTyped.append(keyEvent.getCharacter());
                }
            }
        });
    }

    private static void deleteLastWord() {
        boolean foundLetter = false;
        for(int i = userTyped.length() - 1; i >= 0; i--) {
            if(userTyped.charAt(i) != ' ') {
                foundLetter = true;
            }
            if(foundLetter && userTyped.charAt(i) == ' ') {
                userTyped.delete(i+1, userTyped.length());
                userTyped.trimToSize();
                return;
            }
        }
        userTyped.delete(0, userTyped.length());
        userTyped.trimToSize();
    }

    private static void updateTextHighlight() {
        highlightPause.setOnFinished(event ->{
            TypingTestHelper.highlightText(userTyped, quoteTextArea);
            if(userTyped.toString().equals(quoteTextArea.getText())) {
                double finalWPM = TypingTestHelper.getWPM(userTyped.length());
                highlightPause.stop();
                wpmLabel.setText(finalWPM+ " wpm");
                resultSceneChange(finalWPM);
                stopTest();
            }
            else {
                highlightPause.play();
            }
        });
        highlightPause.play();
    }

    private static void updateWPM() {
        wpmPause.setOnFinished(event ->{
            wpmLabel.setText(TypingTestHelper.getWPM(TypingTestHelper.numOfChars(userTyped, quoteTextArea))+ " wpm");
            if(!userTyped.toString().equals(quoteTextArea.getText())) {
                wpmPause.play();
            }
        });
        wpmPause.play();

    }

    public static void stopTest() {
        if(wpmPause.getStatus() != Animation.Status.STOPPED) {
            wpmPause.stop();
        }
        if(highlightPause.getStatus() != Animation.Status.STOPPED) {
            highlightPause.stop();
        }
        testIsOngoing = false;
        wpmLabel.setText("0.0 wpm");
        quoteTextArea.deselect();
        userTyped = new StringBuilder();
    }

    private static void resultSceneChange(double wpm) {
        if(UserHelper.currentUser != null && QuoteHelper.currentQuote != null) {
            UserHelper.updateUserStats(wpm);
            QuoteHelper.updateTopFive(wpm);
        }

        TestResults.border.setTop(Header.getHeader());
        TypingTestHelper.setTextInResultTextArea(wpm);
        TypingTestHelper.setTextInLeaderboardsTextArea();
        Main.primaryScene.setRoot(TestResults.getBorder());
        TestResults.setHeightForTextArea(quoteTextArea.getHeight());
    }

}

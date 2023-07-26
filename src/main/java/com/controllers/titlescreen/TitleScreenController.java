package com.controllers.titlescreen;

import com.helpers.TypingTestHelper;
import com.typingtest.Main;
import com.views.template.Header;
import com.views.testresults.TestResults;
import com.views.titlescreen.TitleScreen;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private static Button delBtn =  TitleScreen.deleteQuoteBtn;
    private static TextField testQuoteTextField = TitleScreen.testQuote;
    private static Button addQuoteBtn;
    private static Button testQuoteBtn = TitleScreen.testQuoteBtn;
    private static boolean testIsOngoing = false;

    private static boolean backspacePressed = false;

    private static boolean ctrBksPressed = false;
    private static boolean tabPressed = false;



    public static void setHandlersForTitleScreen() {
        quoteTextAreaHandler();
        newQuoteHandler();
    }

    public static void newQuoteHandler() {
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
                    quoteTextArea.setText(newQuote);
                }

            }
        });
    }

    public static void quoteTextAreaHandler() {

        KeyCombination nextTestCombo = new KeyCodeCombination(KeyCode.BACK_SPACE, KeyCodeCombination.CONTROL_DOWN);

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
                if(ctrBksPressed) {
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

                highlightPause.stop();
                return;
            }
//            else if(wpmPause.getStatus() != Animation.Status.STOPPED){
//                highlightPause.play();
//            }
//            else {
//                quoteTextArea.deselect();
//                quoteTextArea.setStyle("-fx-text-fill: #000000");
//                highlightPause.stop();
//            }
            highlightPause.play();
        });
        highlightPause.play();
    }

    private static void updateWPM() {
        wpmPause.setOnFinished(event ->{
            wpmLabel.setText(TypingTestHelper.getWPM(userTyped.length())+ " wpm");
            if(userTyped.toString().equals(quoteTextArea.getText())) {
//                double finalWPM = getWPM();
//                endOfTest(finalWPM, quote); quote was a parameter
                stopTest();
                resultSceneChange();
            }
            else {
                wpmPause.play();
            }
        });
        wpmPause.play();

    }

    private static void stopTest() {
        if(wpmPause.getStatus() != Animation.Status.STOPPED) {
            wpmPause.stop();
        }
        if(highlightPause.getStatus() != Animation.Status.STOPPED) {
            highlightPause.stop();
        }
        testIsOngoing = false;
        quoteTextArea.deselect();
        quoteTextArea.setStyle("-fx-text-fill: #000000");
        userTyped = new StringBuilder();
    }

    private static void resultSceneChange() {
        TestResults.border.setTop(Header.getHeader());
        TestResults.userResults.setText("You typed " + wpmLabel.getText());
        Main.primaryStage.setScene(TestResults.getScene());
    }


}

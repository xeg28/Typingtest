package com.views;

import com.helpers.QuoteHelper;
import com.helpers.TypingTestHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TitleScreen {
    private static BorderPane border;
    private static VBox body = new VBox();
    private static VBox top = new VBox();
    private static HBox innerTop = new HBox();
    private static VBox bottom = new VBox();
    private static VBox shortcuts = new VBox();

    public static TextArea quoteTextArea = new TextArea();
    public static Label wpmLabel = new Label("0.0 wpm");
    public static Button quoteListBtn = new Button("Select Quote");
    public static TextField testQuote = new TextField();
    public static Button testQuoteBtn = new Button("Test Quote");

    public static Button shortcutsBtn = new Button("Show Shortcuts");

    public static void load() {
        border = new BorderPane();
        border.setCenter(body);


        body.setPadding(new Insets(30.0, 30.0, 30.0, 30.0));
        body.setSpacing(20);
        body.getChildren().addAll(top, bottom);

        innerTop.setAlignment(Pos.CENTER_LEFT);
        innerTop.setSpacing(50);
        setQuoteTextArea();
        setWPMLabel();

        HBox btnContainer = new HBox();
        btnContainer.setSpacing(15);
        btnContainer.getChildren().addAll(quoteListBtn, shortcutsBtn);
        setShortcutLabels();

        top.getChildren().addAll(innerTop, btnContainer);
        top.setSpacing(10);
        top.setPrefHeight(412);

        body.heightProperty().addListener((observable, oldHeight, newHeight) -> {
            if(top.getHeight() != 0.0) {
                double topHeight = newHeight.doubleValue() - 60 - bottom.getHeight();
                top.setPrefHeight(topHeight);
                double quoteTextAreaHeight = topHeight - 10 - quoteListBtn.getHeight();
                quoteTextArea.setPrefHeight(quoteTextAreaHeight);
            }
        });


        bottom.setAlignment(Pos.CENTER_LEFT);
        bottom.setSpacing(10);
        setAddQuoteTextField();
        bottom.getChildren().add(testQuoteBtn);
    }

    private static void setQuoteTextArea() {
        quoteTextArea.setText("The quick brown fox jumped over the lazy dog.");
        quoteTextArea.setWrapText(true);
        quoteTextArea.setEditable(false);
        quoteTextArea.setFont(Font.font("Helvatica", FontWeight.BOLD, 20.0));
        quoteTextArea.setPrefWidth(900);
        quoteTextArea.setPrefHeight(377);

        innerTop.widthProperty().addListener((observable, oldWidth, newWidth) -> {
            if(quoteTextArea.getWidth() != 0.0) {
                double desiredWidth = newWidth.doubleValue() - 60 - wpmLabel.getWidth();
                quoteTextArea.setPrefWidth(desiredWidth);
                TypingTestHelper.setScrollPoints(quoteTextArea);
            }

        });
        innerTop.getChildren().add(quoteTextArea);
        QuoteHelper.setRandomQuote();
    }


    private static void setShortcutLabels() {
        Label nextTest = new Label("Next Test: CTR+ALT+K");
        nextTest.setFont(Font.font("Helvatica", FontWeight.BOLD, 20));
        Label restartTest = new Label("Restart Test: CTR+ALT+L");
        restartTest.setFont(Font.font("Helvatica", FontWeight.BOLD, 20));

        shortcuts.setPadding(new Insets(0,30,10,30));
        shortcuts.setSpacing(15);
        shortcuts.setAlignment(Pos.CENTER_LEFT);
        shortcuts.getChildren().addAll(nextTest, restartTest);
    }

    private static void setWPMLabel () {
        wpmLabel.setAlignment(Pos.CENTER);
        wpmLabel.setFont(Font.font("Helvatica", FontWeight.BOLD, 35));
        wpmLabel.setPrefWidth(182);

        innerTop.getChildren().add(wpmLabel);
    }

    private static void setAddQuoteTextField() {
        testQuote.setPromptText("Enter a quote.");
        testQuote.setPrefWidth(900);
        bottom.getChildren().add(testQuote);
    }

    public static void showShortcuts() {
        border.setBottom(shortcuts);
    }

    public static void hideShortcuts() {
        border.getChildren().remove(shortcuts);
    }

    public static BorderPane getBorder() {
        return border;
    }

}

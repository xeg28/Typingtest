package com.views.addquote;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AddQuote {
    private static Scene scene;
    public static BorderPane border;
    private static VBox body = new VBox();
    public static TextArea quoteTitle = new TextArea();
    public static TextArea quote = new TextArea();

    public static Button backButton = new Button("Back");
    public static Button addQuoteBtn = new Button("Create Quote");

    public AddQuote() {
        border = new BorderPane();
        border.setCenter(body);
        scene = new Scene(border, 1200,600);

        body.setPadding(new Insets(30,30,30,30));
        body.setSpacing(20);
        body.setAlignment(Pos.TOP_CENTER);

        Label titlelabel = new Label("Quote Title");
        titlelabel.setFont(Font.font("Helvatica", FontWeight.BOLD, 20));
        quoteTitle.setFont(Font.font("Helvatica", FontWeight.BOLD, 18));

        Label quoteTextLabel = new Label("Quote Text");
        quoteTextLabel.setFont(Font.font("Helvatica", FontWeight.BOLD, 20));
        quote.setFont(Font.font("Helvatica", FontWeight.BOLD, 16));

        HBox buttonContainer = new HBox();
        buttonContainer.setSpacing(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(backButton, addQuoteBtn);

        setHeightProperty(titlelabel, quoteTextLabel);
        body.getChildren().addAll(titlelabel, quoteTitle, quoteTextLabel, quote, buttonContainer);
    }

    private static void setHeightProperty(Label titleLabel, Label textLabel) {
        body.heightProperty().addListener((observable, oldHeight, newHeight) -> {
            double quoteTitleHeight = newHeight.doubleValue() * 0.08 - 140 - textLabel.getHeight() - titleLabel.getHeight();
            double quoteTextHeight = newHeight.doubleValue() - quoteTitleHeight - 140 - textLabel.getHeight() - titleLabel.getHeight();
            quoteTitle.setPrefHeight(quoteTitleHeight);
            quote.setPrefHeight(quoteTextHeight);
        });
    }

    public static Scene getScene() {
        return scene;
    }

}

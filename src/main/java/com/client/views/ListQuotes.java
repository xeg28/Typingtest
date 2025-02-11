package com.client.views;

import com.client.helpers.QuoteHelper;
import com.client.helpers.UserHelper;
import com.client.models.Quote;
import com.client.models.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class ListQuotes {
    private static BorderPane lastRoot;
    private static BorderPane border;
    private static ListView<String> quoteList = new ListView<>();
    private static VBox body = new VBox();
    private static HBox buttonContainer = new HBox();

    public static Button backBtn = new Button("Back");
    public static Button selectQuoteBtn = new Button("Select");
    public static Button deleteQuoteBtn = new Button("Delete");

    private static int previousQuoteHash;

    public static void load() {
        border = new BorderPane();
        border.setCenter(body);

        body.getChildren().addAll(quoteList, buttonContainer);
        body.setSpacing(20);
        body.setPadding(new Insets(30, 30, 30, 30));
        body.heightProperty().addListener((observable, oldHeight, newHeight) -> {
            double quoteListHeight = newHeight.doubleValue() - 20 - buttonContainer.getHeight();
            quoteList.setPrefHeight(quoteListHeight);
        });


        setFontForQuoteList();

        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(15);
        buttonContainer.getChildren().addAll(backBtn, deleteQuoteBtn, selectQuoteBtn);
        createTimeline();
    }

    public static void createTimeline() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(50), e -> {
                    List<Quote> currentQuotes =  QuoteHelper.getQuotes();
                    if(currentQuotes == null || currentQuotes.hashCode() == previousQuoteHash) return;
                    QuoteHelper.addQuotesToList();
                    previousQuoteHash = currentQuotes.hashCode();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private static void setFontForQuoteList() {
        quoteList.setCellFactory(l->new ListCell<String>() {
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if(empty || item == null) {
                    setText(null);
                }
                else {
                    setText(item.toString());
                    setFont(Font.font("Helvatica", FontWeight.BOLD, 16));
                }
            }
        });
    }


    public static void setLastRoot(BorderPane prevRoot) {
        if(prevRoot != border) {
            lastRoot = prevRoot;
        }
    }

    public static BorderPane getLastRoot() {
        return lastRoot;
    }
    public static BorderPane getBorder() {
        return border;
    }


    public static ListView<String> getQuoteList() {
        return quoteList;
    }
}

package com.client.controllers;

import com.client.typingtest.Main;
import com.client.views.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;

public class HeaderController {
    public static void setHandlersForHeader() {
        newQuoteHandler();
        createUserHandler();
        loadUserHandler();
    }

    private static void newQuoteHandler() {
        Header.newQuote.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TitleScreenController.stopTest();
                BorderPane prevRoot = (BorderPane)Main.primaryScene.getRoot();

                AddQuote.setLastRoot(prevRoot);
                AddQuote.border.setTop(Header.getHeader());
                Main.primaryScene.setRoot(AddQuote.getBorder());
            }
        });
    }

    private static void createUserHandler() {
        Header.createUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!Register.getStage().isFocused()) {
                    Register.getStage().setIconified(false);
                    Register.getStage().toFront();
                }
                Register.getStage().show();
                TitleScreenController.stopTest();
            }
        });
    }

    private static void loadUserHandler() {
        Header.loadUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!Login.getStage().isFocused()) {
                    Login.getStage().setIconified(false);
                    Login.getStage().toFront();
                }

                Login.getStage().show();
                TitleScreenController.stopTest();
            }
        });
    }
}

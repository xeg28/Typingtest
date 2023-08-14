package com.controllers;

import com.helpers.UserHelper;
import com.typingtest.Main;
import com.views.*;
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
                if(!CreateUser.getStage().isFocused()) {
                    CreateUser.getStage().toFront();
                }
                CreateUser.getStage().show();
                TitleScreenController.stopTest();
            }
        });
    }

    private static void loadUserHandler() {
        Header.loadUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!LoadUser.getStage().isFocused()) {
                    LoadUser.getStage().toFront();
                    UserHelper.addUsersToList();
                }

                UserHelper.addUsersToList();
                LoadUser.getStage().show();
                TitleScreenController.stopTest();
            }
        });
    }

}

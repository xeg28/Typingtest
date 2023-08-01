package com.controllers;

import com.controllers.TitleScreenController;
import com.helpers.UserHelper;
import com.models.User;
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
                CreateUser.getStage().show();
            }
        });
    }

    private static void loadUserHandler() {
        Header.loadUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UserHelper.addUsersToList();
                LoadUser.getStage().show();
            }
        });
    }

}

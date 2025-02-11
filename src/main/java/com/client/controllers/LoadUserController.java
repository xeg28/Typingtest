package com.client.controllers;

import com.client.helpers.AlertHelper;
import com.client.helpers.UserHelper;
import com.client.models.User;
import com.client.typingtest.Main;
import com.client.views.Header;
import com.client.views.Login;
import com.client.views.TestResults;
import com.client.views.TitleScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class LoadUserController {

    public static void setHandlersForLoadUsers() {
        handlerForSelectButton();
    }

    public static void handlerForSelectButton() {
        Login.selectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(ServerController.isConnected()) {
                    handleForOnline();
                }
                else {
                    handleSelectForOffline();
                }
            }
        });
    }

    private static void handleForOnline() {
        String username = Login.nameField.getText();
        String password = Login.passwordField.getText();
        RequestController.sendUserRequest(username, password);
        Login.passwordField.setText("");
    }

    private static void handleSelectForOffline() {
        int itemIndex = Login.users.getSelectionModel().getSelectedIndex();
        if(!UserHelper.users.isEmpty() && itemIndex >= 0 && itemIndex < UserHelper.users.size()) {
            if(Main.primaryScene.getRoot() == TestResults.getBorder()) {
                TitleScreen.getBorder().setTop(Header.getHeader());
                Main.primaryScene.setRoot(TitleScreen.getBorder());
            }
            User userSelected = UserHelper.getUser(itemIndex);
            UserHelper.currentUser = userSelected;
        }
    }
}

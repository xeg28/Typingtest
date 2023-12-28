package com.controllers;

import com.helpers.AlertHelper;
import com.helpers.UserHelper;
import com.models.User;
import com.typingtest.Main;
import com.views.Header;
import com.views.LoadUser;
import com.views.TestResults;
import com.views.TitleScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class LoadUserController {
    public static void setHandlersForLoadUsers() {
        handlerForDeleteButton();
        handlerForSelectButton();
    }

    public static void handlerForSelectButton() {
        LoadUser.selectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int itemIndex = LoadUser.users.getSelectionModel().getSelectedIndex();
                if(!UserHelper.users.isEmpty() && itemIndex >= 0 && itemIndex < UserHelper.users.size()) {
                    if(Main.primaryScene.getRoot() == TestResults.getBorder()) {
                        TitleScreen.getBorder().setTop(Header.getHeader());
                        Main.primaryScene.setRoot(TitleScreen.getBorder());
                    }
                    User userSelected = UserHelper.getUser(itemIndex);
                    UserHelper.currentUser = userSelected;

                    Header.usernameLabel.setText(userSelected.getName());
                    Header.highWPM.setText("Highest WPM: " + userSelected.getBestWPM());
                    Header.avgWPMLabel.setText("Average WPM: " + userSelected.getAverageWPM());
                    Header.userTooltip.setText("Highest WPM: " + userSelected.getBestWPM() +
                                                "\nAverage WPM: " + userSelected.getAverageWPM());
                    LoadUser.getStage().close();
                }
            }
        });
    }

    public static void handlerForDeleteButton() {
        LoadUser.deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int itemIndex = LoadUser.users.getSelectionModel().getSelectedIndex();

                User userSelected = UserHelper.getUser(itemIndex);
                boolean userConfirmation = AlertHelper.createAlertConformation("Delete User Confirmation",
                        "Are you sure you want to delete '" + userSelected.getName()+ "'?");
                if(!userConfirmation) return;

                if(Main.primaryScene.getRoot() == TestResults.getBorder()) {
                    TitleScreen.getBorder().setTop(Header.getHeader());
                    Main.primaryScene.setRoot(TitleScreen.getBorder());
                }
                UserHelper.deleteUser(userSelected);
                UserHelper.addUsersToList();
            }
        });
    }
}

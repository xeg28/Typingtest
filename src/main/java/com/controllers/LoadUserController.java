package com.controllers;

import com.helpers.UserHelper;
import com.models.User;
import com.views.Header;
import com.views.LoadUser;
import com.views.TitleScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class LoadUserController {
    public static void setHandlersForLoadUsers() {
        handlerForSelectButton();
    }

    public static void handlerForSelectButton() {
        LoadUser.selectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int itemIndex = LoadUser.users.getSelectionModel().getSelectedIndex();
                User userSelected = UserHelper.getUser(itemIndex);
                UserHelper.currentUser = userSelected;

                Header.usernameLabel.setText(userSelected.getName());
                Header.highWPM.setText("Highest WPM: " + userSelected.getBestWPM());
                Header.avgWPMLabel.setText("Average WPM: " + userSelected.getAverageWPM());

                LoadUser.getStage().close();
            }
        });
    }

}

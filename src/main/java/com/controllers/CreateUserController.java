package com.controllers;

import com.helpers.UserHelper;
import com.helpers.WriteAndReadHelper;
import com.models.User;
import com.views.CreateUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CreateUserController {

    public static void setHandlersForCreateUser() {
        createUserHandler();
    }

    public static void createUserHandler() {
        CreateUser.button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = CreateUser.nameField.getText();
                if(!name.isEmpty()) {
                    User user = new User(name);
                    System.out.println("Id seed: " + User.getIdSeed());
                    System.out.println("user Id: " + user.getId());
                    WriteAndReadHelper.writeUser(user);
                    UserHelper.updateUsers();
                    CreateUser.nameField.clear();
                }
            }
        });
    }
}

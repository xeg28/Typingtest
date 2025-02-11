package com.client.controllers;

import com.client.helpers.AlertHelper;
import com.client.views.Register;
import com.client.views.Login;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CreateUserController {

    public static void setHandlersForCreateUser() {
        createUserHandler();
    }

    public static void createUserHandler() {
        Register.button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = Register.nameField.getText();
                String password = Register.passwordField.getText();
                if(name.length() >= 2 && password.length() >= 5) {
                    RequestController.sendUserInsert(name, password);
                    Register.nameField.clear();
                    Register.passwordField.clear();
                    boolean response = AlertHelper.createLoginPrompt();
                    if(response) {
                        if(!Login.getStage().isFocused()) {
                            Login.getStage().setIconified(false);
                            Login.getStage().toFront();
                        }
                        Login.getStage().show();
                    }
                    Register.getStage().close();
                }
                else if(name.length() < 2) {
//                    AlertHelper.createAlert("Username length", "Username must be 2 or more characters");
                    AlertHelper.errors.add("Username length|Username must be 2 or more characters");
                }
                else {
//                    AlertHelper.createAlert("Password length", "Password must be 5 or more characters");
                    AlertHelper.errors.add("Password Length|Password Must be 5 or more characters");
                }
            }
        });
    }
}

package com.client.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Register {
    private static Stage stage = new Stage();
    private static VBox container = new VBox();

    private static Scene scene;

    public static Label label = new Label("Create a user");
    public static TextField nameField = new TextField();
    public static PasswordField passwordField = new PasswordField();
    public static Button button = new Button("Register");

    public static void load() {
        container.getChildren().addAll(label, nameField, passwordField, button);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        label.setFont(new Font("Helvatica", 16));
        nameField.setMaxWidth(250);
        nameField.setPromptText("Enter your username");
        passwordField.setMaxWidth(250);
        passwordField.setPromptText("Password");
        scene = new Scene(container, 300, 150);
        stage.setScene(scene);

        stage.setTitle("Create User");
        stage.centerOnScreen();
    }

    public static Stage getStage() {
        return stage;
    }

}

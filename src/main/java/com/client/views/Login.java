package com.client.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Login {
    private static Stage stage = new Stage();

    private static VBox body = new VBox();
    private static Scene scene;
    private static HBox btnContainer = new HBox();
    public static Label label = new Label("Load a user");
    public static ComboBox<String> users = new ComboBox<>();
    public static TextField nameField = new TextField();
    public static PasswordField passwordField = new PasswordField();

    public static Button selectBtn = new Button("Login");

    public static void load() {
        body.getChildren().addAll(label, nameField,passwordField, btnContainer);
        body.setAlignment(Pos.CENTER);
        body.setSpacing(10);

        label.setFont(new Font("Helvatica", 16));

        btnContainer.getChildren().add(selectBtn);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.setSpacing(10);

        users.setMaxWidth(250);
        users.setPromptText("Select a user");

        nameField.setMaxWidth(250);
        nameField.setPromptText("Enter your username");
        passwordField.setMaxWidth(250);
        passwordField.setPromptText("Password");

        scene = new Scene(body, 300, 150);

        stage.setScene(scene);
        stage.setTitle("Load User");
        stage.centerOnScreen();
    }

    public static Stage getStage() {
        return stage;
    }

}

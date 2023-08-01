package com.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoadUser {
    private static Stage stage = new Stage();

    private static VBox body = new VBox();
    private static Scene scene;
    private static HBox btnContainer = new HBox();
    public static Label label = new Label("Load a user");
    public static ComboBox<String> users = new ComboBox<>();

    public static Button selectBtn = new Button("Select");
    public static Button deleteBtn = new Button("Delete");

    public static void load() {
        body.getChildren().addAll(label, users, btnContainer);
        body.setAlignment(Pos.CENTER);
        body.setSpacing(10);

        label.setFont(new Font("Helvatica", 16));

        btnContainer.getChildren().addAll(deleteBtn, selectBtn);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.setSpacing(10);

        users.setMaxWidth(250);
        users.setPromptText("Select a user");


        scene = new Scene(body, 300, 125);

        stage.setScene(scene);
        stage.setTitle("Load User");
        stage.centerOnScreen();
    }

    public static Stage getStage() {
        return stage;
    }

}

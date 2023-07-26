package com.controllers.template;

import com.typingtest.Main;
import com.views.addquote.AddQuote;
import com.views.template.Header;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class HeaderController {
    private static Scene lastScene;
    public static void setHandlersForHeader() {
        Header.newQuote.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setLastScene();
                AddQuote.border.setTop(Header.getHeader());
                Main.primaryStage.setScene(AddQuote.getScene());
            }
        });
    }

    private static void setLastScene() {
        lastScene = Main.primaryStage.getScene();
    }

    public static Scene getLastScene() {
        return lastScene;
    }

}

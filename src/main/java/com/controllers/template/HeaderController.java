package com.controllers.template;

import com.controllers.titlescreen.TitleScreenController;
import com.typingtest.Main;
import com.views.addquote.AddQuote;
import com.views.template.Header;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class HeaderController {
    public static void setHandlersForHeader() {
        Header.newQuote.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TitleScreenController.stopTest();
                Scene prevScene = Main.primaryStage.getScene();
                Scene targetScene = AddQuote.getScene();
                double targetWidth = prevScene.getWidth();
                double targetHeight = prevScene.getHeight();


                AddQuote.setLastScene(prevScene);
                AddQuote.border.setTop(Header.getHeader());
                Main.primaryStage.setScene(targetScene);
            }
        });
    }
}

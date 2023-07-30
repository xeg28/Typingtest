package com.controllers.template;

import com.controllers.titlescreen.TitleScreenController;
import com.typingtest.Main;
import com.views.addquote.AddQuote;
import com.views.template.Header;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class HeaderController {
    public static void setHandlersForHeader() {
        Header.newQuote.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TitleScreenController.stopTest();
                BorderPane prevRoot = (BorderPane)Main.primaryScene.getRoot();

                AddQuote.setLastRoot(prevRoot);
                AddQuote.border.setTop(Header.getHeader());
                Main.primaryScene.setRoot(AddQuote.getBorder());
            }
        });
    }
}

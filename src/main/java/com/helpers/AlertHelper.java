package com.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.concurrent.atomic.AtomicReference;

public class AlertHelper {
    public static void createAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean createAlertConformation(String title, String message) {
        AtomicReference<Boolean> success = new AtomicReference<>();
        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No");
        Alert alert = new Alert(AlertType.CONFIRMATION, message, yesBtn, noBtn);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait().ifPresent(response -> {
          if(response == yesBtn) {
              success.set(true);
          }
          else {
              success.set(false);
          }
       });
        return success.get();
    }
}

package com.client.helpers;

import com.client.controllers.ServerController;
import com.client.models.Quote;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

public class AlertHelper {
    public static Queue<String> errors = new LinkedList<>();
    private static Alert errorAlert = new Alert(AlertType.ERROR);

    public static void loadAlertTimeline() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(50), e -> {
                    if(!errorAlert.isShowing() && !errors.isEmpty()) {
                        String[] error = errors.remove().split("\\|");
                        errorAlert.setTitle(error[0]);
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText(error[1]);
                        Platform.runLater(() -> {
                            errorAlert.show();
                        });
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static boolean createLoginPrompt() {
        AtomicReference<Boolean> success = new AtomicReference<>();
        ButtonType loginBtn = new ButtonType("Login");
        ButtonType noBtn = new ButtonType("Not now");
        Alert loginPrompt = new Alert(AlertType.CONFIRMATION, "Do you want to login?", loginBtn, noBtn);
        loginPrompt.setTitle("");
        loginPrompt.setHeaderText(null);
        loginPrompt.showAndWait().ifPresent(response -> {
            if(response == loginBtn) {
                success.set(true);
            } else {
                success.set(false);
            }
        });
        return success.get();
    }

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

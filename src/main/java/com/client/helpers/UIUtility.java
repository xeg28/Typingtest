package com.client.helpers;

import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;


public class UIUtility {

    public static StackPane getSpinner(double height) {
        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setProgress(-1); // Set to indeterminate

        StackPane root = new StackPane(spinner);
        root.setPrefHeight(height);
        return root;
    }

    public static ProgressIndicator getSpinner() {
        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setProgress(-1); // Set to indeterminate
        return spinner;
    }
}

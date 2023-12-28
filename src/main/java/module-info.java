module com.example.typingtest {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens com.typingtest to javafx.fxml;
    exports com.typingtest;
    exports com.models;
}
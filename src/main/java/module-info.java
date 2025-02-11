module com.example.typingtest {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    opens com.client.typingtest to javafx.fxml;
    exports com.client.typingtest;
    exports com.client.models;
}
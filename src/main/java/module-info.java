module com.example.personalfinancedb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.personalfinancedb to javafx.fxml;
    exports com.example.personalfinancedb;
}
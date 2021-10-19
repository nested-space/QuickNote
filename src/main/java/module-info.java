module com.edenrump.quicknote {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.edenrump to javafx.fxml;
    opens com.edenrump.controllers to javafx.fxml;
    opens com.edenrump.models.task to com.google.gson;

    exports com.edenrump.ui.controls to javafx.fxml;
    exports com.edenrump;
}
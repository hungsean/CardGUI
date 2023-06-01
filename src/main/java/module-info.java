module com.example.cardgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.cardgui to javafx.fxml;
    exports com.example.cardgui;
}
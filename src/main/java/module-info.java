module com.example.simulinkviewer {
    requires javafx.controls;
    requires javafx.fxml;
    requires engine;
    requires java.xml;


    opens com.example.simulinkviewer to javafx.fxml;
    exports com.example.simulinkviewer;
}
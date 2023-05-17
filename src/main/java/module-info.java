module com.example.simulinkviewer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens eg.edu.asu.eng.cse231s.simulinkviewer to javafx.fxml;
    exports eg.edu.asu.eng.cse231s.simulinkviewer;
}
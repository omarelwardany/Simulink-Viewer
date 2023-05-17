package eg.edu.asu.eng.cse231s.simulinkviewer;

import javafx.application.Application;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;


public class ViewerApplication extends Application {
    @Override
    public void start(Stage stage) {
        /* TODO: Scene  (Issue #2)
        *   Textbox and button to get file path, then save it to a String called mdlPath*/
        String mdlPath = /* Test path */ "C:\\Users\\OmarEmadSayedEl-Ward\\Desktop\\Example.mdl";

        File newXML = extractXML(mdlPath);
    }

    public static void main(String[] args) { launch(); }

    /* Extracts important information from mdl file
    *  and then saves it to a new XML file and returns it as a File object*/
    public static File extractXML(String mdlPath) {
        // TODO: Issue #1
    }

    /* Reads XML file and extracts all of its elements into a NodeList */
    public static NodeList parseXML(String XMLPath) {

    }
}
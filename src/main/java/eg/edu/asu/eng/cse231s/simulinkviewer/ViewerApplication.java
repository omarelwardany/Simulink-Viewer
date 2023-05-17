package eg.edu.asu.eng.cse231s.simulinkviewer;

import javafx.application.Application;
import javafx.stage.Stage;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;


public class ViewerApplication extends Application {
    @Override
    public void start(Stage stage) {
        /* TODO: Scene  (Issue #2)
        *   Textbox and button to get file path, then save it to a String called mdlPath*/
        String mdlPath = /* Test path */ "C:\\Users\\OmarEmadSayedEl-Ward\\Desktop\\Example.mdl";

        String newXMLPath = extractXML(mdlPath);

        /* TODO: Scene 2
        *   Display Loading Scene while reading the file*/
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document document;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(newXMLPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) { launch(); }

    /* Extracts important information from mdl file
    *  and then saves it to a new XML file and returns its path*/
    public static String extractXML(String mdlPath) {
        // TODO: Issue #1
    }
}
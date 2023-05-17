package eg.edu.asu.eng.cse231s.simulinkviewer;

import javafx.application.Application;
import javafx.stage.Stage;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ViewerApplication extends Application {
    @Override
    public void start(Stage stage) {
        /* TODO: Scene  (Issue #2)
        *   Textbox and button to get file path, then save it to a String called mdlPath*/
        String mdlPath = /* Test path */ "C:\\Users\\OmarEmadSayedEl-Ward\\Desktop\\Example.mdl";

        // converts the mdl file to an ArrayList of blocks and an ArrayList for lines to be drawn
        File XMLFile = extractXML(mdlPath);
        NodeList blockNodes = blocksFromXML(XMLFile);
        NodeList lineNodes = linesFromXML(XMLFile);
        List<Element> blocks = new ArrayList<>();
        List<Element> lines = new ArrayList<>();
        for (int i = 0; i < blockNodes.getLength(); i++) {
            blocks.add((Element) blockNodes.item(i));
        }
        for (int i = 0; i < lineNodes.getLength(); i++) {
            lines.add((Element) lineNodes.item(i));
        }

        /* TODO: Scene
        *   for each block, get dimensions and position, then add to scene
        *   for each line, get source point and destination point, then add to scene*/

    }

    public static void main(String[] args) { launch(); }

    /* Extracts important information from mdl file
    *  and then saves it to a new XML file and returns it as a File object*/
    public static File extractXML(String mdlPath) {
        // TODO: Issue #1
    }

    /* Reads XML file and extracts all of its Block elements into a NodeList */
    public static NodeList blocksFromXML(File XMLFile) {
        // TODO: Issue #3
    }

    /* Reads XML file and extracts all of its Line elements into a NodeList */
    public static NodeList linesFromXML(File XMLFile) {
        // TODO: Issue #4
    }

}
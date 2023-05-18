package eg.edu.asu.eng.cse231s.simulinkviewer;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ViewerApplication extends Application {
    @Override
    public void start(Stage stage) {
        // required declarations before initial scene event handler
        File XMLFile;
        NodeList blockNodes;
        NodeList lineNodes;
        List<Element> blocks;
        List<Element> lines;
        String mdlPath;

        /* TODO: Scene  (Issue #2)
        *   Textbox and button to get file path, then save it to a String called mdlPath*/

        /* this block of code converts the mdl file to an ArrayList of blocks and an ArrayList for lines to be drawn
           it should be placed inside the initial scene's button event handler */
        mdlPath = /* Test path */ "C:\\Users\\OmarEmadSayedEl-Ward\\Desktop\\Example.mdl";
        XMLFile = extractXML(mdlPath);
        blockNodes = blocksFromXML(XMLFile);
        lineNodes = linesFromXML(XMLFile);
        blocks = new ArrayList<>();
        lines = new ArrayList<>();
        for (int i = 0; i < blockNodes.getLength(); i++) {
            blocks.add(Integer.parseInt(((Element)blockNodes.item(i)).getAttribute("SID")),(Element) blockNodes.item(i)); // adds blocks to ArrayList<Elements>, sorted by SID
        }
        for (int i = 0; i < lineNodes.getLength(); i++) {
            lines.add((Element) lineNodes.item(i)); // adds lines to ArrayList<Elements>
        }

        /* TODO: Scene
        *   for each block, get dimensions and position, then add to scene
        *   for each line, get source point and destination point, then add to scene*/
        Pane drawingPane = new StackPane();
        ArrayList<Rectangle> rectangles = new ArrayList<>();
        // add Blocks to the scene
        for (Element block: blocks) {
            Rectangle rectangle = new Rectangle();
            String position = block.getElementsByTagName("Position").item(0).getTextContent();
            rectangle.setX(getXFromPosition(position));
            rectangle.setY(getYFromPosition(position));
            rectangle.setHeight(getHeightFromPosition(position));
            rectangle.setWidth(getWidthFromPosition(position));
            rectangles.add(rectangle);
            drawingPane.getChildren().add(rectangle);

            Text blockText = new Text();
            blockText.setText(block.getTagName());
            blockText.setX(getXFromPosition(position));
            blockText.setY(getYFromPosition(position));
        }
        int loopCounter = 0;
        for (Element lineElement: lines) {
            Line line = new Line();
            // TODO

            loopCounter++;
        }
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

    public static double getXFromPosition(String position) {
        // TODO: Implement
    }

    public static double getYFromPosition(String position) {
        // TODO: Implement
    }

    public static double getWidthFromPosition(String position) {
        // TODO: Implement
    }

    public static double getHeightFromPosition(String position) {
        // TODO: Implement
    }
}
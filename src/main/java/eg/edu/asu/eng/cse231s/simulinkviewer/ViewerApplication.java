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
        Pane drawingPane = new StackPane();
        File XMLFile;
        NodeList blockNodes;
        NodeList lineNodes;
        List<Element> blocks;
        List<Element> lines;
        String mdlPath;
        ArrayList<Rectangle> rectangles = new ArrayList<>();

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
        // add Blocks to the drawingPane
        for (Element block: blocks) {
            Rectangle rectangle = new Rectangle();
            NodeList blockChildNodes = block.getChildNodes();
            String position = "";
            for (int i = 0; i < blockChildNodes.getLength(); i++) {
                if (((Element) blockChildNodes.item(i)).hasAttribute("Position")) {
                    position = blockChildNodes.item(i).getTextContent();
                }
            }
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
            drawingPane.getChildren().add(blockText);
        }
        // add Lines to the drawingPane
        // iterate over line elements
        for (Element lineElement: lines) {
            int SrcID;
            int startXDir = 1; // initial direction is positive x (1) or negative x (0) TODO
            // iterate over the child nodes of the line
            for (int i = 0; i < lineElement.getChildNodes().getLength(); i++) {
                // Find Points element
                Element currentChild = (Element) lineElement.getChildNodes().item(i);
                if (currentChild.getAttribute("Name").equals("Points")) {
                    if (getPointsFromString(currentChild.getTextContent())[0].getX() >= 0)
                        startXDir = 1;
                    else startXDir = 0;
                    break;
                }
            }
            boolean hasPointsFlag = false;
            // iterate over the child nodes of the line
            for (int i = 0; i < lineElement.getChildNodes().getLength(); i++) {

                Point Src = new Point();
                // in case the Node is a P node
                if (((Element) lineElement.getChildNodes().item(i)).getTagName().equals("P")) {
                    Element currentP = (Element) lineElement.getChildNodes().item(i);

                    // in case the node is Src
                    if (currentP.getAttribute("Name").equals("Src")) {
                        SrcID = Integer.parseInt(currentP.getTextContent().split("#")[0]);
                        Src.setX(rectangles.get(SrcID).getX() + startXDir * rectangles.get(SrcID).getHeight());
                        Src.setY(rectangles.get(SrcID).getY() + 0.5 * rectangles.get(SrcID).getHeight());
                    }
                    // in case the node is Points

                    if (currentP.getAttribute("Name").equals("Points")) {
                        hasPointsFlag = true;
                        Point[] points = getPointsFromString(currentP.getTextContent());
                        Line initialLine = new Line();
                        Point ptCursor = new Point(Src.getX(), Src.getY());
                        initialLine.setStartX(ptCursor.getX());
                        initialLine.setStartY(ptCursor.getY());
                        ptCursor = ptCursor.add(points[0]);
                        initialLine.setEndX(ptCursor.getX());
                        initialLine.setEndY(ptCursor.getY());
                        drawingPane.getChildren().add(initialLine);
                        for (int j = 1; j < points.length; j++) {
                            Line midLine = new Line();
                            midLine.setStartX(ptCursor.getX());
                            midLine.setStartY(ptCursor.getX());
                            ptCursor = ptCursor.add(points[j]);
                            midLine.setEndX(points[j].getX());
                            midLine.setEndY(points[j].getY());
                            drawingPane.getChildren().add(midLine);
                        }
                    }

                    // in case the node is Dst
                }

                // in case the Node is a Branch node
                if (((Element) lineElement.getChildNodes().item(i)).getTagName().equals("Branch")) {

                }
            }
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

    /* Takes String like "[1040, 283, 1075, 317]" and returns X value of the rectangle as a double*/
    public static double getXFromPosition(String position) {
        // TODO: Implement
    }

    /* Takes String like "[1040, 283, 1075, 317]" and returns Y value of the rectangle as a double*/
    public static double getYFromPosition(String position) {
        // TODO: Implement
    }

    /* Takes String like "[1040, 283, 1075, 317]" and returns Width value of the rectangle as a double*/
    public static double getWidthFromPosition(String position) {
        // TODO: Implement
    }

    /* Takes String like "[1040, 283, 1075, 317]" and returns Height value of the rectangle as a double*/
    public static double getHeightFromPosition(String position) {
        // TODO: Implement
    }

    public static Point[] getPointsFromString(String pointsString) {
        Point[] points;
        String[] numbers;
        pointsString = pointsString.replace('[', ' ');
        pointsString = pointsString.replace(']', ' ');
        pointsString = pointsString.trim();
        numbers = pointsString.split(", ");
        points = new Point[numbers.length / 2];

        for (int i = 0, j = 0; i < numbers.length; j++) {
            points[j].setX(Double.parseDouble(numbers[i]));
            i++;
            points[j].setY(Double.parseDouble(numbers[i]));
            i++;
        }
        return points;
    }
}

class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this(0,0);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point add(Point other) {
        double newX = this.x + other.x;
        double newY = this.y + other.y;
        return new Point(newX, newY);
    }
}
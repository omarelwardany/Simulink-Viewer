package eg.edu.asu.eng.cse231s.simulinkviewer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import static eg.edu.asu.eng.cse231s.simulinkviewer.PosHandler.*;


public class ViewerApplication extends Application {
    @Override
    public void start(Stage stage) {
        // required declarations before initial scene event handler
        Pane drawingPane = new StackPane();
        String XMLFile;
        NodeList blockNodes;
        NodeList lineNodes;
        List<Element> blocks;
        List<Element> lines;
        String mdlPath;
        ArrayList<Rectangle> rectangles = new ArrayList<>();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open MDL file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Simulink Models (*.mdl)", "*.mdl");
        fileChooser.getExtensionFilters().add(extFilter);

        File mdlFile = fileChooser.showOpenDialog(stage);

        /* TODO: Scene  (Issue #2)
        *   Textbox and button to get file path, then save it to a String called mdlPath*/

        /* this block of code converts the mdl file to an ArrayList of blocks and an ArrayList for lines to be drawn
           it should be placed inside the initial scene's button event handler */
        mdlPath = mdlFile.getAbsolutePath();
        try {
            XMLFile = extractXMLasString(mdlPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        blockNodes = elementsFromXML(XMLFile, "Block");
        lineNodes = elementsFromXML(XMLFile, "Line");
        blocks = new ArrayList<>();
        for (int i = 0; i < 50; i++) {// TODO: ana may3gbne4 elklam dah
            Element emptyElement = null;
            blocks.add(emptyElement);
        }
        //System.out.println(blocks.size());
        for (int i = 0; i < blockNodes.getLength(); i++) {
            blocks.set(Integer.parseInt(((Element)blockNodes.item(i)).getAttribute("SID")),(Element) blockNodes.item(i)); // adds blocks to ArrayList<Elements>, sorted by SID
        }
//        for (int i = 0; i < 50; i++) {
//            if (blocks.get(i) == null) {
//                blocks.remove(blocks.get(i));
//            }
//        }
        lines = new ArrayList<>();
        for (int i = 0; i < lineNodes.getLength(); i++) {
            lines.add((Element) lineNodes.item(i)); // adds lines to ArrayList<Elements>
        }
        // add Blocks to the drawingPane
        for (Element block: blocks) {
            if (block != null) {
                Rectangle rectangle = new Rectangle();
                String position = "";
                for (int i = 0; i < block.getChildNodes().getLength(); i++) {
                    if ((block.getChildNodes().item(i)).getTextContent().matches(".*,.*,.*,.*") && block.getChildNodes().item(i).getParentNode().equals(block)) {  // direct child bug handled
                        position = block.getChildNodes().item(i).getTextContent();
                        System.out.println(position);
                        break;
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
            else rectangles.add(null);
        }
        // add Lines to the drawingPane
        // iterate over line elements
        for (Element lineElement: lines) {
            int SrcID;
            int DstID;
            int startXDir = 1; // initial direction is positive x (1) or negative x (0)
            int endXDir = 0; // end direction is positive x (0) or negative x (1)
            // iterate over the child nodes of the line
            for (int i = 0; i < lineElement.getChildNodes().getLength(); i++) {
                if (lineElement.getChildNodes().item(i).getParentNode().equals(lineElement)) { // direct child bug handled
                    // Find Points element
                    Node currentChild = lineElement.getChildNodes().item(i); // direct child bug handled
                    if (currentChild.getTextContent().matches(".*,.*")) {
                        if (getPointsFromString(currentChild.getTextContent())[0].getX() >= 0)
                            startXDir = 1;
                        else startXDir = 0;

                        break;
                    }
                }
            }
            Point ptCursor = null;
            Point Src = new Point();
            Point Dst = new Point();
            Point branchPtCursor = null;
            boolean branchPtCursorExists = false;

            // iterate over the child nodes of the line
            for (int i = 0; i < lineElement.getChildNodes().getLength(); i++) {
                boolean usefulNode = false;
                if (lineElement.getChildNodes().item(i).getParentNode().equals(lineElement) && lineElement.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) { // direct child bug handled
                    // in case the Node is a P node

                    if (((Element )lineElement.getChildNodes().item(i)).getTagName().equals("P")) { // direct child bug handled
                        Element currentP = (Element) lineElement.getChildNodes().item(i);  // direct child bug handled

                        // in case the node is Src
                        // System.out.println(currentP.getFirstChild().getTextContent());
                        System.out.println(currentP.getTextContent());
                        if (currentP.getTextContent().matches(".*#out:.*")) {
                            SrcID = Integer.parseInt(currentP.getTextContent().split("#")[0]);
                            Src.setX(rectangles.get(SrcID).getX() + startXDir * rectangles.get(SrcID).getWidth());
                            Src.setY(rectangles.get(SrcID).getY() + 0.5 * rectangles.get(SrcID).getHeight());
                            ptCursor = new Point(Src.getX(), Src.getY());
                            usefulNode = true;
                        }

                        // in case the node is Points
                        else if (currentP.getTextContent().matches(".*,.*") && ptCursor != null) {
                            Point[] points = getPointsFromString(currentP.getTextContent());
                            // draws the midLines
                            for (int j = 0; j < points.length; j++) {
                                Line midLine = new Line();
                                midLine.setStartX(ptCursor.getX());
                                midLine.setStartY(ptCursor.getX());
                                ptCursor = ptCursor.add(points[j]);
                                midLine.setEndX(points[j].getX());
                                midLine.setEndY(points[j].getY());
                                drawingPane.getChildren().add(midLine);
                            }
                            branchPtCursor = new Point(ptCursor.getX(), ptCursor.getY());
                            branchPtCursorExists = true;
                            usefulNode = true;
                        }

                        // in case the node is Dst
                        else if (currentP.getTextContent().matches(".*#in:.*") && ptCursor != null) {
                            DstID = Integer.parseInt(currentP.getTextContent().split("#")[0]);
                            if (rectangles.get(DstID).getX() > ptCursor.getX()) {
                                endXDir = 0;
                            } else endXDir = 1;
                            Dst.setX(rectangles.get(DstID).getX() + endXDir * rectangles.get(DstID).getWidth());
                            Dst.setY(ptCursor.getY());
                            Line endLine = new Line();
                            endLine.setStartY(ptCursor.getX());
                            endLine.setStartY(ptCursor.getY());
                            endLine.setEndX(Dst.getX());
                            endLine.setEndY(Dst.getY());
                            drawingPane.getChildren().add(endLine);
                            // TODO: arrow heads
                            usefulNode = true;
                        }
                    }

                    // in case the Node is a Branch node
                    else if (((Element) lineElement.getChildNodes().item(i)).getTagName().equals("Branch")) {
                        // iterate over the child ndoes of the branch
                        Element branchElement = (Element) lineElement.getChildNodes().item(i);
                        Point branchDst = new Point();
                        int branchDstID;
                        int branchEndXDir = 0; // end direction is is positive x (0) or negative x (1)
                        for (int j = 0; j < branchElement.getChildNodes().getLength(); j++) { // direct child bug not present
                            Node currentP = branchElement.getChildNodes().item(j);


                            // in case the node is Points
                            if (currentP.getTextContent().matches(".*,.*")) {
                                Point[] branchPoints = getPointsFromString(currentP.getTextContent());
                                // draw the midlines
                                for (int k = 0; k < branchPoints.length; k++) {
                                    Line midLine = new Line();
                                    midLine.setStartX(branchPtCursor.getX());
                                    midLine.setStartY(branchPtCursor.getY());
                                    branchPtCursor = branchPtCursor.add(branchPoints[k]);
                                    midLine.setEndX(branchPtCursor.getX());
                                    midLine.setEndY(branchPtCursor.getY());
                                    drawingPane.getChildren().add(midLine);
                                }
                            }

                            // in case the node is Dst
                            if (currentP.getTextContent().matches(".*#in:.*")) {

                            }
                        }
                        usefulNode = true;
                    }
                    // return the cursor to the branch base
                    if (usefulNode) {
                        branchPtCursor = new Point(ptCursor.getX(), ptCursor.getY());
                    }
                }
            }
        }

        /* TODO: Scene
         *   for each block, get dimensions and position, then add to scene
         *   for each line, get source point and destination point, then add to scene*/
        Scene drawingScene = new Scene(drawingPane);
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.setTitle("Viewing " + mdlFile.getName());
        stage.setScene(drawingScene);
        stage.show();
    }

    public static void main(String[] args) { launch(); }

    /* Extracts important information from mdl file
    *  and then saves it to a new XML file and returns it as a File object*/
    public static String extractXMLasString(String mdlPath)  throws IOException {

        String mdlContent = readFileToString(mdlPath);
        String xmlContent = getSubstring(mdlContent, "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<System>", "</System>");
        return xmlContent;

    }

    /* Reads XML file and extracts all of its elements with a specific tagName into a NodeList */
    public static NodeList elementsFromXML(String XMLFile, String tagName) {
        // TODO: Issue #3
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document xmlDoc;
        try {
            File XML = stringToFile(XMLFile);
            xmlDoc = db.parse(XML.getAbsolutePath());

            xmlDoc.getDocumentElement().normalize();
            NodeList toReturn = xmlDoc.getElementsByTagName(tagName);
            System.out.println("toReturn = " + toReturn.getLength());
            System.out.println("toReturn = " + toReturn.getLength());
            return toReturn;
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Point[] getPointsFromString(String pointsString) {
        Point[] points;
        String[] vectors;
        pointsString = pointsString.replace('[', ' ');
        pointsString = pointsString.replace(']', ' ');
        pointsString = pointsString.trim();
        vectors = pointsString.split("; ");
        points = new Point[vectors.length];
        for (int i = 0; i < vectors.length; i++) {
            String[] coords = vectors[i].split(", ");
            points[i] = new Point();
            points[i].setX(Double.parseDouble(coords[0]));
            points[i].setY(Double.parseDouble(coords[1]));
        }
        return points;
    }

    public static void saveFile(File file, String filePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream(filePath);

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();
    }

    public static String readFileToString(String filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static File stringToFile(String content) throws IOException {
        File file = File.createTempFile("temp", ".xml");
        Files.write(file.toPath(), content.getBytes());
        return file;
    }

    public static String getSubstring(String string1, String string2, String string3) {
        int startIndex = string1.indexOf(string2);
        if (startIndex == -1) {
            return "";  // string2 not found in string1
        }
        int endIndex = string1.indexOf(string3, startIndex + string2.length());
        if (endIndex == -1) {
            return "";  // string3 not found after string2 in string1
        }
        return string2 + string1.substring(startIndex + string2.length(), endIndex) + string3;
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
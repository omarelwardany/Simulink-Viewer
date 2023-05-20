package eg.edu.asu.eng.cse231s.simulinkviewer;

public class PosHandler {

    /* Takes String like "[1040, 283, 1075, 317]" and returns X value of the rectangle as a double*/
    public static double getXFromPosition(String position) {
        String[] positionTag =  position.replaceAll("\\[" ,"" ).replaceAll("]" , "").split(",");

        Double x = Double.parseDouble(positionTag[0]);

        return x;
    }

    /* Takes String like "[1040, 283, 1075, 317]" and returns Y value of the rectangle as a double*/
    public static double getYFromPosition(String position) {
        String[] positionTag =  position.replaceAll("\\[" ,"" ).replaceAll("]" , "").split(",");

        Double y = Double.parseDouble(positionTag[1]);

        return y;
    }

    /* Takes String like "[1040, 283, 1075, 317]" and returns Width value of the rectangle as a double*/
    public static double getWidthFromPosition(String position) {
        String[] positionTag =  position.replaceAll("\\[" ,"" ).replaceAll("]" , "").split(", ");

        Double widthPlusX = Double.parseDouble(positionTag[2]);
        Double x = Double.parseDouble(positionTag[0]);
        Double width = widthPlusX - x;
        return width;
    }

    /* Takes String like "[1040, 283, 1075, 317]" and returns Height value of the rectangle as a double*/
    public static double getHeightFromPosition(String position) {
        String[] positionTag =  position.replaceAll("\\[" ,"" ).replaceAll("]" , "").split(", ");

        Double heightPlusY = Double.parseDouble(positionTag[3]);
        Double y = Double.parseDouble(positionTag[1]);
        Double height = heightPlusY - y;
        return height;
    }
}

package dollar;

import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Utils {
    private static final String TAG = "[Utils]";
    public static double getEuclideanDistance(Point p1, Point p2){
        // int dx = p2.getX() - p1.getX();
        // int dy = p2.getY() - p1.getY();
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static Point getCentroid(Point[] points){
        // var x = 0.0, y = 0.0;
        // for (var i = 0; i < points.length; i++) {
        //     x += points[i].X;
        //     y += points[i].Y;
        // }
        // x /= points.length;
        // y /= points.length;
        // return new Point(x, y, 0);
        double cenX = 0;
        double cenY = 0;
        int n = points.length;
        for(int i = 0; i < n; i++){
            cenX += points[i].getX();
            cenY += points[i].getY();
        }
        cenX /= n;
        cenY /= n;
        return new Point(cenX, cenY, 0, 0);
    }

    public static double degreeToRadians(double degree){
        return degree * Math.PI / 180.0;
    }

    public static double getPathLength(Point[] points){
        // var d = 0.0;
        // for (var i = 1; i < points.length; i++) {
        //     if (points[i].ID == points[i-1].ID)
        //         d += Distance(points[i-1], points[i]);
        // }
        // return d;
        double len = 0;
        for(int i = 1; i < points.length; i++){
            if(points[i].getStrokeID() == points[i-1].getStrokeID())
                len += getEuclideanDistance(points[i-1], points[i]);
        }
        return len;
    }

    public static double getPathDistance(Point[] pts1, Point[] pts2){
        double dist = 0;
        if(pts1.length != pts2.length){
            logStr(TAG, "Warning: preprocessed gestures have different number of points");
        }
        for(int i = 0; i < pts1.length; i++){
            dist += getEuclideanDistance(pts1[i], pts2[i]);
        }
        return dist / pts1.length;
    }

    // public static Box getBoundingBox(Point[] points){
    //     // int minX = Integer.MAX_VALUE;
    //     // int maxX = Integer.MIN_VALUE;
    //     // int minY = Integer.MAX_VALUE;
    //     // int maxY = Integer.MIN_VALUE;
    //     double minX = Integer.MAX_VALUE;
    //     double maxX = Integer.MIN_VALUE;
    //     double minY = Integer.MAX_VALUE;
    //     double maxY = Integer.MIN_VALUE;
    //     for(int i = 0; i < points.length; i++){
    //         minX = Math.min(minX, points[i].getX());
    //         maxX = Math.max(maxX, points[i].getX());
    //         minY = Math.min(minY, points[i].getY());
    //         maxY = Math.max(maxY, points[i].getY());
    //     }
    //     return new Box(minX, minY, maxX - minX, maxY - minY);
    // }

    // public static Point[] rotateBy(Point[] points, double radians){
    //     Point[] newPoints = new Point[points.length];
    //     Point cen = getCentroid(points);
    //     double cos = Math.cos(radians);
    //     // Utils.logStr(TAG, "cos: " + cos);
    //     double sin = Math.sin(radians);
    //     // Utils.logStr(TAG, "sin: " + sin);
    //     for(int i = 0; i < points.length; i++){
    //         // int newX = cen.getX() + (int) Math.round((points[i].getX() - cen.getX()) * cos - (points[i].getY() - cen.getY()) * sin);
    //         // int newY = cen.getY() + (int) Math.round((points[i].getX() - cen.getX()) * sin - (points[i].getY() - cen.getY()) * cos);
    //         double newX = cen.getX() + (points[i].getX() - cen.getX()) * cos - (points[i].getY() - cen.getY()) * sin;
    //         double newY = cen.getY() + (points[i].getX() - cen.getX()) * sin + (points[i].getY() - cen.getY()) * cos;
    //         newPoints[i] = new Point(newX, newY, 0, 0);
    //     }
    //     // Utils.logStr(TAG, "before rotatation: (" + points[0].getX() + "," + points[0].getY() + ")");
    //     // Utils.logStr(TAG, "rotated point: (" + newPoints[0].getX() + "," + newPoints[0].getY() + ")");
    //     return newPoints;
    // }

    public static String listOrArrayToString(List<String> strings){
        return "\"{" + String.join(",", strings) + "}\"";
    }

    public static String listOrArrayToString(String[] strings){
        return "\"{" + String.join(",", strings) + "}\"";
    }

    public static Integer[] generateRandNums(int n, int excluded){
        // Utils.logStr(TAG, "Randomly selecting " + n + " indices");
        // Utils.logStr(TAG, "Excluding: " + excluded);
        Set<Integer> picked = new HashSet<>();
        // int[] numbers = new int[n];
        // int i = 0;
        while(picked.size() < n) {
            Random rand = new Random();
            int randNum = rand.nextInt(Constants.numGesturesPerType);
            while(randNum == excluded){
                randNum = rand.nextInt(Constants.numGesturesPerType);
            }
            picked.add(randNum);
            // Utils.logStr(TAG, "Randomly selected: " + randNum);
            // numbers[i++] = randNum;
        }
        // return numbers;
        return picked.toArray(new Integer[n]);
    }

    public static String getTestingLog(
            String uid, String gestureType, int randIter, int E, int trSetSize, RecognitionResult res, String candidate
        ){
            return String.join(",", new String[]{
                uid, gestureType, String.valueOf(randIter), String.valueOf(E), String.valueOf(trSetSize), 
                res.getTrainingSetVerboseInfo(), candidate, res.getLabel(), String.valueOf(res.getIsCorrect()),
                String.format("%.2f", res.getScore()), res.getNBestList().get(0), res.getNBestListVerboseInfo()
            }) + "\n";
    }

    public static final String columnNames = String.join(",", 
        new String[]{ 
        "RecoResultBestMatch[specific-instance]","RecoResultNBestSorted[instance-and-score]"}) + "\n";

    public static void logStr(String tag, String msg){
        System.out.println(tag + " " + msg);
    }

    public static ImageIcon getScaledImageIcon(String filePath, int width, int height){
        ImageIcon imageIcon = new ImageIcon(filePath);
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg); 
        return imageIcon;
    }

    public static void saveToXMLFile(PointCloud gesture){
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            doc.setXmlStandalone(true);
            Element rootElement = Utils.getGestureElemtent(doc, gesture);
            doc.appendChild(rootElement);
            // Utils.addStrokeElemtents(doc, rootElement, gesture);
            Utils.addPointElemtents(doc, rootElement, gesture);
            // rootElement.appendChild(pointElement);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            DOMSource source = new DOMSource(doc);
            String rootDir = "xml_logs/"; // Mac
            // String rootDir = "xml_logs\\"; // Windows
            String userDir = "xml_logs/" + gesture.getUserId() + "/";
            // String userDir = "xml_logs\\" + gesture.getUserId() + "\\"; // Windows
            Utils.createIfNotExists(rootDir);
            Utils.createIfNotExists(userDir);
            String nameIdx = String.format("%02d", gesture.getIdx());
            String filename =  gesture.getLabel()+nameIdx;
            String logFileName = userDir + filename +".xml";
            File logFile = new File(logFileName);
            if(logFile.createNewFile()){
                Utils.logStr(TAG, "File created: " + logFile.getName());
            }else{
                Utils.logStr(TAG, "File already exists");
            }
            // FileWriter fileWriter = new FileWriter(logFileName);
            // fileWriter.write(source);
            StreamResult streamFile = new StreamResult(logFile);
            transformer.transform(source, streamFile);
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }

    public static Element getGestureElemtent(Document doc, PointCloud gesture){
        Element element = doc.createElement("Gesture");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");  
        Date date = new Date();
        List<Point> rp = gesture.getRawPoints();
        int numPts = rp.size();
        long ms = rp.get(numPts-1).getTimestamp() - rp.get(0).getTimestamp();
        int idx = gesture.getIdx();
        String nameIdx = String.format("%02d", idx);
        element.setAttribute("Name", gesture.getLabel()+"~"+nameIdx);
        element.setAttribute("Subject", gesture.getUserId().replaceAll("[a-zA-Z0]",""));
        element.setAttribute("Speed", "MEDIUM");
        element.setAttribute("Number", String.valueOf(idx));
        element.setAttribute("NumPts", String.valueOf(numPts));
        element.setAttribute("Millseconds", String.valueOf(ms)); // from $1
        element.setAttribute("AppName", "PointClouds"); // from $1
        element.setAttribute("AppVer", "0.0.0.0"); // from $1
        element.setAttribute("Date", dateFormat.format(date)); // from $1
        element.setAttribute("TimeOfDay", timeFormat.format(date)); // from $1
        return element;
    }

    public static void addPointElemtents(Document doc, Element rootElement, PointCloud gesture){
        List<Point> rawPoints = gesture.getRawPoints();
        Utils.logStr(TAG, "Number of points " + rawPoints.size());
        int strokeID = 1;
        Element strokeElement = doc.createElement("Stroke");
        strokeElement.setAttribute("index", String.valueOf(strokeID));
        rootElement.appendChild(strokeElement);
        for(Point p : rawPoints){
            if(strokeID != p.getStrokeID()){
                strokeID = p.getStrokeID();
                strokeElement = doc.createElement("Stroke");
                strokeElement.setAttribute("index", String.valueOf(strokeID));
                rootElement.appendChild(strokeElement);
            }
            Element ptElement = doc.createElement("Point");
            ptElement.setAttribute("X", String.valueOf((int) p.getX()));
            ptElement.setAttribute("Y", String.valueOf((int) p.getY()));
            ptElement.setAttribute("T", String.valueOf(p.getTimestamp()));
            // .appendChild(element);
            strokeElement.appendChild(ptElement);
        }
    }

    public static void createIfNotExists(String directoryName){
        File directory = new File(directoryName);
        if (!directory.exists()){
            directory.mkdir();
        }
    }
}

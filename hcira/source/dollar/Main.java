package dollar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
// import javax.swing.SwingUtilities;
// import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.*;
// import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.concurrent.TimeUnit;

public class Main {
    private static final String TAG = "[Main]";
    public static void main(String args[]){
        // Data Collection or Live Demo
        String userId = "p03";
        JFrame f = new JFrame("Canvas");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(Constants.windowSize+100, Constants.windowSize-200);
        f.setLocationRelativeTo(null);

        GesturePanel gesturePanel = new GesturePanel(userId, Constants.panelSize);
        gesturePanel.setSize(Constants.panelSize, Constants.panelSize+260);
        gesturePanel.setAlignmentX(SwingConstants.LEFT);
        // gesturePanel.setAlignmentY(SwingConstants.CENTER);
        f.add(gesturePanel);

        JLabel guideLabel = new JLabel(Utils.getScaledImageIcon("zhuyin.gif", Constants.panelSize+75, Constants.panelSize+75), SwingConstants.RIGHT);
        guideLabel.setAlignmentY(SwingConstants.TOP);
        f.add(guideLabel);

        // JLabel guideLabel2 = new JLabel(Utils.getScaledImageIcon("unistrokes.gif", Constants.panelSize-250, Constants.panelSize-250), SwingConstants.RIGHT);
        // guideLabel2.setAlignmentY(SwingConstants.BOTTOM);
        // f.add(guideLabel2);

        // f.pack();
        f.setVisible(true);

        // Offline user-dependent testing
    //     JFrame frame = new JFrame("User-Dependent Testing");
    //     JLabel messageToUser = new JLabel("Reading dataset...");
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setLayout(new BorderLayout());
    //     frame.getContentPane().add(messageToUser, BorderLayout.CENTER);
    //     frame.setSize(400, 200);
    //     // frame.pack();
    //     frame.setVisible(true);
    //     long startTime = System.nanoTime();
    //     // User[] users = new User[Constants.numUsers];
    //     List<User> users = new ArrayList<>();
    //     // Read files and preprocess
    //     String rootDir = System.getProperty("user.dir");
    //     // Utils.logStr(TAG, rootDir);
    //     // String usersDir = rootDir + "\\xml_logs"; // for Windows deployment
    //     // String usersDir = rootDir + "/xml_logs"; // testing on MacOS
    //     // String usersDir = rootDir + "/xml_logs_part4"; // testing on MacOS
    //     // String usersDir = rootDir + "/mmg"; // testing on MacOS
    //     String usersDir = rootDir + "\\mmg"; // testing on Windows
    //     // String usersDir = rootDir + "/zhuyin"; // testing on MacOS
    //     // String usersDir = rootDir + "\\zhuyin"; // testing on MacOS
    //     // String usersDir = rootDir + "/testing_logs"; // testing on MacOS
    //     // String usersDir = rootDir + "/testing_logs_2"; // testing on MacOS
    //     File userFolers = new File(usersDir);
    //     for(File userFolder : userFolers.listFiles()){
    //         String userName = userFolder.getName();
    //         // if(userName.charAt(0) == 'p' && !userName.contains("pilot")){ // s or p, for our own dataset (following the naming of the dataset used in the $1 paper)
    //         if(userName.contains("MEDIUM")){ // for mmg, using only the medium speed
    //         // if(userName.charAt(0) == 'p'){
    //             String userIdFromFile = 'p' + userName.substring(0,2); //mmg: 2-digit user ID
    //             // String userIdFromFile = userName;
    //             Utils.logStr(TAG, "Creating user: " + userIdFromFile);
    //             User user = new User(userIdFromFile);
    //             // String gesturesDir = usersDir + "\\" + userName + "\\medium"; // for Windows deployment
    //             // String gesturesDir = usersDir + "/" + userName + "/medium"; // testing on MacOS
    //             // String gesturesDir = usersDir + "/" + userName; // testing on MacOS without speed
    //             String gesturesDir = usersDir + "\\" + userName; // Windows
    //             File userGestures = new File(gesturesDir);
    //             for(File gestureFile : userGestures.listFiles()){
    //                 try{
    //                     // Utils.logStr(TAG, "Parsing gesture file: " + gestureFile.getName());
    //                     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    //                     DocumentBuilder db = dbf.newDocumentBuilder();
    //                     Document doc = db.parse(gestureFile);
    //                     Element gestureElement = doc.getDocumentElement();
    //                     String gestureName = gestureElement.getAttribute("Name");
    //                     // int gestureIndex = Integer.valueOf(gestureElement.getAttribute("Number")); // for our own dataset
    //                     int gestureIndex = Integer.valueOf(gestureName.substring(gestureName.length()-2, gestureName.length())); // for mmg
    //                     // String gestureType = gestureName.substring(0, gestureName.length()-2); // for our own dataset
    //                     String gestureType = gestureName.substring(0, gestureName.length()-3); // for mmg
    //                     // Utils.logStr(TAG, "Gesture index: " + gestureIndex);
    //                     // Utils.logStr(TAG, "Gesture type: " + gestureType);
    //                     NodeList gestureStrokes = gestureElement.getElementsByTagName("Stroke");
    //                     // PointCloud gesture = new PointCloud(gestureType, gestureIndex, userName);
    //                     PointCloud gesture = new PointCloud(gestureType, gestureIndex, userIdFromFile); // for mmg
    //                     for(int i = 0; i < gestureStrokes.getLength(); i++){
    //                         Element strokeElement = (Element) gestureStrokes.item(i);
    //                         NodeList gesturePoints = strokeElement.getElementsByTagName("Point");
    //                         for(int j = 0; j < gesturePoints.getLength(); j++){
    //                             Element pointElement = (Element) gesturePoints.item(j);
    //                             int x = Integer.valueOf(pointElement.getAttribute("X"));
    //                             int y = Integer.valueOf(pointElement.getAttribute("Y"));
    //                             // Utils.logStr(TAG, "Parsing the point: (" + x + "," + y + ")");
    //                             gesture.addRawPoint(x, y, i+1, 0);
    //                         }
    //                     }
    //                     gesture.preprocessRawPoints();
    //                     user.addGesture(gesture, gestureType, gestureIndex);
    //                 } catch (Exception e){
    //                     e.printStackTrace();
    //                 }
                    
    //             }
    //             users.add(user);
    //         }
    //     }        
    //     // Create log file
    //     String logFileName = "user-dependent-testing-log.csv";
    //     File logFile = new File(logFileName);
    //     try{
    //         if(logFile.createNewFile()){
    //             Utils.logStr(TAG, "File created: " + logFile.getName());
    //         }else{
    //             Utils.logStr(TAG, "File already exists");
    //         }
    //     } catch (IOException e) {
    //         // Utils.logStr(TAG, "An error occurred while creating a file");
    //         e.printStackTrace();
    //     }
    //     // // 1. Run user-depedent tests and 2. Write log file
    //     messageToUser.setText("Logging testing result...");
    //     try{
    //         FileWriter fileWriter = new FileWriter(logFileName);
    //         fileWriter.write(Constants.firstLogLine + Constants.columnNames);
    //         double totalAvgAccuracy = 0;
    //         int count = 0;
    //         int userCount = 0;
    //         // Run user-depedent tests
    //         // For each user
    //         for(User user : users){
    //             Utils.logStr(TAG, "For user " + user.getId());
    //             double exampleAccuracy = 0;
    //             for(int i = 1; i < Constants.numGesturesPerType; i++){
    //                 //For number of templates per type E = 1 ~ 9
    //                 double randomIterAccuracy = 0;
    //                 for(int j = 0; j < Constants.numRandomIterations; j++){    
    //                     // OneDollarRecognizer recognizer = new OneDollarRecognizer();
    //                     PDollarRecognizer recognizer = new PDollarRecognizer();
    //                     // For each type
    //                     // Utils.logStr(TAG, "Number of gesture types: " + user.getGestures().entrySet().size());
    //                     // List<Gesture> testingTemplates = new ArrayList<>();
    //                     List<PointCloud> testingTemplates = new ArrayList<>();
    //                     // for(Map.Entry<String,Gesture[]> entry : user.getGestures().entrySet()){
    //                     for(Map.Entry<String,PointCloud[]> entry : user.getGestures().entrySet()){
    //                         // Utils.logStr(TAG, "Selecting " + i + " training templates and one testing template for the type: " + entry.getKey());
    //                         /*
    //                         Select a gesture x per type to be tested and add to the testing set 
    //                         Final size = 16
    //                         */
    //                         Random rand = new Random();
    //                         // Generate random integers in range 0 to numGesturesPerType-1 (9)
    //                         int testingIdx = rand.nextInt(Constants.numGesturesPerType);
    //                         // Utils.logStr(TAG, "Selected " + testingIdx + " as the testing index (0-based)");
    //                         testingTemplates.add(entry.getValue()[testingIdx]);
    //                         /*
    //                         Add E training templates to the recognizer, excluding the one (x) selected above, for each type
    //                         Final size = E * 16
    //                         */
    //                         Integer[] trainingIndices = Utils.generateRandNums(i, testingIdx);
    //                         // Utils.logStr(TAG, "Seletecd " + trainingIndices.length + " training indices");
    //                         for(int k = 0; k < trainingIndices.length; k++){
    //                             // if(i == 1){
    //                             //     Utils.logStr(TAG, "Selected " + entry.getValue()[trainingIndices[k]].getVerboseInfo() + " as the training template");    
    //                             // }
    //                             // Utils.logStr(TAG, "Selected " + trainingIndices[k] + " as the training index (0-based)");
    //                             recognizer.addTrainingTemplate(entry.getValue()[trainingIndices[k]]);
    //                         }
    //                     }
    //                     // Utils.logStr(TAG, "Number of testing samples: " + testingTemplates.size());
    //                     // Utils.logStr(TAG, "Number of training samples: " + recognizer.getTrainingSetSize());
    //                     double testingAccuracy = 0;
    //                     for(PointCloud testingTemplate : testingTemplates){
    //                         RecognitionResult result = recognizer.recognize(testingTemplate);
    //                         // Utils.logStr(TAG, "Correct or incorrect: " + result.getIsCorrect());
    //                         testingAccuracy += result.getIsCorrect();
    //                         count++;
    //                         String testingLog = Utils.getTestingLog(
    //                                 user.getId(), testingTemplate.getLabel(), j+1, i, recognizer.getTrainingSetSize(), result, 
    //                                 testingTemplate.getVerboseInfo()
    //                             );
    //                         fileWriter.write(testingLog);
    //                     }
    //                     testingAccuracy /= testingTemplates.size();
    //                     // Utils.logStr(TAG, "Testing accuracy: " + testingAccuracy);
    //                     randomIterAccuracy += testingAccuracy;
    //                 }
    //                 randomIterAccuracy /= Constants.numRandomIterations;
    //                 // Utils.logStr(TAG, "Random iteration accuracy: " + randomIterAccuracy);
    //                 System.out.println(randomIterAccuracy);
    //                 exampleAccuracy += randomIterAccuracy;
    //             }
    //             exampleAccuracy /= (Constants.numGesturesPerType - 1);
    //             // Utils.logStr(TAG, "Example accuracy: " + exampleAccuracy);
    //             totalAvgAccuracy += exampleAccuracy;
    //             userCount++;
    //         }
    //         Utils.logStr(TAG, "Number of recognition testings: " + count);
    //         // Utils.logStr(TAG, "Total average accuracy: " + totalAvgAccuracy / Constants.numUsers);
    //         // fileWriter.write("TotalAvgAccuracy," + totalAvgAccuracy / Constants.numUsers);
    //         fileWriter.write("TotalAvgAccuracy," + totalAvgAccuracy / userCount);
    //         fileWriter.close();
    //         // Utils.logStr(TAG, "Successfully wrote to the file");
    //         long endTime = System.nanoTime();
    //         long elapsedTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
    //         // Utils.logStr(TAG, "Elapsed time: " + elapsedTime);
    //         messageToUser.setText("Done! Elapsed time (milliseconds): " + String.valueOf(elapsedTime));
    //     } catch (IOException e) {
    //         // Utils.logStr(TAG, "An error occurred while writing to a file");
    //         e.printStackTrace();
    //     }
    }
}
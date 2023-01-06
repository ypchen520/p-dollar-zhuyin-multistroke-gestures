package dollar;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.*;

public class GesturePanel extends JPanel{

    private static final String TAG = "[GesturePanel]";
    // private Gesture gesture;
    private PointCloud gesture;
    private int gestureIdx;
    private int gestureTypeIdx;
    private String userId;
    // private String[] gestureTypes = new String[]{
    //     "triangle", "x", "rectangle", "circle", "check", "caret", "zig-zag", "arrow", 
    //     "left_square_bracket", "right_square_bracket", "v", "delete", "left_curly_brace", "right_curly_brace",
    //     "star", "pigtail"
    // };
    private String[] gestureTypes = new String[]{
        "b", "p", "m", "f", "d", "l", "t", "s", "g", "ch", "en", "eng", "o", "e", "ts", "k"
    };
    private Map<String, Integer> gestureCount;
    private int panelSize;
    private JLabel textDescription;
    private int prevX = -1;
    private int prevY = -1;
    private int isDone = 0;
    private int count = 0;
    private int strokeID = 0;
    // private boolean isNext = true;
    // private long startTime = 0;
    private PDollarRecognizer pDollarRecognizer;

    public GesturePanel(String uid, int panSize) {
        this.userId = uid;
        this.gestureIdx = -1;
        this.gestureTypeIdx = -1;
        this.panelSize = panSize;
        this.gestureCount = new HashMap<>();
        // this.gesture = new Gesture();
        for(int i = 0; i < Constants.numPointClouds; i++){ //numPointClouds == 16
            gestureCount.put(gestureTypes[i], 1);
        }

        setLayout(new BorderLayout());

        // setBorder(BorderFactory.createLineBorder(Color.blue, 3));
        setBorder(BorderFactory.createMatteBorder(3,3,0,3,Color.ORANGE));
        this.addButtons();
        this.textDescription = new JLabel(
            "<html>"+Constants.appDescription+"</html>"
        );
        textDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        JPanel textBP = new JPanel(new BorderLayout());
        textBP.setPreferredSize(new Dimension(panelSize, 120));
        // submitFP.setBorder(BorderFactory.createLineBorder(Color.green, 1));
        textBP.setBorder(BorderFactory.createMatteBorder(0,0,3,0,Color.ORANGE));
        // textDescription.setPreferredSize(new Dimension(panelSize, 200));
        textBP.add(textDescription, BorderLayout.NORTH);
        add(textBP, BorderLayout.NORTH);

        Random randType = new Random();
        // Generate random integers in range 0 to numGestureTypes-1 (15)
        int typeIdx = randType.nextInt(Constants.numPointClouds);
        String currType = gestureTypes[typeIdx];
        while(gestureCount.get(currType) > 10){
            typeIdx = randType.nextInt(Constants.numPointClouds);
            currType = gestureTypes[typeIdx];
        }
        gestureTypeIdx = typeIdx;
        currType = gestureTypes[gestureTypeIdx];
        gestureIdx = gestureCount.get(currType);
        gestureCount.put(currType, gestureIdx+1);
        
        // For data collection
        // String showCount = String.valueOf(this.count) + "/" + String.valueOf(Constants.numGesturesPerType*Constants.numPointClouds);
        // String showType = currType.replaceAll("_", " ");
        // textDescription.setText(
        //     "<html>"+Constants.appDescription+"Number of gestures saved: <b style='color:blue;'>"+showCount+"</b>"+"<br/>Please draw: <b style='color:blue;'>"+showType+"</b>"+"</html>"
        // );
        // For live demo
        textDescription.setText(
            "<html>"+Constants.appDescriptionLiveDemo+"</html>"
        );
        pDollarRecognizer = new PDollarRecognizer();
        pDollarRecognizer.addDefaultTrainingTemplates();

        gesture = new PointCloud(gestureTypes[gestureTypeIdx], gestureIdx, userId);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("Mouse pressed.");
                strokeID++;
                int x = e.getX();
                int y = e.getY();
                // if(x >= 0 && x < panelSize && y >= 0 && y <= panelSize){
                if(x >= 0 && x < panelSize && y >= 120 && y <= panelSize+200){
                    // Allow multiple strokes
                    // gesture.clearRawPoints();
                    if(x != prevX || y != prevY){
                        prevX = x;
                        prevY = y;
                        Date currentDate = new Date();
                        long ts = currentDate.getTime();
                        gesture.addRawPoint(x, y, strokeID, ts);
                        repaint();
                    }
                    // startTime = System.nanoTime();
                }
            }
            public void mouseReleased(MouseEvent e) {
                prevX = -1;
                prevY = -1;
                // if(gesture.getRawPoints().size() < 20){
                //     String showCount = String.valueOf(count) + "/" + String.valueOf(Constants.numGesturesPerType*Constants.numPointClouds);
                //     textDescription.setText(
                //         "<html>"+Constants.appDescription+"<b style='color:blue;'>"+showCount+"</b>"+"<br/>Please draw: <b style='color:blue;'>Too few points made. Please try again.</b></html>"
                //     ); 
                // }
                System.out.println("Mouse released.");
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                // if(x >= 0 && x < panelSize && y >= 0 && y <= panelSize){
                if(x >= 0 && x < panelSize && y >= 120 && y <= panelSize+200 && (x != prevX || y != prevY)){
                    prevX = x;
                    prevY = y;
                    Date currentDate = new Date();
		            long ts = currentDate.getTime();
                    Utils.logStr(TAG, x + ", " + y);                
                    gesture.addRawPoint(x, y, strokeID, ts);
                    repaint();
                }
            }
        });
    }

    // private void addBottomAxis(){
    //     JPanel bottomAxis = new JPanel(new BoxLayout(AxisLayout.HORIZONTAL));

    // }
    private void addButtons(){
        JPanel buttonFP = new JPanel(new FlowLayout());
        JButton clear = getClearButton();
        JButton submit = getSubmitButton();
        JButton recognize = getRecognizeButton();
        buttonFP.setPreferredSize(new Dimension(100, 60));
        // submitFP.setBorder(BorderFactory.createLineBorder(Color.green, 1));
        buttonFP.setBorder(BorderFactory.createMatteBorder(3,0,3,0,Color.ORANGE));
        buttonFP.add(clear);

        // For data collection
        // buttonFP.add(submit);
        // For live demo
        buttonFP.add(recognize);

        add(buttonFP, BorderLayout.SOUTH);
    }

    private JButton getClearButton(){
        JButton clearButton = new JButton("Clear");

        // Data collection
        // clearButton.setFont(new Font("Arial", Font.PLAIN, 20));
        // Set the font size to be smaller for live demo
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));

        clearButton.setPreferredSize(new Dimension(100,50));

        clearButton.setEnabled(true);

        clearButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println(e.getActionCommand());
                clearButton.setEnabled(true);
                if(gesture.getRawPoints().size() > 0) gesture.clearRawPoints();

                // Comment out for live demo
                // String showCount = String.valueOf(count) + "/" + String.valueOf(Constants.numGesturesPerType*Constants.numPointClouds);
                // String showType = gestureTypes[gestureTypeIdx].replaceAll("_", " ");
                // textDescription.setText(
                //     "<html>"+Constants.appDescription+"Number of gestures saved: <b style='color:blue;'>"+showCount+"</b>"+"<br/>Please draw: <b style='color:blue;'>"+showType+"</b></html>"
                // );
                // Live demo
                textDescription.setText(
                    "<html>"+Constants.appDescriptionLiveDemo+"</html>"
                );
                
                strokeID = 0;
                repaint();
            }
        });
        return clearButton;
    }

    private JButton getRecognizeButton(){
        JButton clearButton = new JButton("Recognize");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.setPreferredSize(new Dimension(100,50));

        clearButton.setEnabled(true);

        clearButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println(e.getActionCommand());
                clearButton.setEnabled(true);
                // if(gesture.getRawPoints().size() > 0) gesture.clearRawPoints();
                gesture.preprocessRawPoints();
                RecognitionResult result = pDollarRecognizer.recognize(gesture);
                String resultLabel = result.getLabel();
                String resultScore = String.format("%.2f", result.getScore());
                textDescription.setText(
                    "<html>"+Constants.appDescriptionLiveDemo+"<b style='color:blue;'>Result: "+resultLabel+" ("+resultScore+")</b>"+"</html>"
                );
                // strokeID = 0;
                repaint();
            }
        });
        return clearButton;
    }

    private JButton getSubmitButton(){
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        submitButton.setPreferredSize(new Dimension(100,50));
        submitButton.setEnabled(true);
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(gesture.getRawPoints().size() < 20){
                    String showCount = String.valueOf(count) + "/" + String.valueOf(Constants.numGesturesPerType*Constants.numPointClouds);
                    String showType = gestureTypes[gestureTypeIdx].replaceAll("_", " ");
                    textDescription.setText(
                        "<html>"+Constants.appDescription+"Number of gestures saved: <b style='color:blue;'>"+showCount+"</b>"+"<br/>Please draw: <b style='color:blue;'>"+showType+"</b></html>"
                    );                    
                }else{
                    count++;
                    Utils.saveToXMLFile(gesture);
                    if(gestureIdx == Constants.numGesturesPerType){
                        isDone++;
                        if(isDone == Constants.numPointClouds){
                            String showCount = String.valueOf(count) + "/" + String.valueOf(Constants.numGesturesPerType*Constants.numPointClouds);
                            textDescription.setText(
                                "<html>"+Constants.appDescription+"Number of gestures saved: <b style='color:blue;'>"+showCount+"</b>"+"<br/>Please draw: <b style='color:blue;'>Finished! Thank you!</b></html>"
                            );
                            submitButton.setEnabled(false);
                            gesture.clearRawPoints();
                            repaint();
                            return;
                        }
                    }
                    if(isDone != Constants.numPointClouds){              
                        Random randType = new Random();
                        // Generate random integers in range 0 to numGestureTypes-1 (15)
                        int typeIdx = randType.nextInt(Constants.numPointClouds);
                        String currType = gestureTypes[typeIdx];
                        while(gestureCount.get(currType) > Constants.numGesturesPerType){
                            typeIdx = randType.nextInt(Constants.numPointClouds);
                            currType = gestureTypes[typeIdx];
                        }
                        gestureTypeIdx = typeIdx;
                        currType = gestureTypes[gestureTypeIdx];
                        gestureIdx = gestureCount.get(currType);
                        gestureCount.put(currType, gestureIdx+1);
                        gesture = new PointCloud(gestureTypes[gestureTypeIdx], gestureIdx, userId);
                    
                        String showCount = String.valueOf(count) + "/" + String.valueOf(Constants.numGesturesPerType*Constants.numPointClouds);
                        String showType = currType.replaceAll("_", " ");
                        textDescription.setText(
                            "<html>"+Constants.appDescription+"Number of gestures saved: <b style='color:blue;'>"+showCount+"</b>"+"<br/>Please draw: <b style='color:blue;'>"+showType+"</b></html>"
                        );
                    }
                }
                submitButton.setEnabled(true);
                gesture.clearRawPoints();
                strokeID = 0;
                repaint();
            }
        });
        return submitButton;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(3));
        // for(Point p : gesture.getRawPoints()){
        //     g2d.draw(new Line2D.Double(p.getX(), p.getY(), p.getX(), p.getY()));
        // }
        if(gesture != null){
            for(int i = 1; i < gesture.getRawPoints().size(); i++){
                double pX = gesture.getRawPoints().get(i-1).getX();
                double pY = gesture.getRawPoints().get(i-1).getY();
                int pStrokeID = gesture.getRawPoints().get(i-1).getStrokeID();
                double X = gesture.getRawPoints().get(i).getX();
                double Y = gesture.getRawPoints().get(i).getY();
                int strokeID = gesture.getRawPoints().get(i).getStrokeID();
                if(pStrokeID == strokeID){
                    g2d.draw(new Line2D.Double(pX, pY, X, Y));
                }
            }
        }  
    }
}

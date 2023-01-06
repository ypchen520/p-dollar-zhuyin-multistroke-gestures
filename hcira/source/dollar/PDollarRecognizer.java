package dollar;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PDollarRecognizer {
    private static final String TAG = "[PDollarRecognizer]";
    private List<PointCloud> trainingTemplates;
    public PDollarRecognizer(){
        this.trainingTemplates = new ArrayList<>();
    }
    public void addTrainingTemplate(PointCloud gesture){
        this.trainingTemplates.add(gesture);
        // Utils.logStr(TAG, "Added one gesture, current training set size: " + this.trainingTemplates.size());
    }

    public void addDefaultTrainingTemplates(){
        String rootDir = System.getProperty("user.dir");
        String usersDir = rootDir + "/live_demo";
        // String usersDir = rootDir + "\\live_demo"; // Win
        File userFolers = new File(usersDir);
        for(File userFolder : userFolers.listFiles()){
            String userName = userFolder.getName();
            if(userName.charAt(0) == 'p'){
                Utils.logStr(TAG, "Creating user: " + userName);
                String gesturesDir = usersDir + "/" + userName; // MacOS
                // String gesturesDir = usersDir + "\\" + userName; // Win
                File userGestures = new File(gesturesDir);
                for(File gestureFile : userGestures.listFiles()){
                    try{
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                        DocumentBuilder db = dbf.newDocumentBuilder();
                        Document doc = db.parse(gestureFile);
                        Element gestureElement = doc.getDocumentElement();
                        String gestureName = gestureElement.getAttribute("Name");
                        int gestureIndex = Integer.valueOf(gestureElement.getAttribute("Number"));
                        String gestureType = gestureName.substring(0, gestureName.length()-3);
                        NodeList gestureStrokes = gestureElement.getElementsByTagName("Stroke");
                        PointCloud gesture = new PointCloud(gestureType, gestureIndex, userName);
                        for(int i = 0; i < gestureStrokes.getLength(); i++){
                            Element strokeElement = (Element) gestureStrokes.item(i);
                            NodeList gesturePoints = strokeElement.getElementsByTagName("Point");
                            for(int j = 0; j < gesturePoints.getLength(); j++){
                                Element pointElement = (Element) gesturePoints.item(j);
                                int x = Integer.valueOf(pointElement.getAttribute("X"));
                                int y = Integer.valueOf(pointElement.getAttribute("Y"));
                                // Utils.logStr(TAG, "Parsing the point: (" + x + "," + y + ")");
                                gesture.addRawPoint(x, y, i+1, 0);
                            }
                        }
                        gesture.preprocessRawPoints();
                        trainingTemplates.add(gesture);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    
                }
            }
        }
    }

    public RecognitionResult recognize(PointCloud candidateGesture){
        // var t0 = Date.now();
		// var candidate = new PointCloud("", points);

		// var u = -1;
		// var b = +Infinity;
		// for (var i = 0; i < this.PointClouds.length; i++) // for each point-cloud template
		// {
		// 	var d = GreedyCloudMatch(candidate.Points, this.PointClouds[i]);
		// 	if (d < b) {
		// 		b = d; // best (least) distance
		// 		u = i; // point-cloud index
		// 	}
		// }
		// var t1 = Date.now();
		// return (u == -1) ? new Result("No match.", 0.0, t1-t0) : new Result(this.PointClouds[u].Name, b > 1.0 ? 1.0 / b : 1.0, t1-t0);
        double candidateDist = Integer.MAX_VALUE;
        String resultLabel = "unknown";
        double resultScore = 0;
        int isCorrect = 0;
        List<String> verboseTrainingSet = new ArrayList<>();
        List<NBestListItem> nBestList = new ArrayList<>();
        List<String> nBestListStr = new ArrayList<>();
        for(int i = 0; i < this.trainingTemplates.size(); i++){
            PointCloud template = this.trainingTemplates.get(i);
            String verboseInfo = template.getVerboseInfo();
            // if(this.trainingTemplates.size() == 16){
            //     Utils.logStr(TAG, verboseInfo);
            // }
            double dist = greedyCloudMatch(candidateGesture.getPoints(), template);
            // double dist = distAtBestAng(candidateGesture.getPoints(), template, -1 * Constants.angleRange, Constants.angleRange, Constants.anglePrecision);
            double score = dist > 1.0 ? 1.0 / dist : 1.0;
            // double score = 1 - dist / Constants.halfDiagonal;
            nBestList.add(new NBestListItem(score, verboseInfo)); 
            verboseTrainingSet.add(verboseInfo);
            // Utils.logStr(TAG, "Testing dist: " + dist);
            // Utils.logStr(TAG, "Testing label: " + template.getLabel());
            if(dist < candidateDist){
                candidateDist = dist;
                resultLabel = template.getLabel();
            }
        }
        // Utils.logStr(TAG, "Testing result dist: " + candidateDist);
        if(resultLabel != "unknown"){
            // resultScore = 1 - candidateDist / Constants.halfDiagonal;
            resultScore = candidateDist > 1.0 ? 1.0 / candidateDist : 1.0;
            // Utils.logStr(TAG, "Candidate label: " + candidateGesture.getLabel());
            // Utils.logStr(TAG, "Testing result label: " + resultLabel);
            if(resultLabel.equals(candidateGesture.getLabel())){
                isCorrect = 1;
                // Utils.logStr(TAG, "Correct or incorrect: " + isCorrect);
            }
            Collections.sort(nBestList, new Comparator<NBestListItem>() {
                @Override
                public int compare(NBestListItem item1, NBestListItem item2) {
                    return Double.compare(item2.getScore(), item1.getScore());
                }
            });
            for(NBestListItem item : nBestList){
                nBestListStr.add(item.getVerboseInfo());
                nBestListStr.add(String.format("%.2f", item.getScore()));
            }
        }else{
            Utils.logStr(TAG, "Warning: unknown gesture");
        }
        return new RecognitionResult(verboseTrainingSet, resultLabel, resultScore, isCorrect, nBestListStr);
    }

    private double greedyCloudMatch(Point[] points, PointCloud template){
        // var e = 0.50;
        // var step = Math.floor(Math.pow(points.length, 1.0 - e));
        // var min = +Infinity;
        // for (var i = 0; i < points.length; i += step) {
        //     var d1 = CloudDistance(points, P.Points, i);
        //     var d2 = CloudDistance(P.Points, points, i);
        //     min = Math.min(min, Math.min(d1, d2)); // min3
        // }
        // return min;
        int n = points.length;
        double epsilon = 0.5;
        double step = Math.floor(Math.pow(n, 1.0 - epsilon));
        double dist = Integer.MAX_VALUE;
        for(int i = 0; i < n; i += step){
            double dist1 = getCloudDistance(points, template.getPoints(), i);
            double dist2 = getCloudDistance(template.getPoints(), points, i);
            dist = Math.min(dist, Math.min(dist1, dist2));
        }
        return dist;
    } 

    private double getCloudDistance(Point[] pts1, Point[] pts2, int start){
        // var matched = new Array(pts1.length); // pts1.length == pts2.length
        // for (var k = 0; k < pts1.length; k++)
        //     matched[k] = false;
        // var sum = 0;
        // var i = start;
        // do
        // {
        //     var index = -1;
        //     var min = +Infinity;
        //     for (var j = 0; j < matched.length; j++)
        //     {
        //         if (!matched[j]) {
        //             var d = Distance(pts1[i], pts2[j]); // Euclidean distance between two points
        //             if (d < min) {
        //                 min = d;
        //                 index = j;
        //             }
        //         }
        //     }
        //     matched[index] = true;
        //     var weight = 1 - ((i - start + pts1.length) % pts1.length) / pts1.length;
        //     sum += weight * min;
        //     i = (i + 1) % pts1.length;
        // } while (i != start);
        // return sum;
        int n = pts1.length; // pts1.length == pts2.length
        boolean[] matched = new boolean[n];
        for(int i = 0; i < n; i++){
            matched[i] = false;
        }
        double s = 0;
        int idx = start;
        do{
            int index = -1;
            double dist = Integer.MAX_VALUE;
            for(int i = 0; i < n; i++){
                if(!matched[i]){
                    double d = Utils.getEuclideanDistance(pts1[idx], pts2[i]);
                    if(d < dist){
                        dist = d;
                        index = i;
                    }
                }
            }
            matched[index] = true; //index should not be -1 anymore
            double weight = 1 - ((idx - start + n) % n) / n;
            s += weight * dist;
            idx = (idx + 1) % n;
        }while(idx != start);
        return s;
    }

    public int getTrainingSetSize(){
        return this.trainingTemplates.size();
    }
}

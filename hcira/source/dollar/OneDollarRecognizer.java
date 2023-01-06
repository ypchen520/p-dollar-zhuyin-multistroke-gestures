// package dollar;

// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.Comparator;
// import java.util.List;

// public class OneDollarRecognizer {
//     private static final String TAG = "[OneDollarRecognizer]";
//     private List<Gesture> trainingTemplates;
//     public OneDollarRecognizer(){
//         this.trainingTemplates = new ArrayList<>();
//     }
//     public void addTrainingTemplate(Gesture gesture){
//         this.trainingTemplates.add(gesture);
//         // Utils.logStr(TAG, "Added one gesture, current training set size: " + this.trainingTemplates.size());
//     }

//     public RecognitionResult recognize(Gesture candidateGesture){
//         double candidateDist = Integer.MAX_VALUE;
//         String resultLabel = "unknown";
//         double resultScore = 0;
//         int isCorrect = 0;
//         List<String> verboseTrainingSet = new ArrayList<>();
//         List<NBestListItem> nBestList = new ArrayList<>();
//         List<String> nBestListStr = new ArrayList<>();
//         for(int i = 0; i < this.trainingTemplates.size(); i++){
//             Gesture template = this.trainingTemplates.get(i);
//             String verboseInfo = template.getVerboseInfo();
//             // if(this.trainingTemplates.size() == 16){
//             //     Utils.logStr(TAG, verboseInfo);
//             // }
//             double dist = distAtBestAng(candidateGesture.getPoints(), template, -1 * Constants.angleRange, Constants.angleRange, Constants.anglePrecision);
//             double score = 1 - dist / Constants.halfDiagonal;
//             nBestList.add(new NBestListItem(score, verboseInfo)); 
//             verboseTrainingSet.add(verboseInfo);
//             // Utils.logStr(TAG, "Testing dist: " + dist);
//             // Utils.logStr(TAG, "Testing label: " + template.getLabel());
//             if(dist < candidateDist){
//                 candidateDist = dist;
//                 resultLabel = template.getLabel();
//             }
//         }
//         // Utils.logStr(TAG, "Testing result dist: " + candidateDist);
//         if(resultLabel != "unknown"){
//             resultScore = 1 - candidateDist / Constants.halfDiagonal;
//             // Utils.logStr(TAG, "Candidate label: " + candidateGesture.getLabel());
//             // Utils.logStr(TAG, "Testing result label: " + resultLabel);
//             if(resultLabel.equals(candidateGesture.getLabel())){
//                 isCorrect = 1;
//                 // Utils.logStr(TAG, "Correct or incorrect: " + isCorrect);
//             }
//             Collections.sort(nBestList, new Comparator<NBestListItem>() {
//                 @Override
//                 public int compare(NBestListItem item1, NBestListItem item2) {
//                     return Double.compare(item2.getScore(), item1.getScore());
//                 }
//             });
//             for(NBestListItem item : nBestList){
//                 nBestListStr.add(item.getVerboseInfo());
//                 nBestListStr.add(String.format("%.2f", item.getScore()));
//             }
//         }else{
//             Utils.logStr(TAG, "Warning: unknown gesture");
//         }
//         return new RecognitionResult(verboseTrainingSet, resultLabel, resultScore, isCorrect, nBestListStr);
//     }

//     // Recognition functions
//     private double distAtBestAng(Point[] points, Gesture template, double angleFrom, double angleTo, double angleThreshold){
//         double rad1 = Constants.phi * angleFrom + (1-Constants.phi) * angleTo;
//         double rad2 = (1-Constants.phi) * angleFrom + Constants.phi * angleTo;
//         double dist1 = distAtAng(points, template, rad1);
//         double dist2 = distAtAng(points, template, rad2);
//         while(Math.abs(angleTo - angleFrom) > angleThreshold){
//             if(dist1 < dist2){
//                 angleTo = rad2;
//                 rad2 = rad1;
//                 dist2 = dist1;
//                 rad1 = Constants.phi * angleFrom + (1-Constants.phi) * angleTo;
//                 dist1 = this.distAtAng(points, template, rad1);
//             }else{
//                 angleFrom = rad1;
//                 rad1 = rad2;
//                 dist1 = dist2;
//                 rad2 = (1-Constants.phi) * angleFrom + Constants.phi * angleTo;
//                 dist2 = this.distAtAng(points, template, rad2);
//             }
//         }
//         return Math.min(dist1, dist2);
//     }

//     private double distAtAng(Point[] points, Gesture template, double radians){
//         Point[] newPoints = Utils.rotateBy(points, radians);
//         return Utils.getPathDistance(newPoints, template.getPoints());
//     }

//     public int getTrainingSetSize(){
//         return this.trainingTemplates.size();
//     }
// }

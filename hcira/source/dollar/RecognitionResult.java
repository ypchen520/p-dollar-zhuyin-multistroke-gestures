package dollar;

import java.util.ArrayList;
import java.util.List;

public class RecognitionResult {
    private static final String TAG = "[RecognitionResult]";
    private List<String> trainingSet;
    private String label;
    private int isCorrect;
    private double score;
    private List<String> nBestList;

    public RecognitionResult(List<String> trainingSet, String label, double score, int isCorrect, List<String> nBList){
        this.trainingSet = trainingSet;
        this.label = label;
        this.isCorrect = isCorrect;
        this.score = score;
        this.nBestList = nBList;
        // Utils.logStr(TAG, "Training set verbose string: " + this.trainingSet);
        // Utils.logStr(TAG, "N-Best list verbose string: " + this.nBestList);
    }

    public String getLabel(){
        return label;
    }

    public double getScore(){
        return score;
    }

    public List<String> getNBestList(){
        return nBestList;
    }

    public String getTrainingSetVerboseInfo(){
        return Utils.listOrArrayToString(trainingSet);
    }

    public String getNBestListVerboseInfo(){
        return Utils.listOrArrayToString(nBestList);
    }

    public int getIsCorrect(){
        return isCorrect;
    }
}

package dollar;

public class Constants {
    // public static final int numUsers = 6;
    // public static final int numUsers = 20;
    public static final int numUsers = 12;
    // public static final int numUsers = 10;
    public static final int numGesturesPerType = 10;
    // public static final int numGestureTypes = 16;
    // public static final int numSamplePoints = 64;
    public static final int numPoints = 32;
    public static final int numPointClouds = 16;
    public static final double squareSize = 250;
    // public static final double halfDiagonal = 0.5 * Math.sqrt(2 * squareSize * squareSize);
    public static final Point origin = new Point(0,0,0,0);
    public static final double angleRange = Utils.degreeToRadians(45); //based on the pseudocode in the $1 paper
    public static final double anglePrecision = Utils.degreeToRadians(2); //based on the pseudocode in the $1 paper
    public static final double phi = 0.5 * (-1.0 + Math.sqrt(5)); //golden ratio
    public static final int numRandomIterations = 100;
    // public static final String firstLogLine = 
    //     "Recognition Log: Yu-Peng Chen // $P // Zhuyin Multistroke Gestures // USER-DEPENDENT RANDOM-100\n";
    public static final String firstLogLine = 
        "Recognition Log: Yu-Peng Chen // $P // MMG // USER-DEPENDENT RANDOM-100\n";
    public static final String columnNames = String.join(",", 
        new String[]{"User[all-users]", "GestureType[all-gestures-types]", "RandomIteration[1to100]", "#ofTrainingExamples[E]", 
        "TotalSizeOfTrainingSet[count]", "TrainingSetContents[specific-gesture-instances]", "Candidate[specific-instance]",
        "RecoResultGestureType[what-was-recognized]", "CorrectIncorrect[1or0]", "RecoResultScore", 
        "RecoResultBestMatch[specific-instance]","RecoResultNBestSorted[instance-and-score]"}) + "\n";
    public static final int panelSize = 500;
    public static final int windowSize = 1000;
    public static final String appDescription = 
        "- Follow the instruction to make strokes on the canvas below. <br/>- Examples are shown on the right. <br/>- Click \"Submit\" when you're done.<br/>- Click \"Clear\" and try again if you're not happy with the sample.<br/>";
    public static final String appDescriptionLiveDemo = 
        "- Make strokes on the canvas below. <br/>- Examples are shown on the right. <br/>- Click \"Recognize\" to recognize the gesture.<br/>- Click \"Clear\" to clear the canvas and draw another gesture.<br/>";
}

package dollar;

public class NBestListItem {
    private double score;
    private String verboseGestureInfo;
    public NBestListItem(double score, String info){
        this.score = score;
        this.verboseGestureInfo = info;
    }
    
    public double getScore(){
        return score;
    }

    public String getVerboseInfo(){
        return verboseGestureInfo;
    }
}

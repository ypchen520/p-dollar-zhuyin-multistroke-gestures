package dollar;
import java.util.ArrayList;
// import java.util.LinkedList;
import java.util.List;

public class PointCloud {
    
    private static final String TAG = "[PointCloud]";
    private List<Point> rawPoints;
    private Point[] processedPoints;
    private String label; // Name
    private int index;
    private String userId;
	
    public PointCloud(String label, int index, String uid){
        this.rawPoints = new ArrayList<Point>();
        this.label = label;
        this.index = index;
        this.userId = uid;
        // this.Points = Resample(points, NumPoints);
        // this.Points = Scale(this.Points);
        // this.Points = TranslateTo(this.Points, Origin);
    }

    public void addRawPoint(double x, double y, int index, long ts){
        this.rawPoints.add(new Point(x,y,index,ts));
    }

    public List<Point> getRawPoints(){
        return rawPoints;
    }

    public Point[] getPoints(){
        return processedPoints;
    }

    public String getLabel(){
        return label;
    }

    public int getIdx(){
        return index;
    }

    public String getUserId(){
        return userId;
    }

    public String getVerboseInfo(){
        return String.join("-", userId, label, String.valueOf(index));
    }

    public void clearRawPoints(){
        rawPoints.clear();
    }

    public void preprocessRawPoints(){
        // Point[] tempPts = rawPoints.toArray(new Point[rawPoints.size()]);
        List<Point> resampledPoints = Preprocessing.resample(rawPoints);
        // Utils.logStr(TAG, "Length of resampled points: " + resampledPoints.size());
        if(resampledPoints.size() != Constants.numPoints){
            Utils.logStr(TAG, "Warning: length of resampled points is not " + Constants.numPoints);
        }
        Point[] tempPts = resampledPoints.toArray(new Point[resampledPoints.size()]);
        // Point[] rotatedPts = Preprocessing.rotateToZero(tempPts);
        // Utils.logStr(TAG, "rotated point: (" + rotatedPts[0].getX() + "," + rotatedPts[0].getY() + ")");
        // Point[] scaledPts = Preprocessing.scaleToSquare(rotatedPts, Constants.squareSize);
        Point[] scaledPts = Preprocessing.scaleToSquare(tempPts);
        // Utils.logStr(TAG, "scaled point: (" + scaledPts[0].getX() + "," + scaledPts[0].getY() + ")");        
        this.processedPoints = Preprocessing.translateToOrigin(scaledPts, Constants.origin);
        // Utils.logStr(TAG, "preprocessed point: (" + this.processedPoints[0].getX() + "," + this.processedPoints[0].getY() + ")");        
    }
}

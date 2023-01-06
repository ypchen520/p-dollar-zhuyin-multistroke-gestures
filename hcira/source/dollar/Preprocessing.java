package dollar;

import java.util.ArrayList;
import java.util.List;

public class Preprocessing {
    private static final String TAG = "[Preprocessing]";
    public static List<Point> resample(List<Point> points){
        // var I = PathLength(points) / (n - 1); // interval length
        // var D = 0.0;
        // var newpoints = new Array(points[0]);
        // for (var i = 1; i < points.length; i++)
        // {
        //     if (points[i].ID == points[i-1].ID)
        //     {
        //         var d = Distance(points[i-1], points[i]);
        //         if ((D + d) >= I)
        //         {
        //             var qx = points[i-1].X + ((I - D) / d) * (points[i].X - points[i-1].X);
        //             var qy = points[i-1].Y + ((I - D) / d) * (points[i].Y - points[i-1].Y);
        //             var q = new Point(qx, qy, points[i].ID);
        //             newpoints[newpoints.length] = q; // append new point 'q'
        //             points.splice(i, 0, q); // insert 'q' at position i in points s.t. 'q' will be the next i
        //             D = 0.0;
        //         }
        //         else D += d;
        //     }
        // }
        // if (newpoints.length == n - 1) // sometimes we fall a rounding-error short of adding the last point, so add it if so
        //     newpoints[newpoints.length] = new Point(points[points.length - 1].X, points[points.length - 1].Y, points[points.length - 1].ID);
        // return newpoints;
        List<Point> newPoints = new ArrayList<Point>();
        Point[] tempPts = points.toArray(new Point[points.size()]);
        // newPoints[0] = new Point(points[0].getX(), points[0].getY());
        newPoints.add(new Point(points.get(0).getX(), points.get(0).getY(), points.get(0).getStrokeID(), 0));
        double intervalLen = Utils.getPathLength(tempPts) / (Constants.numPoints - 1);
        double traveledDist = 0;

        for(int i = 1; i < points.size(); i++){
            Point p0 = points.get(i-1);
            Point p1 = points.get(i);
            if(p0.getStrokeID() == p1.getStrokeID()){
                double currDist = Utils.getEuclideanDistance(p0, p1);
                if(traveledDist + currDist > intervalLen){
                    double ratio = (intervalLen - traveledDist) / currDist;
                    // int newX = p0.getX() + (int) Math.round((p1.getX() - p0.getX()) * ratio);
                    // int newY = p0.getY() + (int) Math.round((p1.getY() - p0.getY()) * ratio);
                    double newX = p0.getX() + (p1.getX() - p0.getX()) * ratio;
                    double newY = p0.getY() + (p1.getY() - p0.getY()) * ratio;
                    Point resampleP = new Point(newX, newY, p1.getStrokeID(), 0);
                    Point newP = new Point(newX, newY, p1.getStrokeID(), 0);
                    points.add(i, resampleP);
                    newPoints.add(newP);
                    // Utils.logStr(TAG, "pushed to newPoints at: " + i);
                    // Utils.logStr(TAG, "current points length: " + points.size());
                    traveledDist = 0;
                }else{
                    traveledDist += currDist;
                }
            }
        }
        if(newPoints.size() == Constants.numPoints - 1){ 
            // missing the last point
            newPoints.add(new Point(points.get(points.size()-1).getX(), points.get(points.size()-1).getY(), points.get(points.size()-1).getStrokeID(), 0));
        }
        return newPoints;
    }

    // public static Point[] rotateToZero(Point[] points){
    //     Point cen = Utils.getCentroid(points);
    //     // Utils.logStr(TAG, "Centroid: (" + cen.getX() + "," + cen.getY() + ")");
    //     double indicativeAngle = Math.atan2(cen.getY() - points[0].getY(), cen.getX() - points[0].getX());
    //     // Utils.logStr(TAG, "Indicative angle: " + indicativeAngle);
    //     return Utils.rotateBy(points, -1 * indicativeAngle);
    // }

    // public static Point[] scaleToSquare(Point[] points, double size){
    public static Point[] scaleToSquare(Point[] points){
        // var minX = +Infinity, maxX = -Infinity, minY = +Infinity, maxY = -Infinity;
        // for (var i = 0; i < points.length; i++) {
        //     minX = Math.min(minX, points[i].X);
        //     minY = Math.min(minY, points[i].Y);
        //     maxX = Math.max(maxX, points[i].X);
        //     maxY = Math.max(maxY, points[i].Y);
        // }
        // var size = Math.max(maxX - minX, maxY - minY);
        // var newpoints = new Array();
        // for (var i = 0; i < points.length; i++) {
        //     var qx = (points[i].X - minX) / size;
        //     var qy = (points[i].Y - minY) / size;
        //     newpoints[newpoints.length] = new Point(qx, qy, points[i].ID);
        // }
        // return newpoints;
        // Point[] newPoints = new Point[Constants.numSamplePoints];
        // Box b = Utils.getBoundingBox(points);
        double minX = Integer.MAX_VALUE;
        double maxX = Integer.MIN_VALUE;
        double minY = Integer.MAX_VALUE;
        double maxY = Integer.MIN_VALUE;
        for(int i = 0; i < points.length; i++){
            minX = Math.min(minX, points[i].getX());
            maxX = Math.max(maxX, points[i].getX());
            minY = Math.min(minY, points[i].getY());
            maxY = Math.max(maxY, points[i].getY());
        }
        double size = Math.max(maxX - minX, maxY - minY);
        Point[] newPoints = new Point[Constants.numPoints]; // after resampling, points.length == Constants.numPoints
        for(int i = 0; i < points.length; i++){
            double newX = (points[i].getX() - minX) / size;
            double newY = (points[i].getY() - minY) / size;
            newPoints[i] = new Point(newX, newY, points[i].getStrokeID(), 0);
        }
        return newPoints;
    }

    public static Point[] translateToOrigin(Point[] points, Point pt){
        // var c = Centroid(points);
        // var newpoints = new Array();
        // for (var i = 0; i < points.length; i++) {
        //     var qx = points[i].X + pt.X - c.X;
        //     var qy = points[i].Y + pt.Y - c.Y;
        //     newpoints[newpoints.length] = new Point(qx, qy, points[i].ID);
        // }
        // return newpoints;
        
        // translates the centroid to origin
        // Point[] newPoints = new Point[Constants.numSamplePoints];
        Point[] newPoints = new Point[Constants.numPoints]; // after resampling, points.length == Constants.numPoints
        Point cen = Utils.getCentroid(points);
        // Utils.logStr(TAG, "centroid x: " + cen.getX());
        // Utils.logStr(TAG, "centroid y: " + cen.getY());
        for(int i = 0; i < points.length; i++){
            double newX = points[i].getX() - (cen.getX() - pt.getX());
            double newY = points[i].getY() - (cen.getY() - pt.getY());
            newPoints[i] = new Point(newX, newY, points[i].getStrokeID(), 0);
        }
        return newPoints;
    }
}

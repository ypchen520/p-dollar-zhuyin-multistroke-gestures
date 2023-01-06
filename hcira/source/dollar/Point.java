package dollar;

public class Point {
    // private int X;
    // private int Y;
    private double X;
    private double Y;
    // $P
    private int strokeID;
    private long timestamp;
    public Point(double x, double y, int id, long ts){
        this.X = x;
        this.Y = y;
        this.strokeID = id;
        this.timestamp = ts;
    }
    public double getX(){
        return X;
    }

    public double getY(){
        return Y;
    }

    public int getStrokeID(){
        return strokeID;
    }

    public long getTimestamp(){
        return timestamp;
    }
}

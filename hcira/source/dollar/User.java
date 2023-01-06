package dollar;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String id;
    private Map<String, PointCloud[]> gestures;
    public User(String id){
        this.gestures = new HashMap<>();
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void addGesture(PointCloud gesture, String type, int index){
        // index: 1-based
        if(this.gestures.containsKey(type)){
            this.gestures.get(type)[index-1] = gesture;
        }else{
            this.gestures.put(type, new PointCloud[Constants.numGesturesPerType]);
            this.gestures.get(type)[index-1] = gesture;
        }
    }

    public Map<String, PointCloud[]> getGestures(){
        return gestures;
    }
}

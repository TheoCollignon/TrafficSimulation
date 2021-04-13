package Model;

import java.util.ArrayList;
import java.util.List;

public class Crossroad {

    List<Crossroad> joinedCrossroads;
    Coordinate coords;

    public Crossroad (Coordinate coordinates){
        coords = coordinates;
    }

    public void addJoinedCrossroad(Crossroad crossroad){
        joinedCrossroads.add(crossroad);
    }

    public boolean isCrossroadJoined(Crossroad crossroad){
        return( joinedCrossroads.contains(crossroad));
    }
}

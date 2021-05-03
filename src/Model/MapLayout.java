package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapLayout {

    List<List<Crossroad>> listCrossroads;
    List<List<Road>> listPossiblePaths;
    Random random;

    public MapLayout(int mapSize){
        random = new Random();
        listPossiblePaths = new ArrayList<>();
        listCrossroads = new ArrayList<>();
        for (int i = 0; i < mapSize; i++){
            List<Crossroad> tempList = new ArrayList<>();
            for(int j = 0; j < mapSize; j ++){
                tempList.add(null);
            }
            listCrossroads.add(tempList);
        }
    }

    public void setCrossroad(int coordX, int coordY, Crossroad cross){
        listCrossroads.get(coordX).set(coordY, cross);
    }

    //returns a path from one city to another (with some randomness if several paths have the same length)
    public List<Road> getPath(City startingCity, City endCity){
        List<Road> finalList = new ArrayList<>();

        return finalList;
    }

    public Road getRoadBetweenTwoCrossroads(Crossroad firstCross, Crossroad secondCross){
        List<Road> listRoads = firstCross.getJoinedRoads();
        for (Road r : listRoads){
            if(secondCross.getJoinedRoads().contains(r)){
                return r;
            }
        }
        return null;
    }

    public List<List<Crossroad>> getListCrossroads() {
        return listCrossroads;
    }
}

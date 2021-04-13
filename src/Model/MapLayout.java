package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapLayout {

    List<List<Coordinate>> listBranches;
    Random random;

    public MapLayout(int mapSize){
        random = new Random();
        listBranches = new ArrayList<>();
        for (int i = 0; i < mapSize; i++){
            List<Coordinate> tempList = new ArrayList<>();
            for(int j = 0; j < mapSize; j ++){
                tempList.add(null);
            }
            listBranches.add(tempList);
        }
    }

    public void setBranch(int coordX, int coordY, Coordinate coordBranch){
        listBranches.get(coordX).set(coordY, coordBranch);
    }

    //returns a path from one city to another (with some randomness if several paths have the same length)
    public List<Crossroad> getPath(City startingCity, City endCity){
        List<Crossroad> finalList = new ArrayList<>();
        return finalList;
    }
}

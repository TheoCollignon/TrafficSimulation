package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class MapLayout {

    List<List<Crossroad>> listCrossroads;
    List<List<Road>> listPossiblePaths;
    Random random;
    double minimalWeight;
    List<Road> bestPath;

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

    //djikstra initialization
    public List<Road> getPathBetweenTwoCities(City A, City B){
        this.minimalWeight = 1000000;
        this.bestPath = new ArrayList<>();
        Crossroad crossStart = A.getCrossRoad();
        Crossroad crossEnd = B.getCrossRoad();
        List<Road> path = new ArrayList<>();

        nextCrossroad(crossStart, crossEnd, path);
        return bestPath;
    }

    //recurrent function
    void nextCrossroad(Crossroad cross, Crossroad goal, List<Road> pathUntilNow){

        if(cross.equals(goal)){
            if(getPathWeight(pathUntilNow) < minimalWeight){
                minimalWeight = getPathWeight(pathUntilNow);
                bestPath.clear();
                this.bestPath.addAll(pathUntilNow);
            }
            return;
        }
        List<Road> possibleRoads = cross.getJoinedRoads();
        for (Road road : possibleRoads){
            if(!pathUntilNow.contains(road)){
                pathUntilNow.add(road);
                Crossroad next;
                if(road.getCrossroadStart().equals(cross)){
                    next = road.getCrossroadEnd();
                } else {
                    next = road.getCrossroadStart();
                }
                nextCrossroad(next,goal,pathUntilNow);
                pathUntilNow.remove(road);
            }
        }
    }

    double getPathWeight(List<Road> path){
        double weight = 0;
        for(Road r : path){
            weight += r.getRoadLength();
        }
        return weight;
    }

    public List<List<Crossroad>> getListCrossroads() {
        return listCrossroads;
    }
}

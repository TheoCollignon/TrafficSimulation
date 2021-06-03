package io.sarl.template.javafx.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class MapLayout {

    List<List<Crossroad>> listCrossroads;
    List<List<Road>> listPossiblePaths;
    List<City> sortedCitiesX;
    List<City> sortedCitiesY;
    Random random;
    double minimalWeight;
    List<Road> bestPath;
    Configuration config;
    City origin;
    boolean hasBestPathBeenFound;

    public MapLayout(int mapSize, Configuration conf){
        random = new Random();
        config = conf;
        listPossiblePaths = new ArrayList<>();
        listCrossroads = new ArrayList<>();
        sortedCitiesX = new ArrayList<>();
        sortedCitiesY = new ArrayList<>();

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

    //returns a path from one city to another
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
    	origin = A;
    	hasBestPathBeenFound = false;
        this.minimalWeight = 1000000;
        this.bestPath = new ArrayList<>();
        Crossroad crossStart = A.getCrossRoad();
        Crossroad crossEnd = B.getCrossRoad();
        List<Road> path = new ArrayList<>();
        //starting the recursive method
        nextCrossroad(crossStart, crossEnd, path);
        return bestPath;
    }

    //recursive function
    void nextCrossroad(Crossroad currentCross, Crossroad goal, List<Road> pathUntilNow){
    	if(hasBestPathBeenFound) {
    		return;
    	}
        if(currentCross.equals(goal)){
            if(getPathWeight(pathUntilNow) < minimalWeight){
                minimalWeight = getPathWeight(pathUntilNow);
                bestPath.clear();
                this.bestPath.addAll(pathUntilNow);
                if(pathUntilNow.size() == getDistance(origin, goal.getCity())) {
                	hasBestPathBeenFound = true;
                } /*else { // use for debug
                	System.out.println("Nope : " + pathUntilNow.size() + " instead of " + getDistance(origin, goal.getCity()));
                }*/
            }
            return;
        }
        List<Road> possibleRoads = currentCross.getJoinedRoads();
        for (Road road : possibleRoads){
            if(!pathUntilNow.contains(road)){
                pathUntilNow.add(road);
                Crossroad next;
                if(road.getCrossroadStart().equals(currentCross)){
                    next = road.getCrossroadEnd();
                } else {
                    next = road.getCrossroadStart();
                }
                nextCrossroad(next,goal,pathUntilNow);
                pathUntilNow.remove(road);
            }
        }
    }
    
    //Calculates the logically minimum distance between two cities in a hippodamian config
    int getDistance(City cityA, City cityB) {
    	//if the config isn't hippodamian, we return a 0 value so the djikstra doesn't stop
    	if(sortedCitiesX.size() == 0) {
    		return 0;
    	}
    	//if it is hippodamian, we check the distance between the cities in the sorted lists (by position)
    	int minimumDistance = 0;
    	minimumDistance += Math.abs(sortedCitiesX.indexOf(cityA) - sortedCitiesX.indexOf(cityB));
    	minimumDistance += Math.abs(sortedCitiesY.indexOf(cityA) - sortedCitiesY.indexOf(cityB));
    	return minimumDistance;
    }

    double getPathWeight(List<Road> path){
        double weight = 0;
        for(Road r : path){
            weight += r.getRoadLength();
        }
        return weight;
    }
    
    //Sorts the cities by their coordinates in X and Y
    public void sortCities(float[] coordsX, float[] coordsY) {
    	int iterator = 0;
    	for(City city : config.getCities()) {
    		if(city.getPosition().getX() == coordsX[iterator]) {
    			sortedCitiesX.add(city);
    		}
    		if(city.getPosition().getY() == coordsY[iterator]) {
    			sortedCitiesY.add(city);
    		}
    		iterator ++;
    	}
    }

    public List<List<Crossroad>> getListCrossroads() {
        return listCrossroads;
    }
}

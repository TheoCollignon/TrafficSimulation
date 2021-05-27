package io.sarl.template.javafx.Model;

import java.util.ArrayList;
import java.util.List;

public class Crossroad {

    List<Road> joinedRoads;
    Coordinate coords;
    public boolean isCity;
    City city;

    public Crossroad (Coordinate coordinates){
        coords = coordinates;
        joinedRoads = new ArrayList<>();
    }

    public void addJoinedRoad(Road road){
        joinedRoads.add(road);
    }

    public boolean isRoadJoined(Road road){
        return( joinedRoads.contains(road));
    }

    public List<Road> getJoinedRoads() {
        return joinedRoads;
    }

    public void setJoinedRoads(List<Road> joinedRoads) {
        this.joinedRoads = joinedRoads;
    }

    public Coordinate getCoords() {
        return coords;
    }

    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

    public boolean isCity() {
        return isCity;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}

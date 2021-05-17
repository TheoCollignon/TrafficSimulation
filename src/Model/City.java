package Model;

import java.util.ArrayList;

public class City {
    private Coordinate position;
    Crossroad crossRoad;
    private int size;
    private String name;
    private ArrayList<City> connectedCities;
    private ArrayList<Road> connectedRoad; // I dont like this solution, to change later

    public City(Coordinate position, int size, String name) {
        crossRoad = new Crossroad(position);
        this.position = position;
        this.size = size;
        this.name = name;
        this.connectedCities = new ArrayList<>();
        this.connectedRoad = new ArrayList<>();
    }

    public void addConnectedCity(City city) {
        connectedCities.add(city);
    }

    public void addConnectedRoad(Road road) {
        connectedRoad.add(road);
    }


    public Coordinate getPosition() {
        return position;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public ArrayList<City> getConnectedCities() {
        return connectedCities;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConnectedCities(ArrayList<City> connectedCities) {
        this.connectedCities = connectedCities;
    }

    public ArrayList<Road> getConnectedRoad() {
        return connectedRoad;
    }

    public void setConnectedRoad(ArrayList<Road> connectedRoad) {
        this.connectedRoad = connectedRoad;
    }

    public Crossroad getCrossRoad() {
        return crossRoad;
    }

    public void setCrossRoad(Crossroad crossRoad) {
        this.crossRoad = crossRoad;
    }
}

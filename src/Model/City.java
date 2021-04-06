package Model;

import java.util.ArrayList;

public class City {
    private Coordinate position;
    private int size;
    private String name;
    private ArrayList<City> connectedCities;

    public City(Coordinate position, int size, String name) {
        this.position = position;
        this.size = size;
        this.name = name;
        this.connectedCities = new ArrayList<>();
    }

    public void addConnectedCity(City city) {
        connectedCities.add(city);
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
}

package Model;

import java.util.ArrayList;

public class Configuration {
    private ArrayList<City> cities;
    private ArrayList<Road> roads;

    public Configuration() {
        cities = new ArrayList<>();
        roads = new ArrayList<>();
    }

    public void addCity(City city) {
        cities.add(city);
    }

    public void addCity(int[] position, int size, String name) {
        City city = new City(position, size, name);
        cities.add(city);
    }

    public void addRoad(City A, City B) {
        Road road = new Road(A,B);
        roads.add(road);
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }
}

package Model;

import View.ViewGenerator;

import java.util.ArrayList;
import java.util.Random;

public class Configuration {
    private ArrayList<City> cities;
    private ArrayList<Road> roads;
    private ArrayList<TrafficLight> trafficLights;
    private ViewGenerator viewGenerator;
    private Random random = new Random();

    public Configuration(ViewGenerator viewGenerator) {
        this.viewGenerator = viewGenerator;
        cities = new ArrayList<>();
        roads = new ArrayList<>();
        trafficLights = new ArrayList<>();
    }

    public void addRandomElements(int nbCities) {
        for (int i = 0; i < nbCities; i++) {
            int x = random.nextInt(600) + 1;
            int y = random.nextInt(600) + 1;
            int size = random.nextInt(25) + 15;
            String name = String.valueOf(i+1);
            cities.add(new City(new int[]{x,y}, size, name));
        }
        for (int i = 0; i < nbCities; i++) {
            for (int j = i+1; j < nbCities; j++) {
                boolean isRoad = random.nextBoolean();
                if (isRoad) {
                    //int width = random.nextInt(5) + 10;
                    roads.add(new Road(cities.get(i), cities.get(j)));
                }
            }
        }
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

    public void addTrafficLight() {
        trafficLights.add(new TrafficLight(viewGenerator));
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public ArrayList<TrafficLight> getTrafficLights() {
        return trafficLights;
    }
}

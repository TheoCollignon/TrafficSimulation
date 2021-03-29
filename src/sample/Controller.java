package sample;

import java.util.ArrayList;

public class Controller {
    private static Controller instance = null;
    private ArrayList<City> cities;
    private ArrayList<Road> roads;

    public static Controller getInstance() {
        if (null == instance) {
            instance = new Controller();
        }
        return instance;
    }

    public void init() {
        TrafficLight light = new TrafficLight();
        light.start();
        cities = new ArrayList<>();
        roads = new ArrayList<>();
        City A = new City(new int[]{100,100}, 20, "A");
        City B = new City(new int[]{300,500}, 40, "B");
        City C = new City(new int[]{400,400}, 20, "C");
        cities.add(A);
        cities.add(B);
        cities.add(C);
        Road road1 = new Road(A, B);
        road1.addCoordinates(new int[]{200,200});
        road1.addCoordinates(new int[]{80,150});
        Road road2 = new Road(B, C);
        roads.add(road1);
        roads.add(road2);
        System.out.println(cities);
    }

    public void setViewStart(ViewGenerator viewGenerator) {
        System.out.println(cities);
        viewGenerator.drawElements(cities, roads);
    }
}

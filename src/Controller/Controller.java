package Controller;

import Model.City;
import Model.Configuration;
import Model.TrafficLight;
import View.ViewGenerator;

public class Controller {
    private static Controller instance = null;
    private static Configuration configuration = null;


    public static Controller getInstance() {
        if (null == instance) {
            instance = new Controller();
        }
        return instance;
    }

    public void init() {
        TrafficLight light = new TrafficLight();
        light.start();
        configuration = new Configuration();
        City A = new City(new int[]{100,100}, 20, "A");
        City B = new City(new int[]{300,500}, 40, "B");
        City C = new City(new int[]{400,400}, 20, "C");
        configuration.addCity(A);
        configuration.addCity(B);
        configuration.addCity(C);
        configuration.addRoad(A,B);
        configuration.addRoad(B,C);
    }

    public void setViewStart(ViewGenerator viewGenerator) {
        viewGenerator.drawElements(configuration.getCities(), configuration.getRoads());
    }
}

package sample;

import java.util.ArrayList;

public class Controller {
    private static Controller instance = null;
    private ArrayList<Road> roadsList;

    public static Controller getInstance() {
        if (null == instance) {
            instance = new Controller();
        }
        return instance;
    }

    public void init() {
        TrafficLight light = new TrafficLight();
        light.start();
        Road road1 = new Road(new int[]{10,10}, new int[]{150,10});
        roadsList = new ArrayList<>();
        roadsList.add(road1);
    }

    public void setViewStart(ViewGenerator viewGenerator) {
        viewGenerator.drawRoads(roadsList);
    }
}

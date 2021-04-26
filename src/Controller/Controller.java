package Controller;

import Model.Car;
import Model.Configuration;
import Model.JSONManager;
import View.ViewGenerator;

import java.util.ArrayList;

public class Controller {
    private static Controller instance = null;
    private Configuration configuration = null;
    private ViewGenerator viewGenerator = null;
    private JSONManager jsonManager = null;

    public static Controller getInstance() {
        if (null == instance) {
            instance = new Controller();
        }
        return instance;
    }

    public void createConfiguration() {
        /*configuration = new Configuration(viewGenerator);
        City A = new City(new int[]{100,100}, 20, "A");
        City B = new City(new int[]{300,500}, 40, "B");
        City C = new City(new int[]{400,400}, 20, "C");
        configuration.addCity(A);
        configuration.addCity(B);
        configuration.addCity(C);
        configuration.addRoad(A,B);
        configuration.addRoad(B,C);
        configuration.addTrafficLight();*/
        configuration = new Configuration(viewGenerator);
        jsonManager = new JSONManager();
        configuration.addRandomElements(5);
        ArrayList<Car> cars = configuration.setupCars(5);
        CarManager cm = new CarManager(cars);
        viewGenerator.updateView(cars);
        jsonManager.saveJSONFile(configuration, "test");
        jsonManager.readJSONFile("test");
    }

    public void loadConfiguration(ViewGenerator viewGenerator) {
        this.viewGenerator = viewGenerator;
        createConfiguration();
        viewGenerator.drawConfiguration(configuration);


    }
}

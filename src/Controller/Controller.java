package Controller;

import Model.Configuration;
import Model.JSONParser;
import View.ViewGenerator;

public class Controller {
    private static Controller instance = null;
    private Configuration configuration = null;
    private ViewGenerator viewGenerator = null;
    private JSONParser jsonParser = null;

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
        jsonParser = new JSONParser();
        configuration.addRandomElements(5);
        configuration.setupCars(5);
        jsonParser.saveJSONFile(configuration, "test");
    }

    public void loadConfiguration(ViewGenerator viewGenerator) {
        this.viewGenerator = viewGenerator;
        createConfiguration();
        viewGenerator.drawConfiguration(configuration);
    }
}

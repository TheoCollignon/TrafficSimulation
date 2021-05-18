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
        jsonManager = new JSONManager();
        configuration = new Configuration(viewGenerator);
        configuration.addRandomElements(5);
        ArrayList<Car> cars = configuration.setupCars(2);
        jsonManager.saveJSONFile(configuration, "test");
        CarManager cm = new CarManager(cars);
        viewGenerator.updateView(cars);
    }

    public void loadJSONConfiguration(String fileName) {
        jsonManager = new JSONManager();
        configuration = jsonManager.readJSONFile(viewGenerator,fileName);
        CarManager cm = new CarManager(configuration.getCars());
        viewGenerator.updateView(configuration.getCars());
    }

    public void loadConfiguration(ViewGenerator viewGenerator) {
        this.viewGenerator = viewGenerator;
        createConfiguration();
        //loadJSONConfiguration("test");
        viewGenerator.drawConfiguration(configuration);
    }
}

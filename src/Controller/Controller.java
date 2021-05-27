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

    public Configuration initializeConfig(){
        configuration = new Configuration(viewGenerator);
        return configuration;
    }

    public void initializeSimulation(ViewGenerator viewGenerator){
        this.viewGenerator = viewGenerator;
        jsonManager = new JSONManager();
        ArrayList<Car> cars = configuration.setupCars(configuration.getCities().size());
        jsonManager.saveJSONFile(configuration, "test");
        CarManager cm = new CarManager(cars);
        viewGenerator.updateView(cars);
        viewGenerator.drawConfiguration(configuration);
    }
    public void createRandomConfiguration() {
        initializeConfig();
        configuration.addRandomElements(5,true);
    }

    public void loadJSONConfiguration(ViewGenerator viewGenerator, String fileName) {
        this.viewGenerator = viewGenerator;
        jsonManager = new JSONManager();
        configuration = jsonManager.readJSONFile(viewGenerator,fileName);
        CarManager cm = new CarManager(configuration.getCars());
        viewGenerator.updateView(configuration.getCars());
        viewGenerator.drawConfiguration(configuration);
    }
}

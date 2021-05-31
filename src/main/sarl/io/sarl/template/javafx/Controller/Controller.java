package io.sarl.template.javafx.Controller;

import io.sarl.template.javafx.Model.Car;
import io.sarl.template.javafx.Model.Configuration;
import io.sarl.template.javafx.Model.JSONManager;
import io.sarl.template.javafx.View.ViewGenerator;

import java.util.ArrayList;

public class Controller {
    private static Controller instance = null;
    private Configuration configuration = null;
    private ViewGenerator viewGenerator = null;
    private JSONManager jsonManager = null;
    private boolean startAgent = false;

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
        startAgent = true;
        System.out.println(this.startAgent);
        viewGenerator.updateView(cars);
        viewGenerator.drawConfiguration(configuration);
        
    }
    public void createRandomConfiguration() {
        initializeConfig();
        configuration.addRandomElements(5,false);
        startAgent = true;
    }

    public void loadJSONConfiguration(ViewGenerator viewGenerator, String fileName) {
        this.viewGenerator = viewGenerator;
        jsonManager = new JSONManager();
        configuration = jsonManager.readJSONFile(viewGenerator,fileName);
        CarManager cm = new CarManager(configuration.getCars());
        startAgent = true;
        viewGenerator.updateView(configuration.getCars());
        viewGenerator.drawConfiguration(configuration);
        
    }
    
    public Configuration getConfiguration() {
    	return configuration;
    }
    
    public boolean getStartAgent() {
    	return startAgent;
    }

	
}

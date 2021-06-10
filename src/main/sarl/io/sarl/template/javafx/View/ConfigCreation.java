package io.sarl.template.javafx.View;

import io.sarl.template.javafx.Controller.Controller;
import io.sarl.template.javafx.Model.City;
import io.sarl.template.javafx.Model.Configuration;
import io.sarl.template.javafx.Model.Coordinate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.control.Spinner;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javax.swing.text.View;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigCreation {
    @FXML
    public Button validateRoads;
    @FXML
    private Button buttonHippodamian;
    @FXML
    private Button buttonNormal;
    @FXML
    private Pane cityPane;
    @FXML
    private TextField cityName;
    @FXML
    private Spinner<Integer> sizeSpinner;
    @FXML
    private Label errorLabel;
    @FXML
    private Spinner<Integer> carEnergySpinner;
    @FXML
    private Label energyLabel;

    private boolean placeRoads = false;
    private boolean isCitySelected = false;
    private City citySelected = null;
    private int defaultSize = 30;
    
    private ArrayList<City[]> linkedCities = new ArrayList<>();
    private ArrayList<City> citiesChecked = new ArrayList<>();

    Controller controller = Controller.getInstance();
    Configuration config;
    ViewGenerator viewGenerator = new ViewGenerator();
    int chosenEnergy = 100;

    public void createConfig(boolean isHippo){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("simulationViewer.fxml"));
            Parent root = loader.load();
            viewGenerator.setMainPane((Pane) loader.getNamespace().get("mainPane"));
            config.addElements(isHippo);
            controller.initializeSimulation(viewGenerator, chosenEnergy);
            stage.setScene(new Scene(root, 600, 650));
            stage.setTitle("Simulation Viewer");
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createHippodamianConfig(ActionEvent actionEvent) {
        Button close = (Button) actionEvent.getSource();
        Stage oldstage = (Stage) close.getScene().getWindow();
        oldstage.close();
        createConfig(true);
    }

    public void createNormalConfig(ActionEvent actionEvent) {
//        Button close = (Button) actionEvent.getSource();
//        Stage oldstage = (Stage) close.getScene().getWindow();
//        oldstage.close();
        changeToDrawRoadView();
    }

    private void changeToDrawRoadView() {
        buttonHippodamian.setDisable(true);
        buttonHippodamian.setVisible(false);
        buttonNormal.setDisable(true);
        buttonNormal.setVisible(false);
        validateRoads.setVisible(true);
        carEnergySpinner.setVisible(true);
        energyLabel.setVisible(true);
        placeRoads = true;
    }

    public void addSomethingWhereMouseIs(MouseEvent mouseEvent) {
        if (!placeRoads) { // PLACE CITIES
        	ArrayList<String> errors = new ArrayList<>();
            if (config == null) {
                config = controller.initializeConfig();
            }
            String nameOfTheCity = cityName.getText().toString();
            int size = sizeSpinner.getValue();
            Coordinate coords = new Coordinate((float) mouseEvent.getX(), (float) mouseEvent.getY());
            boolean isCityPossible = true;
            for (City city : config.getCities()) {
                if (Math.abs(city.getPosition().getX() - coords.getX()) < (city.getSize() * 2) &&
                        Math.abs(city.getPosition().getY() - coords.getY()) < (city.getSize() * 2)) {
                    isCityPossible = false;
                    if (!(Math.abs(city.getPosition().getX() - coords.getX()) < city.getSize() &&
                            Math.abs(city.getPosition().getY() - coords.getY()) < city.getSize())) {
                    	errors.add("Ville trop proche ! ");
                    }
                }
                if (city.getName().equals(nameOfTheCity)) {
                	isCityPossible = false;
                	errors.add("Nom de ville incorrect ! ");
                }
            }
            if (isCityPossible) {
                addCity(coords, size, nameOfTheCity);
            } else { // Check if we need to delete city
            	City deletedCity = null;
            	for (City city: config.getCities()) {
            		if (Math.abs(city.getPosition().getX() - coords.getX()) < city.getSize() &&
                            Math.abs(city.getPosition().getY() - coords.getY()) < city.getSize()) {
                        deletedCity = city;
                    }
            	}
            	if (deletedCity != null) {
            		removeCity(deletedCity);
            	} else {
            		errorController(errors);
            	}
            }
        } else { // Place Roads
            if (!isCitySelected) { // Première ville selectionnée
                Coordinate coords = new Coordinate((float) mouseEvent.getX(), (float) mouseEvent.getY());
                for (City city : config.getCities()) {
                    if (Math.abs(city.getPosition().getX() - coords.getX()) < (city.getSize() * 2) &&
                            Math.abs(city.getPosition().getY() - coords.getY()) < (city.getSize() * 2)) {
                        citySelected = city;
                        isCitySelected = true;
                    }
                }
            } else {
                Coordinate coords = new Coordinate((float) mouseEvent.getX(), (float) mouseEvent.getY());
                for (City city : config.getCities()) {
                    if (Math.abs(city.getPosition().getX() - coords.getX()) < (city.getSize() * 2) &&
                            Math.abs(city.getPosition().getY() - coords.getY()) < (city.getSize() * 2) &&
                            isPossibleToLink(citySelected, city)) {
                        // Place road
                        config.addRoad(citySelected.getCrossRoad(), city.getCrossRoad());
                        Line line = new Line(citySelected.getPosition().getX(), citySelected.getPosition().getY(), city.getPosition().getX(), city.getPosition().getY());
                        line.setStrokeWidth(12);
                        line.setStroke(Color.BLACK);
                        cityPane.getChildren().add(line);
                        //adding the new linked cities to the list
                        City[] newLink = {city, citySelected};
                        linkedCities.add(newLink);
                        isCitySelected = false;
                    }
                }
                //if the configuration links all the cities together, then we can validate
                if (isConfigValid()) {
                    validateRoads.setDisable(false);
                    carEnergySpinner.setDisable(false);
                    
                }
            }
        }
    }
    
    //checks if every city can reach every other city
    private boolean isConfigValid() {
    	lilRecursiveFunctionToCheckThroughAllTheCities(config.getCities().get(0));
    	if(citiesChecked.size() != config.getCities().size()) {
    		//means the cities aren't all linked for now
    		citiesChecked.clear();
    		return false;
    	}
    	return true;
    }
    
    private void lilRecursiveFunctionToCheckThroughAllTheCities(City cityIn) {
    	//adding the current city, then checking if the cities it's linked to are already stored
    	//If they're not then we call the function with them
    	citiesChecked.add(cityIn);
    	for(City[] link : linkedCities) {
    		if(link[0].equals(cityIn) && !citiesChecked.contains(link[1])) {
    			lilRecursiveFunctionToCheckThroughAllTheCities(link[1]);
    			
    		} else if (link[1].equals(cityIn) && !citiesChecked.contains(link[0])) {
    			lilRecursiveFunctionToCheckThroughAllTheCities(link[0]);
    		}
    	}
    	
    }
    
    //checks if two cities are "linkable"
    private boolean isPossibleToLink(City cityA, City cityB) {
    	//checks if the city to link isn't the same as the first one
    	if(cityA.equals(cityB)) {
    		System.out.println("You can't link the city to itself");
    		isCitySelected = false;
    		return false;
    	} else {
    		//checks if both cities aren't already linked
    		for (City[] link : linkedCities) {
    			if((link[0].equals(cityA) && link[1].equals(cityB)) ||
    					(link[0].equals(cityB) && link[1].equals(cityA))) {
    	    		System.out.println("Cities already linked");
    				return false;
    			}
    		}
    	}
    	return true;
    }

    private void addCity(Coordinate coords, int size, String nameOfTheCity) {
    	config.addCity(coords, size, nameOfTheCity);
        if (config.getCities().size() > 3) { // Min 4 cities per configuration
            buttonHippodamian.setDisable(false);
            buttonNormal.setDisable(false);
        }
        Circle cityCircle = new Circle(coords.getX(), coords.getY(), size);
        cityCircle.setId(nameOfTheCity);
        Text cityName = new Text(coords.getX() - 10, coords.getY() + 5, nameOfTheCity);
        cityName.setId("text"+nameOfTheCity);
        cityCircle.setFill((Color.color(Math.random(), Math.random(), Math.random())));
        cityPane.getChildren().add(cityCircle);
        cityPane.getChildren().add(cityName);
	}

	private void removeCity(City deletedCity) {
    	Circle circle = null;
    	Text text = null;
		// Remove City with id
    	for (Node node: cityPane.getChildren()) {
            if (deletedCity.getName().equals(node.getId())) {
                circle = (Circle) node;
            }
            if (("text"+deletedCity.getName()).equals(node.getId())) {
                text = (Text) node;
            }
        }
		cityPane.getChildren().remove(circle);
		cityPane.getChildren().remove(text);
		config.getCities().remove(deletedCity);
		// Check if we need to disable buttons
		if (config.getCities().size() < 4) {
			buttonHippodamian.setDisable(true);
            buttonNormal.setDisable(true);
		}
	}
	
	private void errorController(ArrayList<String> error) {
		errorLabel.setAlignment(Pos.CENTER);
		String errorMessage = "";
		for (String str: error) {
			errorMessage += str;
		}
		errorLabel.setText(errorMessage);
	}

	public void validateConfig(ActionEvent event) {
		chosenEnergy = carEnergySpinner.getValue();
        Button close = (Button) event.getSource();
        Stage oldstage = (Stage) close.getScene().getWindow();
        oldstage.close();
        createConfig(false);
    }
}

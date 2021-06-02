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
import javafx.stage.Stage;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
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
    private TextField sizeLabel;

    private boolean placeRoads = false;
    private boolean isCitySelected = false;
    private City citySelected = null;
    private int defaultSize = 30;
    
    private ArrayList<City> citiesNotLinked = new ArrayList<>();

    Controller controller = Controller.getInstance();
    Configuration config;
    ViewGenerator viewGenerator = new ViewGenerator();

    public void createConfig(boolean isHippo){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("simulationViewer.fxml"));
            Parent root = loader.load();
            viewGenerator.setMainPane((Pane) loader.getNamespace().get("mainPane"));
            config.addElements(isHippo);
            controller.initializeSimulation(viewGenerator);
            stage.setScene(new Scene(root, 600, 600));
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
        citiesNotLinked.addAll(config.getCities());
        changeToDrawRoadView();
    }

    private void changeToDrawRoadView() {
        buttonHippodamian.setDisable(true);
        buttonHippodamian.setVisible(false);
        buttonNormal.setDisable(true);
        buttonNormal.setVisible(false);
        validateRoads.setVisible(true);
        placeRoads = true;
    }

    public void addCityWhereMouseIs(MouseEvent mouseEvent) {
        if (!placeRoads) { // PLACE CITIES
            if (config == null) {
                config = controller.initializeConfig();
            }
            String nameOfTheCity = cityName.getText().toString();
            String tempSize = sizeLabel.getText().toString();
            int size = 0;
            try {
            	size = Integer.parseInt(tempSize);
            } catch (NumberFormatException e) {
            	size = defaultSize;
            }
            System.out.println(size);
            Coordinate coords = new Coordinate((float) mouseEvent.getX(), (float) mouseEvent.getY());
            boolean isCityPossible = true;
            for (City city : config.getCities()) {
                if (Math.abs(city.getPosition().getX() - coords.getX()) < (city.getSize() * 2) &&
                        Math.abs(city.getPosition().getY() - coords.getY()) < (city.getSize() * 2)) {
                    isCityPossible = false;
                }
                if (city.getName().equals(nameOfTheCity)) isCityPossible = false;
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
                            Math.abs(city.getPosition().getY() - coords.getY()) < (city.getSize() * 2)) {
                        // Place road
                        config.addRoad(citySelected.getCrossRoad(), city.getCrossRoad());
                        citiesNotLinked.remove(citySelected);
                        citiesNotLinked.remove(city);
                        Line line = new Line(citySelected.getPosition().getX(), citySelected.getPosition().getY(), city.getPosition().getX(), city.getPosition().getY());
                        line.setStrokeWidth(12);
                        line.setStroke(Color.BLACK);
                        cityPane.getChildren().add(line);
                        isCitySelected = false;
                    }
                }
                if (citiesNotLinked.size() == 0) {
                    validateRoads.setDisable(false);
                }
            }
        }
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
	
	private void errorController(String error) {
		System.out.println(error);
	}

	public void validateConfig(ActionEvent event) {
        Button close = (Button) event.getSource();
        Stage oldstage = (Stage) close.getScene().getWindow();
        oldstage.close();
        createConfig(false);
    }
}

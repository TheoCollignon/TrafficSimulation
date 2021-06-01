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

    private boolean placeRoads = false;
    private boolean isCitySelected = false;
    private City citySelected = null;
    
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
            Coordinate coords = new Coordinate((float) mouseEvent.getX(), (float) mouseEvent.getY());
            boolean isCityPossible = true;
            for (City city : config.getCities()) {
                if (Math.abs(city.getPosition().getX() - coords.getX()) < (city.getSize() * 2) &&
                        Math.abs(city.getPosition().getY() - coords.getY()) < (city.getSize() * 2)) {
                    isCityPossible = false;
                }
            }
            if (isCityPossible) {
                config.addCity(coords, 30, String.valueOf(config.getCities().size() + 1));
                if (config.getCities().size() > 3) { // Min 4 cities per configuration
                    buttonHippodamian.setDisable(false);
                    buttonNormal.setDisable(false);
                }
                Circle cityCircle = new Circle(mouseEvent.getX(), mouseEvent.getY(), 30);
                cityCircle.setId(""+config.getCities().size());
                Text cityName = new Text(mouseEvent.getX() - 5, mouseEvent.getY() + 5, String.valueOf(config.getCities().size()));
                cityName.setId("text"+config.getCities().size());
                cityCircle.setFill((Color.color(Math.random(), Math.random(), Math.random())));
                cityPane.getChildren().add(cityCircle);
                cityPane.getChildren().add(cityName);
            } else { // Check if we need to delete city
            	City deletedCity = null;
            	int cityID = 0;
            	for (int i = 0; i < config.getCities().size(); i++) {
            		City city = config.getCities().get(i);
            		if (Math.abs(city.getPosition().getX() - coords.getX()) < city.getSize() &&
                            Math.abs(city.getPosition().getY() - coords.getY()) < city.getSize()) {
                        deletedCity = city;
                        cityID = i+1;
                    }
            	}
            	if (deletedCity != null) {
            		removeCity(cityID, deletedCity);
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
                        // Place roads
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

    private void removeCity(int cityID, City deletedCity) {
    	Circle circle = null;
    	Text text = null;
		// Remove City with id
    	for (Node node: cityPane.getChildren()) {
            if (String.valueOf(cityID).equals(node.getId())) {
                circle = (Circle) node;
            }
            if (String.valueOf("text"+cityID).equals(node.getId())) {
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

	public void validateConfig(ActionEvent event) {
        Button close = (Button) event.getSource();
        Stage oldstage = (Stage) close.getScene().getWindow();
        oldstage.close();
        createConfig(false);
    }
}

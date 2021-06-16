package io.sarl.template.javafx.View;

import io.sarl.template.javafx.Controller.Controller;
import io.sarl.template.javafx.Model.Car;
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
import javafx.scene.control.TextArea;
import javax.swing.text.View;
import javafx.fxml.Initializable;

import com.google.common.io.Resources;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DisplayData implements Initializable {
	@FXML
    public TextArea datasArea;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Controller controller = Controller.getInstance();
		Configuration configuration = controller.getConfiguration();
		int nbTotalCarsSpawned = configuration.getNbTotalCarsSpawned();
        datasArea.setText("Total number of cars that existed : " + nbTotalCarsSpawned);
        datasArea.appendText("\nNumber of cars on the map : " + configuration.getCars().size());
        datasArea.appendText("\nNumber of cars destroyed : " + configuration.getDeletedCars().size());
        
        datasArea.appendText("\n\nList of cars on the map");
        for (Car car: configuration.getCars()) {
        	datasArea.appendText("\nCar "+car.getId()+" : on the road between "+car.getCityFrom().getName()+" and " +car.getDestination().getName()+ " with "+car.getEnergy()+" fuel left at "+car.getSpeedMoy()+" km/h on average.");
        }
        
        datasArea.appendText("\n\nList of the destroyed cars");
        for (Car car: configuration.getDeletedCars()) {
        	datasArea.appendText("\nCar "+car.getId()+" was on the road between "+car.getCityFrom().getName()+" and " +car.getDestination().getName()+ " with "+car.getEnergy()+" fuel left at "+car.getSpeedMoy()+" km/h on average.");
        }
	}
    
}

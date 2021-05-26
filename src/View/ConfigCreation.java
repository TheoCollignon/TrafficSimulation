package View;

import Controller.Controller;
import Model.City;
import Model.Configuration;
import Model.Coordinate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.text.View;
import java.awt.*;
import java.io.IOException;

public class ConfigCreation {
    @FXML
    private Button buttonHippodamian;
    @FXML
    private Button buttonNormal;
    @FXML
    private Pane cityPane;

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
        Button close = (Button) actionEvent.getSource();
        Stage oldstage = (Stage) close.getScene().getWindow();
        oldstage.close();
        createConfig(false);
        
    }

    public void addCityWhereMouseIs(MouseEvent mouseEvent) {
        if(config == null){
            config = controller.initializeConfig();
        }
        if(config.getCities().size() == 1){
            buttonHippodamian.setDisable(false);
            buttonNormal.setDisable(false);

        }
        Coordinate coords = new Coordinate((float)mouseEvent.getX(),(float)mouseEvent.getY());
        boolean isCityPossible = true;
        for (City city: config.getCities()) {
            if (Math.abs(city.getPosition().getX() - coords.getX()) < (city.getSize()*2) &&
                    Math.abs(city.getPosition().getY() - coords.getY()) < (city.getSize()*2)){
                isCityPossible = false;
            }
        }
        if(isCityPossible){
            config.addCity(coords,30,String.valueOf(config.getCities().size()+1));
            Circle cityCircle = new Circle(mouseEvent.getX(), mouseEvent.getY(), 30);
            Text cityName = new Text(mouseEvent.getX() - 5, mouseEvent.getY() + 5, String.valueOf(config.getCities().size()));
            cityCircle.setFill((Color.color(Math.random(), Math.random(), Math.random())));
            cityPane.getChildren().add(cityCircle);
            cityPane.getChildren().add(cityName);
        }
    }
}

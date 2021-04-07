package View;

import Model.*;
import Controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ViewGenerator extends Application {
    private Stage stage;
    @FXML
    private Pane mainPane;
    Controller controller = Controller.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent rootLayout = loader.load();
        mainPane = (Pane) loader.getNamespace().get("mainPane");
        this.controller = Controller.getInstance();
        controller.loadConfiguration(this);
        stage.setScene(new Scene(rootLayout, 600, 600));
        stage.show();
    }

    public void drawConfiguration(Configuration configuration) {
        // Draw Roads
        for (Road road : configuration.getRoads()) {
            ArrayList<Coordinate> roadCoords = road.getCoordsList();
            for (int i = 0; i < 2; i++) {
                Line line = new Line(roadCoords.get(i).getX(), roadCoords.get(i).getY(), roadCoords.get(i+1).getX(), roadCoords.get(i+1).getY());
                line.setStrokeWidth(road.getWidth());
                mainPane.getChildren().add(line);
            }
            for (int i = 0; i < roadCoords.size() - 1; i++) {
                Circle circle = new Circle(roadCoords.get(i).getX(), roadCoords.get(i).getY(), 2);
                circle.setFill(Color.GREEN);
                circle.setStrokeWidth(road.getWidth());
                mainPane.getChildren().add(circle);
            }


        }
        // Draw Cities
        for (City city : configuration.getCities()) {
            Circle whole = new Circle(city.getPosition().getX(), city.getPosition().getY(), city.getSize());
            Text t = new Text(city.getPosition().getX() - 5, city.getPosition().getY() + 5, city.getName());
            whole.setFill(Color.LIGHTGRAY);
            mainPane.getChildren().add(whole);
            mainPane.getChildren().add(t);
        }
        // Draw TrafficLights
        for (TrafficLight trafficLight : configuration.getTrafficLights()) {
            Circle tLight = new Circle(trafficLight.getPosition()[0], trafficLight.getPosition()[1], 10);
            tLight.setId(String.valueOf(trafficLight.getId()));
            if (trafficLight.isRed()) tLight.setFill(Color.RED);
            else tLight.setFill(Color.GREEN);
            mainPane.getChildren().add(tLight);
        }
        // Draw cars comme le film
        for (Car cars : configuration.getCars()) {
            Circle car = new Circle(cars.getPosition().getX(), cars.getPosition().getY(), 5);
            car.setId(String.valueOf(cars.getId()));
            car.setFill(Color.DARKRED);
            mainPane.getChildren().add(car);
        }
        /*Line line = new Line(100, 10, 10, 110);
        System.out.println(mainPane);
        mainPane.getChildren().add(line);*/
    }




    public void updateTrafficLight(TrafficLight trafficLight) {
        for (Node node: mainPane.getChildren()) {
            if (String.valueOf(trafficLight.getId()).equals(node.getId())) {
                Circle circle = (Circle) node;
                if (trafficLight.isRed()) circle.setFill(Color.RED);
                else circle.setFill(Color.GREEN);
            }
        }
    }

    public void updateCarPositionDraw(Car car) {
        for (Node node: mainPane.getChildren()) {
            if (String.valueOf(car.getId()).equals(node.getId())) {
                Circle circle = (Circle) node;
                System.out.println("--------");
//                if ( circle.getCenterX() == car.getPosition().getX() ) System.out.println("avant x correct " + circle.getCenterX());
//                if ( circle.getCenterY() == car.getPosition().getY() ) System.out.println("avant y correct " + circle.getCenterY());

                circle.setCenterX(car.getPosition().getX());
                circle.setCenterY(car.getPosition().getY());
//                if ( circle.getCenterX() == car.getPosition().getX() ) System.out.println("apres x correct " + circle.getCenterX());
//                if ( circle.getCenterY() == car.getPosition().getY() ) System.out.println("apres y correct " + circle.getCenterY());
//                System.out.println("--------");
//                System.out.println("affichage x apres : " + circle.getCenterX());
//                System.out.println("affichage y apres : " + circle.getCenterY());
                circle.setFill(Color.YELLOW);
            }
        }

    }
}

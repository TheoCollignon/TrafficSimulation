package io.sarl.template.javafx.View;

import io.sarl.template.javafx.Controller.Controller;
import io.sarl.template.javafx.Model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class ViewGenerator {
    private Stage stage;
    @FXML
    private Pane mainPane;
    @FXML
    private Button saveConfig;
    @FXML
    private TextField jsonfileName;
    Controller controller = Controller.getInstance();
    
    @FXML
    public void saveConfig(ActionEvent event) {
    	String JSONFile = jsonfileName.getText().toString();
    	controller.getJSONManager().saveJSONFile(controller.getConfiguration(), JSONFile);
    }

    public void drawConfiguration(Configuration configuration) {
        // Draw Roads
        for (Road road : configuration.getRoads()) {
            ArrayList<Coordinate> roadCoords = road.getCoordsList();

            for (int i = 0; i <  roadCoords.size() - 1; i++) {
                if (i + 1 < (roadCoords.size() - 1) ) {
                    Line line = new Line(roadCoords.get(i).getX(), roadCoords.get(i).getY(), roadCoords.get(i + 1).getX(), roadCoords.get(i + 1).getY());
                    line.setStrokeWidth(road.getWidth());
                    mainPane.getChildren().add(line);
                }
            }
            for (int i = 0; i < roadCoords.size() - 1; i++) {
                if (i + 1 < (roadCoords.size() - 10) ) {
                    if (i%20 == 0) {
                        Line line = new Line(roadCoords.get(i).getX(), roadCoords.get(i).getY(), roadCoords.get(i + 10).getX(), roadCoords.get(i +10).getY());
                        line.setStrokeWidth(road.getWidth() - 13); // markings
                        line.setStroke(Color.WHITE);
                        mainPane.getChildren().add(line);
                    }
                }
            }

        }
        // Draw Cities
        for (City city : configuration.getCities()) {
            Circle whole = new Circle(city.getPosition().getX(), city.getPosition().getY(), city.getSize());
            Text t = new Text(city.getPosition().getX() - 5, city.getPosition().getY() + 5, city.getName());
            whole.setFill((Color.color(Math.random(), Math.random(), Math.random())));
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
            Rectangle car = new Rectangle(cars.getPosition().getX(),cars.getPosition().getY(), 10,5);
            // Circle car = new Circle(cars.getPosition().getX(), cars.getPosition().getY(), 5);
            car.setId(String.valueOf(cars.getId()));
            car.setFill(Color.DARKRED);
            mainPane.getChildren().add(car);
        }
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

//    public void drawNewCar(Car car) {
//		 Rectangle rectangleCar = new Rectangle(car.getPosition().getX(),car.getPosition().getY(), 10,5);
//	     // Circle car = new Circle(cars.getPosition().getX(), cars.getPosition().getY(), 5);
//		 rectangleCar.setId(String.valueOf(car.getId()));
//		 rectangleCar.setFill(Color.PURPLE);
//	     mainPane.getChildren().add(rectangleCar);
//    }
    
    public void updateCarPositionDraw(Car car) {
    	// System.out.println("size:" + car);
    	boolean isFind = false;
        for (Node node: mainPane.getChildren()) {
            if (String.valueOf(car.getId()).equals(node.getId())) {
            	isFind = true;
                // Circle circle = (Circle) node;
                Rectangle carView = (Rectangle) node;
                // trying to put the car on the right side of the road for better display
                float carPositionX = car.getPosition().getX();
                float carPositionY = car.getPosition().getY();

                // edit the X position for better looking on the road
                if (car.getRoadOn().getCrossroadEnd().getCoords().getY() > car.getRoadOn().getCrossroadStart().getCoords().getY()) {
                    if ( car.getDirection() == -1) {
                        carPositionX += 4;
                    } else if  ( car.getDirection() == 1) {
                        carPositionX -= 4;
                    }
                } else if (car.getRoadOn().getCrossroadEnd().getCoords().getY() <= car.getRoadOn().getCrossroadStart().getCoords().getY()){
                    if ( car.getDirection() == -1) {
                        carPositionX -= 4;
                    } else if  ( car.getDirection() == 1) {
                        carPositionX += 4;
                    }
                }
                // edit the Y position for better looking on the road
                if (car.getRoadOn().getCrossroadEnd().getCoords().getX() <= car.getRoadOn().getCrossroadStart().getCoords().getX()) {
                    if ( car.getDirection() == -1) {
                        carPositionY += 4;
                    } else if  ( car.getDirection() == 1) {
                        carPositionY -= 4;
                    }
                } else if (car.getRoadOn().getCrossroadEnd().getCoords().getX() > car.getRoadOn().getCrossroadStart().getCoords().getX()){
                    if ( car.getDirection() == -1) {
                        carPositionY -= 4;
                    } else if  ( car.getDirection() == 1) {
                        carPositionY += 4;
                    }
                }
                // circle.setCenterX(carPositionX);
                // circle.setCenterY(carPositionY);
                carView.setX(carPositionX);
                carView.setY(carPositionY);

                // rotation of the car on the same angle of the road
                // default value :
                float xA = car.getRoadOn().getCrossroadStart().getCoords().getX();
                float xB = car.getRoadOn().getCrossroadEnd().getCoords().getX();
                float yA = car.getRoadOn().getCrossroadStart().getCoords().getY();
                float yB = car.getRoadOn().getCrossroadEnd().getCoords().getY();

                for (int i=0; i< car.getRoadOn().getCoordsList().size(); i++) {
                    if (car.getRoadOn().getCoordsList().get(i).getCar().size() > 0){
                        if (car.getRoadOn().getCoordsList().get(i).getCar().get(0).equals(car)){
                            if (car.getRoadOn().getCoordsList().size()  > i + 1) {
                                xA = car.getRoadOn().getCoordsList().get(i).getX();
                                xB = car.getRoadOn().getCoordsList().get(i+1).getX();
                                yA = car.getRoadOn().getCoordsList().get(i).getY();
                                yB = car.getRoadOn().getCoordsList().get(i+1).getY();
                            }
                        }
                    }
                }

                double distanceAB = Math.sqrt(Math.pow(xB - xA,2) +
                      Math.pow( yB- yA,2));
                double cos = (xB- xA )/ distanceAB;
                double arccos =  Math.acos( cos);
                double sin = (yB- yA )/ distanceAB;
                double rotateValue = arccos;
                // if ((car.getRoadOn().getEnd().getPosition().getY() - car.getRoadOn().getStart().getPosition().getY()) <= 0 ) rotateValue = -rotateValue;
                if (sin <= 0 ) rotateValue = -rotateValue;
                // radiant to degres
                rotateValue = (rotateValue * 180 / Math.PI)%360;
                carView.setRotate(rotateValue);

                // to be delete, but for the moment, it helps to know if the car's thread is alive
                // carView.setFill(Color.color(Math.random(), Math.random(), Math.random()));
                carView.setFill(Color.color(0.9, 0.1, 0.2));
                // to be delete
                if(car.getId() >4) {
                	carView.setFill(Color.GREEN);
                } 
                // end to be delete
            }
        }
        if(!isFind) {
        	System.out.println("SALUT");
	   		 Rectangle rectangleCar = new Rectangle(car.getPosition().getX(),car.getPosition().getY(), 10,5);
		     // Circle car = new Circle(cars.getPosition().getX(), cars.getPosition().getY(), 5);
			 rectangleCar.setId(String.valueOf(car.getId()));
			 rectangleCar.setFill(Color.PURPLE);
		     mainPane.getChildren().add(rectangleCar);
        }
        

    }

    public void updateView(Configuration configuration) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), ev -> {
            //what you want to do
        	ArrayList<Car> cars = configuration.getCars();
            for(Car c : cars) {
                updateCarPositionDraw(c);
            }
        }));
        timeline.setCycleCount(1);//do it x times
        timeline.setCycleCount(Animation.INDEFINITE);//or indefinitely

        //play:
        timeline.play();
    }

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }
}

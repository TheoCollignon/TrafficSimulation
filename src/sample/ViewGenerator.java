package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
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
        controller.init();
        controller.setViewStart(this);
        stage.setScene(new Scene(rootLayout, 600, 600));
        stage.show();
    }

    public void drawElements(ArrayList<City> cities, ArrayList<Road> roads) {
        System.out.println(cities);
        // Draw Roads
        for (Road road : roads) {
            ArrayList<int[]> roadCoords = road.getCoordsList();
            for (int i = 0; i < roadCoords.size() - 1; i++) {
                Line line = new Line(roadCoords.get(i)[0], roadCoords.get(i)[1], roadCoords.get(i+1)[0], roadCoords.get(i+1)[1]);
                line.setStrokeWidth(road.getWidth());
                mainPane.getChildren().add(line);
            }
        }
        // Draw Cities
        for (City city : cities) {
            Circle whole = new Circle(city.getPosition()[0], city.getPosition()[1], city.getSize());
            Text t = new Text(city.getPosition()[0] - 5, city.getPosition()[1] + 5, city.getName());
            whole.setFill(Color.LIGHTGRAY);
            mainPane.getChildren().add(whole);
            mainPane.getChildren().add(t);
        }
        /*Line line = new Line(100, 10, 10, 110);
        System.out.println(mainPane);
        mainPane.getChildren().add(line);*/
    }
}

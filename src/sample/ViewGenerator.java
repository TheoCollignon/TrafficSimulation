package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ViewGenerator extends Application {
    @FXML
    private Pane mainPane;
    Controller controller = Controller.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        System.out.println(loader);
        mainPane = (Pane) loader.getNamespace().get("mainPane");
        System.out.println(mainPane);
        this.controller = Controller.getInstance();
        controller.setViewStart(this);
    }

    public void drawRoads(ArrayList<Road> roadsList) {
        Line line = new Line(100, 10, 10, 110);
        System.out.println(mainPane);
        mainPane.getChildren().add(line);
    }
}

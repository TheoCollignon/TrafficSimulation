package View;

import Controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class ViewMenu extends Application {
    private ViewGenerator viewGenerator = new ViewGenerator();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent rootLayout = loader.load();
        stage.setScene(new Scene(rootLayout, 600, 600));
        stage.setTitle("Menu");
        stage.show();
    }

    @FXML
    public void loadRandomConfiguration() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("simulationViewer.fxml"));
            Parent root = loader.load();
            viewGenerator.setMainPane((Pane) loader.getNamespace().get("mainPane"));
            Controller.getInstance().createRandomConfiguration(viewGenerator);
            stage.setScene(new Scene(root, 600, 600));
            stage.setTitle("Simulation Viewer");
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void loadConfigCreationWindow() {

    }

    @FXML
    public void loadConfigSelectorWindow() {
        Stage stage = new Stage();
        File f = new File("./configurationFiles/");
        // This filter will only include files ending with .json
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                return name.endsWith(".json");
            }
        };
        String[] filesNames = f.list(filter);
        assert filesNames != null;

        Button[] buttonsArray = new Button[filesNames.length];
        for (int i = 0; i < filesNames.length; i++) {
            buttonsArray[i] = new Button(filesNames[i]);
            buttonsArray[i].setOnAction(this::loadJSONFile);
        }
        VBox vbox = new VBox(buttonsArray);
        Scene scene = new Scene(vbox, 600, 600);
        stage.setScene(scene);
        stage.setTitle("Configuration Selector");
        stage.show();
    }

    private void loadJSONFile(ActionEvent value) {
        String fileName = ((Button)value.getSource()).getText();
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("simulationViewer.fxml"));
            Parent root = loader.load();
            viewGenerator.setMainPane((Pane) loader.getNamespace().get("mainPane"));
            Controller.getInstance().loadJSONConfiguration(viewGenerator, fileName.substring(0, fileName.length() - 5));
            stage.setScene(new Scene(root, 600, 600));
            stage.setTitle("Simulation Viewer");
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

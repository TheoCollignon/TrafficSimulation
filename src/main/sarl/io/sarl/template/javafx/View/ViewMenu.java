package io.sarl.template.javafx.View;

import io.sarl.template.javafx.Controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.geometry.Rectangle2D;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;

public class ViewMenu extends Application {
    @FXML
    private Stage globalStage;
    private ViewGenerator viewGenerator = new ViewGenerator();

    @Override
    public void start(Stage stage) throws Exception {
    	URL location = getClass().getResource("menu.fxml");
        FXMLLoader loader = new FXMLLoader(location);
        Parent rootLayout = loader.load();
        stage.setScene(new Scene(rootLayout, 600, 650));
        this.globalStage = stage;
        stage.setTitle("Menu");
        stage.show();
    }

    @FXML
    public void loadRandomHippo(ActionEvent event) throws InterruptedException {
        Button close = (Button) event.getSource();
        Stage stage = (Stage) close.getScene().getWindow();
        // oldstage.close();
        
        try {
            // Stage stage = new Stage();
        	Thread.sleep(100);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("displayData.fxml"));
            System.out.println("salut3");
            Parent root = loader.load();
            System.out.println("salut2"); 
            stage.getScene().setRoot(root);
            
            //stage.setScene(new Scene(root, 600, 600));
            stage.setTitle("Simulation Datas");
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            stage.setX(screenBounds.getWidth()/2 + 300);
            stage.setY(screenBounds.getHeight()/2 - 400);
      
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        loadRandomConfiguration(true);
    }
    
    @FXML
    public void loadRandomNormal(ActionEvent event) throws InterruptedException {
        Button close = (Button) event.getSource();
        Stage stage = (Stage) close.getScene().getWindow();
        // oldstage.close();
        
        try {
            // Stage stage = new Stage();
        	Thread.sleep(100);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("displayData.fxml"));
            System.out.println("salut3");
            Parent root = loader.load();
            System.out.println("salut2"); 
            stage.getScene().setRoot(root);
            //stage.setScene(new Scene(root, 600, 600));
            stage.setTitle("Simulation Datas");
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            stage.setX(screenBounds.getWidth()/2 + 300);
            stage.setY(screenBounds.getHeight()/2 - 400);
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        loadRandomConfiguration(false);
    }
    
    public void loadRandomConfiguration(boolean isHippo) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("simulationViewer.fxml"));
            Parent root = loader.load();
            viewGenerator.setMainPane((Pane) loader.getNamespace().get("mainPane"));
            Controller.getInstance().createRandomConfiguration(isHippo, viewGenerator);
            Controller.getInstance().initializeSimulation(viewGenerator, 10000000);
            stage.setScene(new Scene(root, 600, 650));
            stage.setTitle("Simulation Viewer");
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void loadConfigCreationWindow(ActionEvent event) {
        Button close = (Button) event.getSource();
        Stage oldstage = (Stage) close.getScene().getWindow();
        oldstage.close();
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("configCreation.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root, 600, 750));
            stage.setTitle("Configuration Creation");
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void loadConfigSelectorWindow(ActionEvent event) {
        Button close = (Button) event.getSource();
        Stage oldstage = (Stage) close.getScene().getWindow();
        oldstage.close();
        Stage stage = new Stage();
        // for eclipse : 
        
        URL location = getClass().getResource("../configurationFiles");
    	String jsonFolder = location.toString();
    	jsonFolder = jsonFolder.substring(6);
   
        File f = new File(jsonFolder);
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
            buttonsArray[i].setOnAction(arg0 -> {
				try {
					loadJSONFile(arg0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
        }
        VBox vbox = new VBox(buttonsArray);
        Scene scene = new Scene(vbox, 600, 600);
        stage.setScene(scene);
        stage.setTitle("Configuration Selector");
        stage.show();
    }

    private void loadJSONFile(ActionEvent value) throws InterruptedException {
        Button close = (Button) value.getSource();
        Stage oldstage = (Stage) close.getScene().getWindow();
        // oldstage.close();
        
        try {
            // Stage stage = new Stage();
        	Thread.sleep(100);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("displayData.fxml"));
            System.out.println("salut3");
            Parent root = loader.load();
            System.out.println("salut2"); 
            oldstage.getScene().setRoot(root);
            //stage.setScene(new Scene(root, 600, 600));
            oldstage.setTitle("Simulation Datas");
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            oldstage.setX(screenBounds.getWidth()/2 + 300);
            oldstage.setY(screenBounds.getHeight()/2 - 400);
            oldstage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        String fileName = ((Button)value.getSource()).getText();
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("simulationViewer.fxml"));
            Parent root = loader.load();
            viewGenerator.setMainPane((Pane) loader.getNamespace().get("mainPane"));
            Controller.getInstance().loadJSONConfiguration(viewGenerator, fileName.substring(0, fileName.length() - 5));
            stage.setScene(new Scene(root, 600, 650));
            stage.setTitle("Simulation Viewer");
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

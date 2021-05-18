package View;

import Controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewMenu extends Application {
    private ViewGenerator viewGenerator = new ViewGenerator();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent rootLayout = loader.load();
        stage.setScene(new Scene(rootLayout, 600, 600));
        stage.show();
    }

    @FXML
    public void openViewConfiguration() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = loader.load();
            viewGenerator.setMainPane((Pane) loader.getNamespace().get("mainPane"));
            Controller.getInstance().loadConfiguration(viewGenerator);
            stage.setScene(new Scene(root, 600, 600));
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

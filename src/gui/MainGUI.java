package ui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// MainGUI class responsible for displaying the GUI.
public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // load main.fxml file from controller.
    	Parent root = new MainController();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); 
    }
}

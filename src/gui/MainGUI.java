package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// MainGUI class responsible for displaying the GUI.
public class MainGUI extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		// load main.fxml file from controller.
		Parent root = FXMLLoader.load(getClass().getResource("/ui/main.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ubiquitous-tribble");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args); 
	}
}

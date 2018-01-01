package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class MainController { // main.fxml controller.

	@FXML private IOTabController IOTabController;

	@FXML public void initialize() {
		System.out.println("Application started");
		IOTabController.initialize(null, null);
	}
}


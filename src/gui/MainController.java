package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class MainController extends VBox { // main.fxml controller.
	
	@FXML private IOTabController IOTabController;

	// constructor for loading fxml.
	public MainController() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/main.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

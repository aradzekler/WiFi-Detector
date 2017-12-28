package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class IOTabController {  // IOTab.fxml controller
	
	@FXML
	private MainController mainController;

	public void injectMainController(MainController mainController){
		this.mainController = mainController;
	}

	public void CustomControl() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"/ui/IOTab.fxml"));
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}

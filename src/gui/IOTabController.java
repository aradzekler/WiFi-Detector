package ui;

import utils.csvWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

public class IOTabController implements Initializable{  // IOTab.fxml controller

	@FXML private MainController Main;
	// variables from fxml file to inject.
	@FXML private AnchorPane IOTab;
	@FXML private TextField sourceFolderFullPath;
	@FXML private Button sourceFolderPathSubmit;
	@FXML private TextField csvFileName;
	@FXML private Button csvFileNameSubmit;
	@FXML private Button deleteExistingData;
	@FXML private Button convertCsvToKml;

	@FXML
	protected void handleSourceFolderPathSubmit(ActionEvent event) { // handles path submit.
		if (sourceFolderFullPath.getText().isEmpty()) { // if empty field
			AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter path.");
			return;
		}
		AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, "SUCCESS","Path inserted.");
	}

	@FXML 
	protected void handleSourceFileNameSubmit(ActionEvent event) { // handles name submit.
		Window owner = csvFileNameSubmit.getScene().getWindow();
		if (csvFileName.getText().isEmpty()) { // if empty field
			AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter name.");
			return;
		}
		AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, "SUCCESS","Name inserted.");
	}

	@FXML
	protected void handleCsvFileNameSubmit(ActionEvent event) {

	}


	// class for creating alerts.
	public static class AlertHelper {
		public static void showAlert(Alert.AlertType alertType, String title, String message) {
			Alert alert = new Alert(alertType);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(message);
			alert.showAndWait();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}


package ui;

import utils.csvWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class IOTabController {  // IOTab.fxml controller

	private MainController main;
	// variables from fxml file to inject.
	@FXML private TextField sourceFolderFullPath;
	@FXML private Button sourceFolderPathSubmit;
	@FXML private TextField csvFileName;
	@FXML private Button csvFileNameSubmit;
	@FXML private Button deleteExistingData;
	@FXML private Button convertCsvToKml;

	@FXML
	protected void handleSourceFolderPathSubmit(ActionEvent event) { // handles path submit.
		Window owner = sourceFolderPathSubmit.getScene().getWindow();
		if (sourceFolderFullPath.getText().isEmpty()) { // if empty field
			AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please enter path.");
			return;
		}
		AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "SUCCESS","Path inserted.");
	}

	@FXML 
	protected void handleSourceFileNameSubmit(ActionEvent event) { // handles name submit.
		Window owner = csvFileNameSubmit.getScene().getWindow();
		if (csvFileName.getText().isEmpty()) { // if empty field
			AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please enter name.");
			return;
		}
		AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "SUCCESS","Name inserted.");
	}

	@FXML
	protected void handleCsvFileNameSubmit(ActionEvent event) {

	}

	public void init(MainController mainController) {
		main = mainController;
	}

	// class for creating alerts.
	public static class AlertHelper {
		public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
			Alert alert = new Alert(alertType);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(message);
			alert.initOwner(owner);
			alert.show();
		}
	}

}


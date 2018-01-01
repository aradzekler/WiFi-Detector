package ui;

import utils.csvWriter;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import algorithms.ApproxLocationByMac;
import filters.csvToKml;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class IOTabController implements Initializable {  // IOTab.fxml controller

	@FXML private MainController Main;
	// variables from fxml file to inject.
	@FXML private AnchorPane IOTab;
	@FXML private TextField sourceFolderFullPath;
	@FXML private Button sourceFolderPathSubmit;
	@FXML private TextField csvFileName;
	@FXML private Button csvFileNameSubmit;
	@FXML private Button unifyCsvFiles;
	@FXML private Button deleteExistingData;
	@FXML private Button convertCsvToKml;
	@FXML private Button presentData;

	private String csvPathName;
	private String csvName;
	private csvToKml kmlFile;
	private csvWriter csvFile;

	@FXML
	protected void handleSourceFolderPathSubmit(ActionEvent event) { // handles path submit.
		if (sourceFolderFullPath.getText().isEmpty()) { // if empty field
			AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter path.");
			return;
		}
		setCsvPathName(sourceFolderFullPath.getText());
		AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","Path inserted.");
	}

	@FXML 
	protected void handleSourceFileNameSubmit(ActionEvent event) { // handles name submit.
		if (csvFileName.getText().isEmpty()) { // if empty field
			AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter name.");
			return;
		}
		setCsvName(getCsvPathName() + csvFileName.getText() + ".csv");
		AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","Name inserted.");
	}

	@FXML // creates a new csv file when button clicked.
	protected void handleCsvFileNameSubmit(ActionEvent event) {
		if (csvPathName.isEmpty() || csvName.isEmpty()) {
			AlertHelper.showAlert(Alert.AlertType.ERROR, "FAILURE", "One or both fields are empty. \n Please enter "
					+ "both full folder path and file name.");
		}
		csvWriter unifiedCsvFile = new csvWriter(csvPathName, csvName);
		unifiedCsvFile.csvWriteFile();
		setCsvFile(unifiedCsvFile);
		AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","CSV File created.");
	}

	@FXML  // deletes all data created
	protected void handleDeleteExistingData(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "This will delete all data written.", ButtonType.OK, ButtonType.CANCEL);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.OK) { // if pressed ok, delete all data.
			File file = new File(getCsvName());
			file.delete();
			setCsvName(null);
			setCsvPathName(null);
			setCsvFile(null);
			setKmlFile(null);
		}
	}

	@FXML // creates a new kml file.
	protected void handleConvertCsvToKml(ActionEvent event) {
		File f = new File(getCsvName());
		if(f.exists() && !f.isDirectory()) { 
			csvToKml newkml = new csvToKml(getCsvPathName(), getCsvName());
			newkml.writeFileKML();
			setKmlFile(newkml);
		}
	}

	@FXML // creates a dialog with data.
	protected void handlePresentData(ActionEvent event) {
		ApproxLocationByMac newalgo = new ApproxLocationByMac();
		newalgo.recordsToVector();
		getCsvFile();
		String s = ("Number of data lines:" + csvWriter.getDataCount() 
		+ "\nNumber of MAC addresses:" + newalgo.getMacCount());
		AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Details", s);
	}


	// class for creating alerts.
	private static class AlertHelper {
		public static void showAlert(Alert.AlertType alertType, String title, String message) {
			Alert alert = new Alert(alertType);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(message);
			alert.show();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public String getCsvName() {
		return csvName;
	}

	public void setCsvName(String csvName) {
		this.csvName = csvName;
	}

	public String getCsvPathName() {
		return csvPathName;
	}

	public void setCsvPathName(String csvPathName) {
		this.csvPathName = csvPathName;
	}

	public csvToKml getKmlFile() {
		return kmlFile;
	}

	public void setKmlFile(csvToKml kmlfile) {
		this.kmlFile = kmlfile;
	}

	public csvWriter getCsvFile() {
		return csvFile;
	}

	public void setCsvFile(csvWriter csvFile) {
		this.csvFile = csvFile;
	}

}


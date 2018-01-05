package Gui;

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

public class AlgorithmsTabController {
	@FXML private Pane AlgorithemTab;

	//algo 1
	@FXML private TextField MacBox;
	@FXML private Button InputCheckMacSubmit;
	//algo2
	@FXML private TextField InputCheckString; 
	@FXML private Button InputCheckStringSubmit;

	@FXML private TextField MacBox1;
	@FXML private TextField MacBox2;
	@FXML private TextField MacBox3;
	@FXML private Button InputCheckMac3Submit;

	
	private String MacSource;
	private String StringSource;
	private String Mac1Source;
	private String Mac2Source;
	private String Mac3Source;
	
	//Algorithem 1
	@FXML // submit mac insert
	protected void handleCheckMacSubmit(ActionEvent event) { // handles mac submit.
		if (MacBox.getText().isEmpty()) { // if empty field
			AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Mac.");
			return;
		}
		setMacSource(MacBox.getText());
		AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","Mac inserted.");
	}
	
	//Algorithem 2
	@FXML // submit String  insert 
	protected void handleCheckStringSubmit(ActionEvent event) { // handles mac submit.
		if (InputCheckString.getText().isEmpty()) { // if empty field
			AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter String.");
			return;
		}
		setStringSource(InputCheckString.getText());
		AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","String inserted.");
	}

	@FXML // submit 1 mac insert 
	protected void handleInputCheckMac1Submit(ActionEvent event) { // handles mac submit.
		if (MacBox1.getText().isEmpty()) { // if empty field
			AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Mac.");
			return;
		}
		 setMac1Source(MacBox1.getText());
		AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","First Mac inserted.");
	}
	@FXML // submit 2 mac insert 
	protected void handleInputCheckMac2Submit(ActionEvent event) { // handles mac submit.
		if (MacBox2.getText().isEmpty()) { // if empty field
			AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Mac.");
			return;
		}
		setMac2Source(MacBox2.getText());
		AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","Second Mac inserted.");
	}
	@FXML // submit 3 mac insert 
	protected void handleInputCheckMac3Submit(ActionEvent event) { // handles mac submit.
		if (MacBox3.getText().isEmpty()) { // if empty field
			AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Mac.");
			return;
		}
		setMac3Source(MacBox3.getText());
		AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","Second Mac inserted.");
		
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
		public String getMacSource() {
			return MacSource;
		}
		public void setMacSource(String MacSource) {
			this.MacSource = MacSource;
		}
		public String getStringSource() {
			return StringSource;
		}
		public void setStringSource(String StringSource) {
			this.StringSource = StringSource;
		}
		public String getMac1Source() {
			return Mac1Source;
		}
		public void setMac1Source(String Mac1Source) {
			this.Mac1Source = Mac1Source;
		}
		public String getMac2Source() {
			return Mac1Source;
		}
		public void setMac2Source(String Mac2Source) {
			this.Mac2Source = Mac2Source;
		}
		public String getMac3Source() {
			return Mac1Source;
		}
		public void setMac3Source(String Mac3Source) {
			this.Mac3Source = Mac3Source;
		}
		
		
		
		
		
		
		
		
		
		
}

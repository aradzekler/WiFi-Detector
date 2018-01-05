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

public class AlgorithmsTabController {

	//algo 1
		@FXML private AnchorPane FiltersTab;
                @FXML private CheckBox TimeEnable;
	        @FXML private TextField MacBox;
	//algo2
               @FXML private TextField InputCheckString; 
	       @FXML private TextField MacBox1;
	        @FXML private TextField MacBox2;
	        @FXML private TextField MacBox3;
	
                @FXML private CheckBox LocationEnable
		
		@FXML private Button InputCheckStringSubmit;
		
	
		//Algorithem 1
		@FXML // submit mac insert
		protected void handleInputCheckStringSubmit(ActionEvent event) { // handles mac submit.
			if (InputCheckString.getText().isEmpty()) { // if empty field
				AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Mac.");
				return;
			}
			(InputCheckString.getText());
			AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","Mac inserted.");
		}
		//Algorithem 2
		@FXML // submit 1 mac insert 
		protected void handleInputCheckMac1Submit(ActionEvent event) { // handles mac submit.
			if (InputCheckMac1.getText().isEmpty()) { // if empty field
				AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Mac.");
				return;
			}
			setCsvPathName(InputCheckMac1.getText());
			AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","First Mac inserted.");
		}
		
		@FXML // submit 1 mac insert 
		protected void handleInputCheckMac1Submit(ActionEvent event) { // handles mac submit.
			if (InputCheckMac1.getText().isEmpty()) { // if empty field
				AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Mac.");
				return;
			}
			setCsvPathName(InputCheckMac1.getText());
			AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","First Mac inserted.");
		}
		@FXML // submit 2 mac insert 
		protected void handleInputCheckMac2Submit(ActionEvent event) { // handles mac submit.
			if (InputCheckMac2.getText().isEmpty()) { // if empty field
				AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Mac.");
				return;
			}
			setCsvPathName(InputCheckMac2.getText());
			AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","Second Mac inserted.");
		}
		@FXML // submit 3 mac insert 
		protected void handleInputCheckMac3Submit(ActionEvent event) { // handles mac submit.
			if (InputCheckMac3.getText().isEmpty()) { // if empty field
				AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Mac.");
				return;
			}
			setCsvPathName(InputCheckMac3.getText());
			AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","Third Mac inserted.");
		}
		@FXML // submit signal
		protected void handleInputCheckSignalSubmit(ActionEvent event) { // handles mac submit.
			if (InputSignal.getText().isEmpty()) { // if empty field
				AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Mac.");
				return;
			}
			setCsvPathName(InputSignal.getText());
			AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS"," Signal inserted.");
		}
		
	
	
}

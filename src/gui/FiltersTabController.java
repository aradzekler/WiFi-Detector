/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;



public class FiltersTabController implements Initializable {
    	@FXML private MainController Main;
	// variables from fxml file to inject.
	@FXML private AnchorPane FiltersTab;
        @FXML private CheckBox TimeEnable;
	@FXML private TextField TimeFrom;
        @FXML private TextField UntilTime;
        @FXML private CheckBox LocationEnable;
	@FXML private TextField minAlt;
        @FXML private TextField maxAlt;
        @FXML private TextField minLon;
        @FXML private TextField maxLon;
        @FXML private TextField minLat;
        @FXML private TextField maxLat;
        @FXML private CheckBox DeviceEnable;
        @FXML private TextField DeviceName;
	@FXML private Button FilterSubmit;
	@FXML private Button deleteFIlters;
	@FXML private Button SaveFilterdFile;
        
        private String csvPathName;
        private String csvName;
        private String StartTime;
        private String EndTime;
        private String minimumAlt;
        private String maximumAlt;
        private String minimumLon;
        private String maximumLon;
        private String minimumLat;
        private String maximumLat;
        private String Device;
        
	@FXML 
	protected void handleTimeFilterSubmit(ActionEvent event) { // handles name submit.
		if (TimeFrom.getText().isEmpty()||UntilTime.getText().isEmpty()) { // if empty field
			AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Time.");
			return;
            }
                
        }
        
        	@FXML 
	protected void handleLocationFilterSubmit(ActionEvent event) 
        { // handles name submit.
		if (TimeFrom.getText().isEmpty()||UntilTime.getText().isEmpty()) // if empty field
                { 
			AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Time.");
			return;
                 }
                
        }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    } 
    
    	private static class AlertHelper {
		public static void showAlert(Alert.AlertType alertType, String title, String message) {
			Alert alert = new Alert(alertType);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(message);
			alert.show();
		}
}
    
    	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String TimeFrom) {
		this.StartTime=TimeFrom;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String EndTime) {
		this.EndTime =EndTime ;
	}

	public void setMinAlt(String minimumAlt) {
		this.minimumAlt=minimumAlt;
	}

	public String getMinAlt() {
		return minimumAlt;
	}
        	public void setMinLon(String minimumLon) {
		this.minimumLon=minimumLon;
	}

	public String getMinLon() {
		return minimumLon;
	}
        	public void setMinLat(String minimumLat) {
		this.minimumLat=minimumLat;
	}

	public String getMinLat() {
		return minimumLat;
	}
        	public void setMaxAlt(String maximumAlt) {
		this.maximumAlt=maximumAlt;
	}

	public String getMaxAlt() {
		return maximumAlt;
	}
        	public void setMaxLon(String maximumLon) {
		this.maximumLon=maximumLon;
	}

	public String getMaxLon() {
		return maximumLon;
	}
        
                	public void setMaxLat(String maximumLat) {
		this.maximumLat=maximumLat;
	}

	public String getMaxLat() {
		return maximumLat;
	}
                	public void setDevice(String Device) {
		this.Device=Device;
	}

	public String getDevice() {
		return Device;
	}
}

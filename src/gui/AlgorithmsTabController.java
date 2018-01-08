package gui;

import utils.csvWriter;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import algorithms.ApproxLocationByMac;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class AlgorithmsTabController  implements Initializable {
	@FXML private MainController Main;
	// variables from fxml file to insert.
	@FXML private Pane AlgorithemTab;
	//algo 1
	@FXML private TextField MacBox;
	@FXML private Button InputCheckMacSubmit;
	@FXML private TextField LonBox;
	@FXML private TextField LatBox;
	@FXML private TextField AltBox;
	//algo2
	@FXML private TextField InputCheckString; 
	@FXML private Button InputCheckStringSubmit;

	@FXML private TextField MacBox1;
	@FXML private TextField MacBox2;
	@FXML private TextField MacBox3;
	@FXML private TextField pSignal;
	@FXML private Button InputCheckMac3Submit;

	private String MacSource;
	private String StringSource;
	private String Mac1Source;
	private String Mac2Source;
	private String Mac3Source;
	private String Signal;

	//Algorithem 1
	@FXML // submit mac insert
	protected void handleCheckMacSubmit(ActionEvent event) { // handles mac submit.
		if (MacBox.getText().isEmpty()) { // if empty field
			AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Mac.");
			return;
		}
		setMacSource(MacBox.getText());
		ApproxLocationByMac algo1=new ApproxLocationByMac ();
		algo1.weightedCenterPointFirstAlgo( getMacSource());
		AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","Mac inserted.");
		Vector<Vector<String>> matrix =algo1.getVector();
		LatBox.setText(matrix.get(0).get(1));
		LonBox.setText(matrix.get(0).get(2));
		AltBox.setText(matrix.get(0).get(3));
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

	@FXML // submit 3 Macs +Signal insert 
	protected void handleInputCheckMac1Submit(ActionEvent event) { // handles mac submit.
		if (MacBox1.getText().isEmpty()||MacBox2.getText().isEmpty()||MacBox3.getText().isEmpty()||pSignal.getText().isEmpty()) { // if empty field
			AlertHelper.showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter Mac.");
			return;
		}
		setMac1Source(MacBox1.getText());
		setMac2Source(MacBox2.getText());
		setMac3Source(MacBox3.getText());
		setMac3Source(pSignal.getText());
		AlertHelper.showAlert(Alert.AlertType.INFORMATION, "SUCCESS","Data inserted.");
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
	public String getSignal() {
		return Mac1Source;
	}
	public void setSignal(String Signal) {
		this.Signal = Signal;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
}

package controllers;

import databaseConnection.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends Controller{
	
	@FXML
	private TextField dbURL;
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField pwd;
	
	@FXML
	private Label statusText;
	
	public void bajs() {
		System.out.println("hallo");
	}
	
	public void loginPressed() {
		this.setupDBconnection();
	}
	
	private void setupDBconnection() {
		DatabaseHandler dbh = new DatabaseHandler(dbURL.getText(), username.getText(), pwd.getText());
		try {
			dbh.connect();
			this.getApp().setDBH(dbh);
			statusText.setText("");
		} catch (Exception e) {
			statusText.setText("Something went wrong when trying to connect to DB");
			e.printStackTrace();
		}
	}

}

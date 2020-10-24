package controllers;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import databaseConnection.DatabaseHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import main.App;

public class LoginController extends Controller{
	
	private Executor exec;
	
	@FXML
	private ProgressIndicator connectionProgress;
	
	@FXML
	private TextField dbURL;
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField pwd;
	
	@FXML
	private Label statusText;
	
	@FXML
	private Button loginButton;
	
	@FXML
	public void initialize() {
		loginButton.setDisable(true);
		connectionProgress.setVisible(false);
		
		exec = Executors.newCachedThreadPool(runnable -> {
			Thread t = new Thread (runnable);
			t.setDaemon(true);
			return t;
		});
	}
	
	public void loginPressed() {
		loginButton.setDisable(true);
		String dbName = dbURL.getText();
		String usrname = username.getText();
		String pswrd = pwd.getText();
		App currentApp = this.getApp();
		connectionProgress.setVisible(true);
		
		Task<Void> dbLogin = new Task<Void>() {
			
			@Override
			protected Void call() throws Exception {
				DatabaseHandler dbh = DatabaseHandler.getInstance();
				dbh.connect(dbName, usrname, pswrd);
				return null;
			}
						
		};
		
		dbLogin.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				currentApp.setDash();
				DashController controller = (DashController) currentApp.getCurrentController();
				controller.startup();
				System.out.println(currentApp.getCurrentController().getClass());
			}
			
		});
		
		dbLogin.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				statusText.setText("Couldn't connect");
				System.out.println(dbLogin.getException().toString());
			}
			
		});
		
		connectionProgress.progressProperty().bind(dbLogin.progressProperty());
		dbLogin.runningProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean wasRunning, Boolean isRunning) {
				if(!isRunning) {
					loginButton.setDisable(false);
					connectionProgress.setVisible(false);
				}
				
			}
			
		});
		
		exec.execute(dbLogin);
	}
	
	
	public void onKeyReleased() {
		Boolean disablebutton = username.getText().trim().isEmpty() || dbURL.getText().trim().isEmpty();
		
		loginButton.setDisable(disablebutton);
	}


}

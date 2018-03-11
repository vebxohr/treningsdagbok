package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import databaseConnection.DatabaseHandler;
import javafx.application.Platform;
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
		
//		Oppgave som skal kjøres på bakgrunnstråden
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
				controller.showØkter();
			
				System.out.println(currentApp.getCurrentController().getClass());
			}
			
		});
		
		dbLogin.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				statusText.setText("Couldn't connect");
//				loginButton.setDisable(false);
			
				
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
		
//		Kjører oppgaven på en ny tråd
		exec.execute(dbLogin);
//		setupDBconnection();
//		Task<Boolean> dbTask = new Task<Boolean>() {
//
//			@Override
//			protected Boolean call() throws Exception {
//				setupDBconnection();
//				if (DatabaseHandler.getInstance().isConnected())
//					return true;
//				else 
//					return false;
//			}
//			
//		};
//		
//		dbTask.setOnSucceeded(e -> {
//			try {
//				Boolean bool = dbTask.get();
//				if (bool != null && bool == true) {
//					this.getApp().setDash();
////					DashController controller = (DashController) this.getApp().getCurrentController();
////					controller.showØkter();
//				}
//			} catch (InterruptedException | ExecutionException | IOException | SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		});
//		exec.execute(dbTask);
//		
//		
		
	}
	
//	private void setupDBconnection() {
//		DatabaseHandler dbh = DatabaseHandler.getInstance();
//		try {
//			if (dbh.isConnected()) {
//				statusText.setText("");
//				
//			}	
//			else {
//				dbh.connect(dbURL.getText(), username.getText(), pwd.getText());
//			}
//		} catch (Exception e) {
//			statusText.setText("Something went wrong");
//			e.printStackTrace();
//		}
//		if (dbh.isConnected())
//			try {
//				this.getApp().setDash();
//			} catch (IOException | SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//	}
	
	public void onKeyReleased() {
		Boolean disablebutton = username.getText().trim().isEmpty() || pwd.getText().trim().isEmpty() || dbURL.getText().trim().isEmpty();
		
		loginButton.setDisable(disablebutton);
	}


}

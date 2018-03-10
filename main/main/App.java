package main;

import java.io.IOException;

import controllers.Controller;
import databaseConnection.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
	
	private DatabaseHandler dbh;
	private Stage primaryStage;
	private Controller currentController;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.setLogin();
			
	}
	
	@Override
	public void stop() {
		this.dbh.closeConnection();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
	public void setLogin() {
		try {
			this.setScene(this.loadScene("/resources/Login.fxml"), "Login");
		} catch (IOException e) {
			System.out.println("Couldn't load login");
			e.printStackTrace();
		}
	}

	
    public Parent loadScene(String fxmlFile) throws IOException {
    	Parent parent = null;
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
   
		parent = fxmlLoader.load();
	
    	currentController = fxmlLoader.getController();
    	currentController.setApp(this);
    		 	
    	return parent;
    }
  
    
    public void setScene(Parent parent, String sceneTitle) {
        
    	Scene newScene = new Scene(parent);
//    	newScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    	this.primaryStage.setScene(newScene);
    	this.primaryStage.setTitle(sceneTitle);
    	this.primaryStage.show();
    }
    
    public DatabaseHandler getDBH() {
    	return this.dbh;
    }

	public void setDBH(DatabaseHandler dbh) {
		this.dbh = dbh;
	}


	
	

}

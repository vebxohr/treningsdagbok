package controllers;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

import databaseConnection.DatabaseHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import main.Økt;
import main.Øvelse;
import main.ØvelseIØkt;
import queryHandler.QueryHandler;

public class AddØvelseController extends Controller{
	
	private QueryHandler qh = new QueryHandler();
	
	@FXML
	private ListView<ØvelseIØkt> addedØvelseListe;
	@FXML
	private ListView<Øvelse> øvelseListe;
	@FXML
	private ListView<Øvelse> selectedØvelseListe;
	@FXML
	private Button addSelectedButton;
	@FXML
	private Label selectedØktNavn;
	@FXML
	private TextField kgText;
	@FXML
	private TextField settText;
	@FXML
	private TextField repsText;
	@FXML
	private TextField tidsbrukText;
	@FXML
	private Label dbStatus;
	
	private Øvelse selectedØvelse;
	private Økt selectedØkt;
	
	@FXML
	public void initialize() throws SQLException {
		addSelectedButton.setDisable(true);
		
		øvelseListe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Øvelse>() {
            @Override
            public void changed(ObservableValue<? extends Øvelse> observable, Øvelse oldValue, Øvelse newValue) {
                if(newValue != null) {
                	addSelectedButton.setDisable(false);
        
                    selectedØvelse = øvelseListe.getSelectionModel().getSelectedItem();                 
                }
                else
                	addSelectedButton.setDisable(true);
            }
        });
		
	}
	
	public void startup(Økt økt, ObservableList<Øvelse> øvelser) {
		selectedØkt = økt;
		String øktString = økt.toString();
		selectedØktNavn.setText("Økt: " + øktString);
		ObservableList<ØvelseIØkt> øvelserIØkt = økt.getØvelseIØkt();
		addedØvelseListe.setItems(øvelserIØkt);
		øvelseListe.setItems(øvelser);
	}
	
	@FXML
	public void addSelectedØvelse() {
		if (this.selectedØvelse != null) {
			
			String kgString = kgText.getText();
			String settString = settText.getText();
			String repsString = repsText.getText();
			String timeString = tidsbrukText.getText();
			
			if (kgString.trim().equals("")) 
				kgString = kgString.trim() + 0;
			
			if (settString.trim().equals("")) 
				settString = settString.trim() + 0;
			
			if (repsString.trim().equals(""))
				repsString = repsString.trim() + 0;
			
			if (timeString.trim().equals(""))
				timeString = "00:00:00";
			
			if (DashController.isInteger(kgString) && DashController.isInteger(settString) && DashController.isInteger(repsString) && isTime(timeString)) {
				try {
					int øktID = selectedØkt.getØktID();
					String øvelsenavn = selectedØvelse.getØvelsenavn();
					
					int kg = Integer.parseInt(kgString);
					int sett = Integer.parseInt(settString);
					int reps = Integer.parseInt(repsString);
					Time tidsbruk = Time.valueOf(LocalTime.parse(timeString));
					ØvelseIØkt øvelseIØkt = new ØvelseIØkt(øktID, øvelsenavn, kg, sett, reps, tidsbruk);
					qh.addØvelseIØkt(øktID, øvelseIØkt, DatabaseHandler.getInstance());
					selectedØkt.getØvelseIØkt().add(øvelseIØkt);
					dbStatus.setTextFill(Color.GREEN);
					dbStatus.setText("Øvelse lagt til i databasen");
				} catch (SQLException e) {
					dbStatus.setTextFill(Color.RED);
					dbStatus.setText("Noe gikk galt");
					e.printStackTrace();
				}
			}
		}	
	}
	
	public void removeØvelseFromØkt() {
		
	}
	
	public void addØvelse() {
		
	}
	
	private static Boolean isTime(String timeString) {
		try {
			LocalTime.parse(timeString);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

}

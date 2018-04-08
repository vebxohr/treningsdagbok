package controllers;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import databaseConnection.DatabaseHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import main.Økt;
import main.Øvelse;
import main.ØvelseIØkt;
import queryHandler.QueryHandler;
import utility.Utility;

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
	@FXML
	private Button removeØvelseIØktButton;
	
	
	private Øvelse selectedØvelse;
	private Økt selectedØkt;
	private ØvelseIØkt selectedØvelseIØkt;
	
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
		
		addedØvelseListe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ØvelseIØkt>() {

			@Override
			public void changed(ObservableValue<? extends ØvelseIØkt> observable, ØvelseIØkt oldValue,
					ØvelseIØkt newValue) {
				if(newValue != null) {
					selectedØvelseIØkt = addedØvelseListe.getSelectionModel().getSelectedItem();
					
				}	
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
			String beskrivelseString = selectedØvelse.getBeskrivelse();
			String apparatnavnString = selectedØvelse.getApparat().getApparatnavn();
			
			if (kgString.trim().equals("")) 
				kgString = kgString.trim() + 0;
			
			if (settString.trim().equals("")) 
				settString = settString.trim() + 0;
			
			if (repsString.trim().equals(""))
				repsString = repsString.trim() + 0;
			
			if (timeString.trim().equals(""))
				timeString = "00:00:00";
			
			if (Utility.isInteger(kgString) && Utility.isInteger(settString) && Utility.isInteger(repsString) && Utility.isTime(timeString)) {
				try {
					LocalDate dato = selectedØkt.getDato();
					Time starttid = selectedØkt.getStarttid();
					String øvelsenavn = selectedØvelse.getØvelsenavn();
					
					int kg = Integer.parseInt(kgString);
					int sett = Integer.parseInt(settString);
					int reps = Integer.parseInt(repsString);
					Time tidsbruk = Time.valueOf(LocalTime.parse(timeString));
					ØvelseIØkt øvelseIØkt = new ØvelseIØkt(Date.valueOf(dato), starttid, øvelsenavn, kg, sett, reps, tidsbruk, apparatnavnString, beskrivelseString);
					int resultint = qh.addØvelseIØkt(selectedØkt, øvelseIØkt, DatabaseHandler.getInstance());
					if (resultint == 1) {
						selectedØkt.getØvelseIØkt().add(øvelseIØkt);
						dbStatus.setTextFill(Color.GREEN);
						dbStatus.setText("Øvelse lagt til i databasen");
					} else {
						dbStatus.setTextFill(Color.RED);
						dbStatus.setText("Kunne ikke legge til i DB");
					}
					
				} catch (SQLException e) {
					dbStatus.setTextFill(Color.RED);
					dbStatus.setText("Noe gikk galt");
					e.printStackTrace();
				}
			}
		}	
	}
	
	public void removeØvelseFromØkt() {
		ØvelseIØkt øvelse = selectedØvelseIØkt;
		try {
			System.out.println(øvelse.getØktID());
			int resultInt = qh.deleteØvelseIØkt(selectedØkt, øvelse.getøvelsenavn(), DatabaseHandler.getInstance());
			if (resultInt != 0) {
				selectedØkt.getØvelseIØkt().remove(øvelse);
				dbStatus.setTextFill(Color.GREEN);
				dbStatus.setText("Øvelse slettet fra DB");
			}
			else {
				dbStatus.setTextFill(Color.RED);
				dbStatus.setText("Noe gikk galt");
			}
		
			
		} catch (SQLException e) {
			dbStatus.setTextFill(Color.RED);
			dbStatus.setText("Noe gikk galt");
			e.printStackTrace();
		}
	}
	


}

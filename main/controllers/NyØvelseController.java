package controllers;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import databaseConnection.DatabaseHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import main.Apparat;
import main.Gruppe;
import main.Øvelse;
import queryHandler.QueryHandler;

public class NyØvelseController extends Controller {
	
	@FXML
	TextField øvelsenavnText;
	@FXML
	TextArea beskrivelseText;
	@FXML
	CheckBox apparatCheckBox;
	@FXML
	ListView<Apparat> apparatListe;
	@FXML
	ListView<Gruppe> gruppeListe;
	@FXML
	Button addØvelseButton;
	@FXML
	Label queryStatus;
	@FXML
	RadioButton apparatRadio;
	
	private DashController dashController;
	
	private QueryHandler qh = new QueryHandler();
	
	private Apparat selectedApparat;
	private Gruppe selectedGruppe;
	
	private Gruppe removedAlleGrupper;
	private ObservableList<Gruppe> selectedGrupper;
	
	@FXML
	public void initialize() {
		gruppeListe.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		selectedApparat = null;
		selectedGruppe = null;
		apparatListe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Apparat>() {

			@Override
			public void changed(ObservableValue<? extends Apparat> observable, Apparat oldValue, Apparat newValue) {
				if (newValue != null) {
					selectedApparat = newValue;
				}
				
			}
			
		});
		
		gruppeListe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Gruppe>() {

			@Override
			public void changed(ObservableValue<? extends Gruppe> observable, Gruppe oldValue, Gruppe newValue) {
				if (newValue != null) {
					selectedGruppe = newValue;
					selectedGrupper = gruppeListe.getSelectionModel().getSelectedItems();
				}
				
			}
			
		});
		
	}
	
	public void startup(DashController controller) {
		this.dashController = controller;
		try {
			ObservableList<Apparat> apparater = qh.getApparater(DatabaseHandler.getInstance());
			apparatListe.setItems(apparater);
			
//			ObservableList<Gruppe> grupper = qh.getGrupper(DatabaseHandler.getInstance());
			ObservableList<Gruppe> grupper = dashController.getCurrentGruppe();
			removedAlleGrupper = grupper.remove(0);
			gruppeListe.setItems(grupper);
			
		} catch (SQLException e) {
			queryStatus.setTextFill(Color.RED);
			queryStatus.setText("Kunne ikke oppdatere listene");
			e.printStackTrace();
		}
		this.getApp().getPopupStage().setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				dashController.getCurrentGruppe().add(0, removedAlleGrupper);
				dashController.setAlleØvelserGruppe(removedAlleGrupper);
				
			}
			
		});
	}
	

	public void addØvelsePressed(ActionEvent e) {
		Apparat apparat;
		if (this.apparatRadio.isSelected() && selectedApparat != null) {
			apparat = selectedApparat;
		} else {
			apparat = new Apparat();
		}
		String øvelsenavn = øvelsenavnText.getText();
		String beskrivelse = beskrivelseText.getText();
		
		ObservableList<Gruppe> grupper;
		if (selectedGrupper != null) {
			grupper= selectedGrupper;
		} else {
			queryStatus.setText("Velg grupper");
			e.consume();
			return;
		}
		
		
		Øvelse øvelse = new Øvelse(øvelsenavn, apparat, beskrivelse);

		try {
			int resultInt = qh.addØvelse(DatabaseHandler.getInstance(), øvelse);
			if(resultInt != 0) {
				
				øvelse.setGrupper(grupper);
				for (Gruppe g: grupper) {
					g.getØvelser().add(øvelse);
				}
//				dashController.getalleØvelserList().add(øvelse);
//				gruppenavn.getØvelser().add(øvelse);
//				dashController.getCurrentGruppe().get(dashController.getCurrentGruppe().indexOf(gruppenavn)).getØvelser().add(øvelse);
//				selectedGruppe.getØvelser().add(øvelse);
				this.removedAlleGrupper.getØvelser().add(øvelse);
				
				beskrivelseText.clear();
				øvelsenavnText.clear();
				queryStatus.setTextFill(Color.GREEN);
				queryStatus.setText("Øvelse lagt til i DB");
				
			}else {
				queryStatus.setTextFill(Color.RED);
				queryStatus.setText("Kunne ikke legge inn ny øvelse");
			}
			
			
		} catch (SQLException e1) {
			queryStatus.setTextFill(Color.RED);
			queryStatus.setText("Kunne ikke legge inn ny øvelse");
			e1.printStackTrace();
		}
		
	}
	


}

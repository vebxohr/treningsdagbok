package controllers;

import java.sql.SQLException;

import databaseConnection.DatabaseHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import main.Gruppe;
import main.Øvelse;
import queryHandler.QueryHandler;

public class EndreGruppeController extends Controller{
	
	@FXML
	private ListView <Øvelse> gyldigeØvelser;
	@FXML
	private Label valgtGruppeLabel;
	@FXML
	private Label queryStatus;
	
	private ObservableList<Øvelse> selectedØvelser;
	private QueryHandler qh = new QueryHandler();
	private Gruppe selectedGruppe;
	
	@FXML
	public void initialize() {
		gyldigeØvelser.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		gyldigeØvelser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Øvelse>() {

			@Override
			public void changed(ObservableValue<? extends Øvelse> observable, Øvelse oldValue, Øvelse newValue) {
				if (newValue!=null)
					selectedØvelser = gyldigeØvelser.getSelectionModel().getSelectedItems();
				
			}
			
		});
	}
	public void startup(DashController controller, Gruppe gruppe) {
		
		selectedGruppe = gruppe;
		ObservableList<Øvelse> alleØvelser = FXCollections.observableArrayList(controller.getalleØvelserList());
		
		ObservableList<Øvelse> øvelserIgruppe = gruppe.getØvelser();
		alleØvelser.removeAll(øvelserIgruppe);
		valgtGruppeLabel.setText("Gruppe: " + gruppe.getGruppenavn());
		gyldigeØvelser.setItems(alleØvelser);
	}
	
	public void addPressed() {
		try {
			int resultInt = qh.addØvelserIGruppe(DatabaseHandler.getInstance(), selectedGruppe, selectedØvelser);
			if (resultInt != 0) {
				queryStatus.setText("Øvelse lagt til i gruppe");
				for (Øvelse ø : selectedØvelser) {
					selectedGruppe.getØvelser().add(ø);
					gyldigeØvelser.getItems().remove(ø);
				}
				
			} else {
				queryStatus.setText("Kunne ikke legge til øvelser i gruppen");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}

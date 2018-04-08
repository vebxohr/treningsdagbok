package controllers;

import java.sql.SQLException;

import databaseConnection.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import main.Gruppe;
import queryHandler.QueryHandler;

public class nyGruppeController extends Controller{
	
	@FXML
	private Label nyGruppeStatus;
	@FXML
	private TextField nyGruppeText;
	
	
	private QueryHandler qh = new QueryHandler();
	private DashController dashController;
	
	public void startUp(DashController controller) {
		this.dashController = controller;
		nyGruppeText.clear();
		nyGruppeStatus.setText("");
	}
	
	public void leggTilPressed() {
		String gruppenavn = nyGruppeText.getText().trim();
		if (!gruppenavn.equals("")) {
			Gruppe gruppe = new Gruppe(gruppenavn);
			gruppe.setØvelser(FXCollections.observableArrayList());
			try {
				int resultInt = qh.insertGruppe(DatabaseHandler.getInstance(), gruppe);
				if (resultInt != 0) {
					dashController.getCurrentGruppe().add(gruppe);
					nyGruppeStatus.setTextFill(Color.GREEN);
					nyGruppeStatus.setText("Gruppe lagt til i DB");
				}
				else {
					nyGruppeStatus.setTextFill(Color.RED);
					nyGruppeStatus.setText("Kunne ikke legge til gruppe i DB");
					
				}
			} catch (SQLException e) {
				nyGruppeStatus.setTextFill(Color.RED);
				nyGruppeStatus.setText("Kunne ikke legge til gruppe i DB");
				e.printStackTrace();
			}
		}
	}

}

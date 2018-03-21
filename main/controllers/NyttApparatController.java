package controllers;


import java.sql.SQLException;

import databaseConnection.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.Apparat;
import queryHandler.QueryHandler;

public class NyttApparatController extends Controller{
	
	@FXML
	TextField apparatnavnText;
	@FXML
	TextArea instruksjonerText;
	@FXML
	Label statusLabel;
	
	private QueryHandler qh = new QueryHandler();
	
	public void addPressed(ActionEvent e) {
		if (apparatnavnText.getText().trim().equals("")) {
			statusLabel.setText("Velg et navn");
			e.consume();
		}
			
			
		Apparat apparat = new Apparat(apparatnavnText.getText(),instruksjonerText.getText());
		try {
			int resultInt = qh.addApparat(DatabaseHandler.getInstance(), apparat);
			if (resultInt != 0)
				statusLabel.setText("Apparat lagt til i DB");
			else
				statusLabel.setText("Kunne ikke legge til i DB");
		} catch (SQLException e1) {
			statusLabel.setText("Kunne ikke legge til i DB");
			e1.printStackTrace();
		}
	}

}

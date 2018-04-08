package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import databaseConnection.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import main.Gruppe;
import main.Resultat;
import main.Økt;
import main.Øvelse;
import main.ØvelseIØkt;
import queryHandler.QueryHandler;
import utility.Utility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DashController extends Controller {
	
	
	@FXML
	private Button addØvelse;

	@FXML
	private TextField antallØkter;
	@FXML
	private Button resultatConfirm;
	@FXML
	private DatePicker Sluttdato;
	
	@FXML
	private DatePicker Startdato;
	
	@FXML
	private Label queryStatus;
	@FXML
	private Button addØkt;
	@FXML
	private ListView<Økt> økterList;
	
	
	@FXML
	private TextArea øktNotat;
	@FXML
	private ListView<Øvelse> øvelseListe;
	@FXML
	private TextArea øvelseInfo;
	@FXML
	private Button nyØvelseButton;
	@FXML
	private Button slettØvelseButton;
	@FXML
	private ListView<Gruppe> gruppeListe;
	@FXML
	private Label apparatnavnLabel;
	@FXML
	private Label øvelseQueryStatus;
	@FXML
	private Label øktDBstatus;
	@FXML
	private ListView<Øvelse> resultatØvelseListe;
	@FXML
	private Label resultatLabel;
	@FXML
	private ListView<Resultat> øvelseResultaterList;
	@FXML
	private Label resultatQueryStatus;
	@FXML
	private Label refreshStatus;
	@FXML
	private TextArea apparatInstruksjonText;

	private ObservableList<Øvelse> alleØvelserList;
	private QueryHandler qh = new QueryHandler();	
	private Øvelse selectedØvelse;
	private Gruppe selectedGruppe;
	private Gruppe alleØvelser;
	private Økt selectedØkt;
	private ObservableList<Gruppe> alleGrupperListe;
	
	@FXML
	private ListView<ØvelseIØkt> øvelserIøktList;
	
	@FXML
	public void initialize() {
		addØvelse.setDisable(true);
		addØvelse.setVisible(false);
		økterList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Økt>() {
            @Override
            public void changed(ObservableValue<? extends Økt> observable, Økt oldValue, Økt newValue) {
                if(newValue != null) {
                	addØvelse.setDisable(false);
            		addØvelse.setVisible(true);
                    Økt økt = økterList.getSelectionModel().getSelectedItem();     
                    selectedØkt = økt;
                    øvelserIøktList.setItems(økt.getØvelseIØkt());
                    øktNotat.setText("Varighet:  " + økt.getVarighet() + "\n\nForm:  " + økt.getForm() + "\n\nPrestasjon:  " + økt.getPreastasjon() + "\n\nNotat:  " + økt.getNotat());
                }
            }
        });
		
		
		øvelseListe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Øvelse>() {
            @Override
            public void changed(ObservableValue<? extends Øvelse> observable, Øvelse oldValue, Øvelse newValue) {
                if(newValue != null) {
                	
                	

                	øvelseInfo.setText(newValue.øvelseInfoString());
                	apparatnavnLabel.setText("Apparat: " + newValue.getApparat().getApparatnavn());
                	apparatInstruksjonText.setText("Apparatinstruksjoner:\n" + newValue.getApparat().getInstruksjoner());
        
                    selectedØvelse = øvelseListe.getSelectionModel().getSelectedItem();                 
                }
            }
        });
		
		gruppeListe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Gruppe>() {

			@Override
			public void changed(ObservableValue<? extends Gruppe> observable, Gruppe oldValue, Gruppe newValue) {
				if (newValue != null) {
					Gruppe gruppe = gruppeListe.getSelectionModel().getSelectedItem();
					selectedGruppe = gruppe;
					øvelseListe.setItems(gruppe.getØvelser());
					
					if(newValue != oldValue)
						øvelseInfo.clear();
				}
				
			}
		});
		
		resultatØvelseListe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Øvelse>(){
			
		

			@Override
			public void changed(ObservableValue<? extends Øvelse> observable, Øvelse oldValue, Øvelse newValue) {
				if (newValue != null) {
					øvelseResultaterList.setItems(FXCollections.observableArrayList());
					if (!(Startdato.getValue() == null || Sluttdato.getValue() == null)) {
						resultatQueryStatus.setText("");
						Øvelse øvelse = resultatØvelseListe.getSelectionModel().getSelectedItem();
						ObservableList<Resultat> øvelseresultat = øvelse.getResultater();
						List<Resultat> gyldigeResultat = new ArrayList<Resultat> ();
						for (Resultat r: øvelseresultat) {
							if (r.getDate().isAfter(Startdato.getValue()) && r.getDate().isBefore(Sluttdato.getValue())) {
								gyldigeResultat.add(r);
							}
						}
						
						ObservableList<Resultat> finalResultater = FXCollections.observableArrayList(gyldigeResultat);
						
						øvelseResultaterList.setItems(finalResultater);
					} else {
						resultatQueryStatus.setText("Velg en dato");
					}	
				}	
			}
		});
	}
	
	public ObservableList<Økt> alleØkter(){
		return økterList.getItems();
	}
	
	public void addØkt() throws IOException {
		this.getApp().showPopupStage("/resources/AddØkt.fxml");
		
		AddØktController controller = (AddØktController) this.getApp().getCurrentController();
		controller.startUp(this);
	}
	
	public void setAlleØvelserGruppe(Gruppe gruppe) {
		this.alleØvelser = gruppe;
	}
	
	
	
	public void deleteØvelse(ActionEvent event) {
		if (selectedØvelse != null) {
			String valgtØvelseNavn = selectedØvelse.getØvelsenavn();
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Er du sikker på at du vil slette valg øvelse?");
			alert.setContentText("Trykk OK for å bekrefte eller Cancel for å gå tilbake");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
			try {
				int resultint = qh.deleteØvelse(DatabaseHandler.getInstance(), selectedØvelse);
				if (resultint != 0) {
					ObservableList<Øvelse> alleøvelsergruppeliste= alleØvelser.getØvelser();
					if (isInAlleØvelserListe(alleøvelsergruppeliste) != null)
						alleØvelser.getØvelser().remove(isInAlleØvelserListe(alleøvelsergruppeliste));
					List<Gruppe> gruppeliste = gruppeListe.getItems().stream().collect(Collectors.toList());
					
					
					for (int i = 0; i < gruppeliste.size(); i++) {
						Gruppe g = gruppeliste.get(i);
						List<Øvelse> øvelser = g.getØvelser();
						for (int s = 0; s < øvelser.size();s++) {
							Øvelse ø = øvelser.get(s);
							if (ø.getØvelsenavn().equals(valgtØvelseNavn))
								g.getØvelser().remove(ø);
						}
					}			
					
					øvelseQueryStatus.setTextFill(Color.GREEN);
					øvelseQueryStatus.setText("Øvelse slettet fra DB");
				}
				else {
					øvelseQueryStatus.setTextFill(Color.RED);
					øvelseQueryStatus.setText("Kunne ikke slette øvelse fra DB");
				}
			} catch (SQLException e) {
				øvelseQueryStatus.setTextFill(Color.RED);
				øvelseQueryStatus.setText("Kunne ikke slette øvelse fra DB");
				e.printStackTrace();
				}
			} else
				event.consume();
		}
	}
	
	private Øvelse isInAlleØvelserListe(ObservableList<Øvelse> alleøvelsergruppeliste) {
		for (Øvelse ø : alleøvelsergruppeliste ) {
			if (ø.getØvelsenavn().equals(selectedØvelse.getØvelsenavn()))
				return ø;
					
		}
		return null;
	}
	
	
	
	public void addGruppe() throws IOException {
		this.getApp().showPopupStage("/resources/NyGruppe.fxml");
		nyGruppeController controller = (nyGruppeController) this.getApp().getCurrentController();
		controller.startUp(this);
	}
	
	public void deleteGruppe(ActionEvent event) {
		if (selectedGruppe != null) {
			String selectedGruppenavn = selectedGruppe.getGruppenavn();
			Gruppe valgtGruppe = null;
			for (Gruppe g: alleGrupperListe) {
				if (g.getGruppenavn().equals(selectedGruppenavn)) {
					valgtGruppe = g;
					break;
				}
			}
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Er du sikker på at du vil slette valg gruppe?");
			alert.setContentText("Trykk OK for å bekrefte eller Cancel for å gå tilbake");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					int resultInt = qh.deleteGruppe(DatabaseHandler.getInstance(), selectedGruppe);
					if (resultInt != 0) {
						gruppeListe.getItems().remove(selectedGruppe);
						øvelseQueryStatus.setTextFill(Color.GREEN);
						øvelseQueryStatus.setText("Gruppe fjernet fra DB");
					} else {
						øvelseQueryStatus.setTextFill(Color.RED);
						øvelseQueryStatus.setText("Kunne ikke fjerne gruppe fra DB");
					}
				} catch (SQLException e) {
					øvelseQueryStatus.setTextFill(Color.RED);
					øvelseQueryStatus.setText("Kunne ikke fjerne gruppe fra DB");
					e.printStackTrace();
					}
			} else
				event.consume();
		}
		
	}
	
	public void startup() {

		try {
			alleØvelserList = qh.getAllØvelser(DatabaseHandler.getInstance());
			ObservableList<Gruppe> grupper;
			grupper = qh.getGrupper(DatabaseHandler.getInstance());
			alleØvelser = new Gruppe("Alle øvelser");
			alleØvelser.setØvelser(alleØvelserList);
			grupper.add(0, alleØvelser);
			
			gruppeListe.setItems(grupper);
			alleGrupperListe = grupper;
			
			resultatØvelseListe.setItems(alleØvelserList);
			
			for (Øvelse ø : alleØvelserList) {
				qh.getØvelseResultat(ø, DatabaseHandler.getInstance());
			}
			refreshStatus.setTextFill(Color.GREEN);
			refreshStatus.setText("Oppdatert");
		} catch (SQLException e) {
			refreshStatus.setTextFill(Color.RED);
			refreshStatus.setText("Noe gikk galt");
			e.printStackTrace();
		}
		
	}
	
	public void deleteØkt(ActionEvent e) {
		if (selectedØkt != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Er du sikker på at du vil slette valg økt?");
			alert.setContentText("Trykk OK for å bekrefte eller Cancel for å gå tilbake");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					
					int resultInt = qh.deleteØkt(DatabaseHandler.getInstance(), selectedØkt);
					if (resultInt != 0) {
						øktDBstatus.setTextFill(Color.GREEN);
						øktDBstatus.setText("Økt slettet fra DB");
						økterList.getItems().remove(selectedØkt);
					} else {
						øktDBstatus.setTextFill(Color.RED);
						øktDBstatus.setText("Kunne ikke slette økt fra DB");
					}
				} catch (SQLException e1) {
					øktDBstatus.setTextFill(Color.RED);
					øktDBstatus.setText("Kunne ikke slette økt fra DB");
					e1.printStackTrace();
				}
			} else {
				e.consume();
			}	
		}	
	}
	
	public ObservableList<Gruppe> getCurrentGruppe(){
		return this.gruppeListe.getItems();
	}
	public ObservableList<Øvelse> getalleØvelserList(){
		return this.alleØvelserList;
	}
		
	public void showØvelser() {
		 ObservableList<Øvelse> øvelser;
		try {
			øvelser = qh.getAllØvelser(DatabaseHandler.getInstance());
			øvelseListe.setItems(øvelser);
		} catch (SQLException e) {
			e.printStackTrace();
		}		 
	}
	


	
	public void addØvelsePressed() throws SQLException, IOException {
		Økt økt = økterList.getSelectionModel().getSelectedItem();
		
		ObservableList<Øvelse> øvelser = qh.getAllØvelser(DatabaseHandler.getInstance());
	
		this.getApp().showPopupStage("/resources/AddØvelse.fxml");
		AddØvelseController controller = (AddØvelseController)this.getApp().getCurrentController();
		controller.startup(økt, øvelser);
	}
	
	public void nyØvelsePressed() throws IOException {
		this.getApp().showPopupStage("/resources/NyØvelse.fxml");
		
		NyØvelseController controller = (NyØvelseController) this.getApp().getCurrentController();
		controller.startup(this);
	}
	
	
	public void resultatConfirmPressed() throws SQLException {
		if (Utility.isInteger(antallØkter.getText())) {
			int n = Integer.parseInt(antallØkter.getText());
			ObservableList<Økt> økter = qh.getØktList(DatabaseHandler.getInstance(), n);
			økterList.getItems().clear();
			økterList.setItems(økter);
		}		
	}
	

	public void endreGruppePressed() throws IOException {
		this.getApp().showPopupStage("/resources/EndreGruppe.fxml");
		
		EndreGruppeController controller = (EndreGruppeController) this.getApp().getCurrentController();
		controller.startup(this, selectedGruppe);
	}
	
	public void addApparatPressed() throws IOException {
		this.getApp().showPopupStage("/resources/NyttApparat.fxml");
		
	}
	

	

}

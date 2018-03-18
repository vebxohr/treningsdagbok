package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import databaseConnection.DatabaseHandler;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Gruppe;
import main.Økt;
import main.Øvelse;
import main.ØvelseIØkt;
import queryHandler.QueryHandler;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DashController extends Controller {
	
	private Executor exec;
	
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
	private TableView<Økt> øktTable;
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

	private ObservableList<Øvelse> currentØvelseList;
	
	private ObservableList<Gruppe> currentGruppeList;
	
	private QueryHandler qh = new QueryHandler();
	
	private Service<Void> dbThread;
	
	
	private Stage popupStage;
	
	private Øvelse selectedØvelse;
	
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
                    øvelserIøktList.setItems(økt.getØvelseIØkt());
                    øktNotat.setText("Varighet:  " + økt.getVarighet() + "\n\nForm:  " + økt.getForm() + "\n\nPrestasjon:  " + økt.getPreastasjon() + "\n\nNotat:  " + økt.getNotat());
                }
            }
        });
		
		
		øvelseListe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Øvelse>() {
            @Override
            public void changed(ObservableValue<? extends Øvelse> observable, Øvelse oldValue, Øvelse newValue) {
                if(newValue != null) {
                	String s;
                	

                	øvelseInfo.setText(newValue.øvelseInfoString());
                	apparatnavnLabel.setText("Apparat: " + newValue.getApparatnavn());
        
                    selectedØvelse = øvelseListe.getSelectionModel().getSelectedItem();                 
                }
            }
        });
		
		gruppeListe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Gruppe>() {

			@Override
			public void changed(ObservableValue<? extends Gruppe> observable, Gruppe oldValue, Gruppe newValue) {
				if (newValue != null) {
					Gruppe gruppe = gruppeListe.getSelectionModel().getSelectedItem();
					øvelseListe.setItems(gruppe.getØvelser());
					
					if(newValue != oldValue)
						øvelseInfo.clear();
				}
				
			}
		});

		
		
		exec = Executors.newCachedThreadPool(runnable -> {
			Thread t = new Thread (runnable);
			t.setDaemon(true);
			return t;
		});
	}
	public void deleteØvelse() {
		
	}
	
	public void addGruppe() {
		
	}
	
	public void deleteGruppe() {
		
	}
	
	public void startup() throws SQLException {
		
		currentØvelseList = qh.getAllØvelser(DatabaseHandler.getInstance());
		øvelseListe.setItems(currentØvelseList);
		
		ObservableList<Gruppe> grupper = qh.getGrupper(DatabaseHandler.getInstance());
		Gruppe alleØvelser = new Gruppe("Alle øvelser");
		alleØvelser.setØvelser(currentØvelseList);
		grupper.add(0, alleØvelser);
		currentGruppeList = grupper;
		gruppeListe.setItems(grupper);
	}
	
	public ObservableList<Gruppe> getCurrentGruppe(){
		return this.currentGruppeList;
	}
	public ObservableList<Øvelse> getCurrentØvelseList(){
		return this.currentØvelseList;
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}

	
	public void showØvelser() {
		 ObservableList<Øvelse> øvelser;
		try {
			øvelser = qh.getAllØvelser(DatabaseHandler.getInstance());
			øvelseListe.setItems(øvelser);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	


	
	public void addØvelsePressed() throws SQLException, IOException {
		Økt økt = økterList.getSelectionModel().getSelectedItem();
		
		ObservableList<Øvelse> øvelser = qh.getAllØvelser(DatabaseHandler.getInstance());
		
		popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.initOwner(this.getApp().getPrimaryStage());
		Parent parent = this.getApp().loadScene("/resources/AddØvelse.fxml");
		Scene scene = new Scene(parent);
		popupStage.setScene(scene);
		popupStage.show();
		this.getApp().setPopupStage(popupStage);
	
		this.getApp().setPopupStage(popupStage);
		
		
		AddØvelseController controller = (AddØvelseController)this.getApp().getCurrentController();
		
		System.out.println(controller);
		controller.startup(økt, øvelser);
	}
	
	public void nyØvelsePressed() throws IOException {
		popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.initOwner(this.getApp().getPrimaryStage());
		Parent parent = this.getApp().loadScene("/resources/NyØvelse.fxml");
		Scene scene = new Scene(parent);
		popupStage.setScene(scene);
		popupStage.show();
		this.getApp().setPopupStage(popupStage);
		
		NyØvelseController controller = (NyØvelseController) this.getApp().getCurrentController();
		controller.startup(this);
	}
	
	
	public void resultatConfirmPressed() throws SQLException {
		if (isInteger(antallØkter.getText())) {
			String intString = antallØkter.getText();
			System.out.println(intString);
			int n = Integer.parseInt(antallØkter.getText());
			System.out.println(n);
			ObservableList<Økt> økter = qh.getØktList(DatabaseHandler.getInstance(), n);
			økterList.getItems().clear();
			økterList.setItems(økter);
		}		
	}
	
	public void confirmPressed() {
		LocalDate startDate = Startdato.getValue();
		LocalDate endDate = Sluttdato.getValue();
	}
	
	

}

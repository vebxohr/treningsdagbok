package controllers;

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
	
	private QueryHandler qh = new QueryHandler();
	
	private Service<Void> dbThread;
	
	private List<String> currentØvelseList;
	
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

		
		
//		økterList.setCellFactory(lv -> new ListCell<Økt>() {
//			@Override
//			public void updateItem(Økt økt, boolean empty) {
//				super.updateItem(økt, empty);
//				if (empty) {
//					setText(null);
//				} else {
//					setText(getDisplayText(økt));
//				}
//			}
//
//		
//		});
//		
		
		exec = Executors.newCachedThreadPool(runnable -> {
			Thread t = new Thread (runnable);
			t.setDaemon(true);
			return t;
		});
	}
	
	private void fillØvelserIØkt(Økt økt) {
		øvelserIøktList.getItems().addAll(økt.getØvelseIØkt());
	}
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
//	public void handleClickListView() {
//		Økt økt = økterList.getSelectionModel().getSelectedItem();
//		System.out.println(økt);
//		øktNotat.setText(økt.getNotat());
//		
////		if (økt.getØvelseIØkt().isEmpty())
////			this.setØvelseriØkt(økt.getØktID());
////		øvelseIøktList.getItems().clear();
//		
//
////		øvelserIøktList.getItems().addAll(currentØvelseList);
//	}
	
	private String getDisplayText(Økt økt) {
		return økt.getDatoStarttid();
	}
	
//	public void showØkter() {
//		try {
//			List<Økt> list = qh.getØktList(DatabaseHandler.getInstance());
//			økterList.getItems().addAll(list);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public void showØkter() {
//		
//		
//		Task<List<Økt>> showøktTask = new Task<List<Økt>>() {
//
//			@Override
//			protected List<Økt> call() throws Exception {
//				return qh.getØktList(DatabaseHandler.getInstance());
//			}
//		};
//		
//		showøktTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
//			@Override
//			public void handle(WorkerStateEvent event) {
//				try {
//					økterList.getItems().addAll(showøktTask.get());
//				} catch (InterruptedException | ExecutionException e) {
//					queryStatus.setText("Something went wrong");
//					e.printStackTrace();
//				}
//			}
//		});
//		
//		showøktTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
//
//			@Override
//			public void handle(WorkerStateEvent event) {
//				queryStatus.setText("Something went wrong");
//			}
//			
//		});
//		exec.execute(showøktTask);
//		
////		ØkterList.setCellValueFactory(new PropertyValueFactory<>("datoStarttid"));
//		
////		øktTable.getItems().addAll(qh.getØktList(DatabaseHandler.getInstance()));
//	}
	
//	public void setØvelseriØkt(int øktID) {
//		Task<ObservableList<ØvelseIØkt>> setØvelseIØktTask = new Task<ObservableList<ØvelseIØkt>>() {
//
//			@Override
//			protected ObservableList<ØvelseIØkt> call() throws Exception {
//				// TODO Auto-generated method stub
////				System.out.println("Hallo:" + økt.getØktID());
//				return qh.getØvelseIØktList(DatabaseHandler.getInstance(), øktID);
//				}
//		};
//		
//		setØvelseIØktTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
//
//			@Override
//			public void handle(WorkerStateEvent event) {
//				try {
//					System.out.println(setØvelseIØktTask.get());
////					økt.setØvelseIØkt(setØvelseIØktTask.get());
//				} catch (InterruptedException | ExecutionException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}	
//			}
//		});
//		
//		setØvelseIØktTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
//
//			@Override
//			public void handle(WorkerStateEvent event) {
//				System.out.println("ALWEFKAWELFK");
//				
//			}
//			
//		});
//		
//		exec.execute(setØvelseIØktTask);
//	}
	
	public void addØvelsePressed() throws SQLException {
		Økt økt = økterList.getSelectionModel().getSelectedItem();
		System.out.println(økt + " valgt økt");
		ObservableList<Øvelse> øvelser = qh.getAllØvelser(DatabaseHandler.getInstance());
		System.out.println(øvelser + "valgt øvelser");
		this.getApp().setAddØvelse();
		
		AddØvelseController controller = (AddØvelseController)this.getApp().getCurrentController();
		
		System.out.println(controller);
		controller.startup(økt, øvelser);
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

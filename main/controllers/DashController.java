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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Økt;
import queryHandler.QueryHandler;


public class DashController extends Controller {
	
	private Executor exec;
	
	@FXML
	private DatePicker Sluttdato;
	
	@FXML
	private DatePicker Startdato;
	
	@FXML
	private Label queryStatus;
	
	@FXML
	private TableColumn<String, Økt> ØkterList;
	
	@FXML
	private TableView<Økt> øktTable;
	
	private QueryHandler qh = new QueryHandler();
	
	private Service<Void> dbThread;
	
	@FXML
	public void initialize() {
		ØkterList.setCellValueFactory(new PropertyValueFactory<>("datoStarttid"));
		
		exec = Executors.newCachedThreadPool(runnable -> {
			Thread t = new Thread (runnable);
			t.setDaemon(true);
			return t;
		});
	}
	
	public void showØkter() {
		
		Task<List<Økt>> showøktTask = new Task<List<Økt>>() {

			@Override
			protected List<Økt> call() throws Exception {
				return qh.getØktList(DatabaseHandler.getInstance());
			}
		};
		
		showøktTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				try {
					øktTable.getItems().addAll(showøktTask.get());
				} catch (InterruptedException | ExecutionException e) {
					queryStatus.setText("Something went wrong");
					e.printStackTrace();
				}
			}
		});
		
		showøktTask.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				queryStatus.setText("Something went wrong");
			}
			
		});
		exec.execute(showøktTask);
		
//		ØkterList.setCellValueFactory(new PropertyValueFactory<>("datoStarttid"));
		
//		øktTable.getItems().addAll(qh.getØktList(DatabaseHandler.getInstance()));
	}
	
	public void confirmPressed() {
		LocalDate startDate = Startdato.getValue();
		LocalDate endDate = Sluttdato.getValue();
	}

}

package queryHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import databaseConnection.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Økt;
import main.Øvelse;
import main.ØvelseIØkt;

public class QueryHandler {
	
	
	public ResultSet getØvelseResultat(LocalDate fromDate, LocalDate toDate, String øvelsenavn, DatabaseHandler dbh) throws SQLException {
		String fromDateString = fromDate.toString();
		String toDateString = toDate.toString();
		String query = "SELECT form, prestasjon FROM ((Øvelse NATURAL JOIN ØvelseIØkt) NATURAL JOIN Økt) WHERE DATE(dato) <= '?' AND DATE(dato) >= '?' AND Øvelse.navn = '?';";
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setString(1, toDateString);
		statement.setString(2, fromDateString);
		statement.setString(3, øvelsenavn);
		return statement.executeQuery();
		
	}
	
	public void addØvelseIØkt(int øktID, ØvelseIØkt øvelse, DatabaseHandler dbh) throws SQLException {
		String query = "INSERT INTO ØvelseIØkt VALUES(?, ?, ?, ?, ?, ?);";
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setInt(1, øktID);
		statement.setString(2, øvelse.getøvelsenavn());
		statement.setInt(3, øvelse.getKg());
		statement.setInt(4, øvelse.getSett());
		statement.setInt(5, øvelse.getReps());
		statement.setTime(6, øvelse.getTidsbruk());
		statement.executeUpdate();
	}
	
	public ObservableList<Øvelse> getAllØvelser(DatabaseHandler dbh) throws SQLException{
		String query = "SELECT * FROM Øvelse;";
		PreparedStatement statement = dbh.prepareQuery(query);
		ResultSet result = statement.executeQuery();
		
		List<Øvelse> øvelseList = new ArrayList<>();
		while (result.next()) {
			String øvelsenavn = result.getString("øvelsenavn");
			String gruppenavn = result.getString("gruppenavn");
			Øvelse øvelse = new Øvelse(øvelsenavn, gruppenavn);
			øvelseList.add(øvelse);
		}
		
		return FXCollections.observableArrayList(øvelseList);
		
	}
	
	public int setApparat(String navn, DatabaseHandler dbh) throws SQLException {
		String query = "INSERT INTO Apparat(navn) VALUES(?);";
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setString(1, navn);
		
		return statement.executeUpdate();
	}
	
	
	public ObservableList<Økt> getØktList(DatabaseHandler dbh, int n) throws SQLException {
		String query = "SELECT * FROM Økt ORDER BY dato desc, starttid desc LIMIT ?;";
		String query2 = "SELECT Øvelse.øvelsenavn, kg, sett, reps, tidsbruk FROM (ØvelseIØkt JOIN Øvelse ON Øvelse.øvelsenavn = ØvelseIØkt.øvelsenavn) JOIN Økt ON Økt.ØktID = ØvelseIØkt.ØktID WHERE Økt.ØktID = ?;";
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setInt(1, n);
		ResultSet result = statement.executeQuery();
		
//		ObservableList<ØvelseIØkt> øvelseListe = FXCollections.observableArrayList();
		
		List<Økt> øktList = new ArrayList<>();
		while (result.next()) {
			int øktID = result.getInt("øktID");
			

			LocalDate dato = LocalDate.parse(result.getDate("dato").toString());
			Time starttid = result.getTime("starttid");
			Time varighet = result.getTime("varighet");
			int form = result.getInt("form");
			int prestasjon = result.getInt("prestasjon");
			String notat = result.getString("notat").toString();
			Økt økt = new Økt(øktID, dato, starttid, varighet, form, prestasjon, notat);
			øktList.add(økt);
			
//			øvelseListe = this.getØvelseIØktList(dbh, øktID);
//			økt.setØvelseIØkt(getØvelseIØktList(dbh, øktID));

			
		}
		List<ØvelseIØkt> øvelseListe;

		
		for (int i = 0; i<øktList.size(); i++) {
			øvelseListe = new ArrayList<>();
			Økt økt = øktList.get(i);
			int øktID = økt.getØktID();
			PreparedStatement statement2 = dbh.prepareQuery(query2);
			statement2.setInt(1, øktID);
			ResultSet result2 = statement2.executeQuery();
			
			while (result2.next()) {
				String øvelsenavn = result2.getString("Øvelsenavn");
				int kg = result2.getInt("kg");
				int sett = result2.getInt("sett");
				int reps = result2.getInt("reps");
				Time tidsbruk;
				if (result2.getTime("tidsbruk") == null)
					tidsbruk = Time.valueOf("00:00:00");
				else 
					tidsbruk = result2.getTime("tidsbruk");
//				String øvelse = øvelsenavn;
				ØvelseIØkt øvelse = new ØvelseIØkt(øktID, øvelsenavn, kg, sett, reps, tidsbruk);
				
				øvelseListe.add(øvelse);
			}
//			System.out.println(øvelseListe+ "SADFDSFASDF") ;
			økt.setØvelseIØkt(FXCollections.observableArrayList(øvelseListe));
//			økt.setØvelseIØkt(øvelseListe);
			
			
		}
		return FXCollections.observableArrayList(øktList);
	}
	
//	public ObservableList<ØvelseIØkt> getØvelseIØktList (DatabaseHandler dbh, int øktID) {
//		String query = "SELECT Øvelse.øvelsenavn, kg, sett, reps, tidsbruk FROM (ØvelseIØkt JOIN Øvelse ON Øvelse.øvelsenavn = ØvelseIØkt.øvelsenavn) JOIN Økt ON Økt.ØktID = ØvelseIØkt.ØktID WHERE Økt.ØktID = ?;";
//		
//		ObservableList<ØvelseIØkt> øvelseListe = FXCollections.observableArrayList();
//		
//		try {
//			
//			PreparedStatement statement = dbh.prepareQuery(query);
//			statement.setInt(1, øktID);
//			System.out.println(statement);
//			ResultSet result = statement.executeQuery();
//
//			
//			
//			
//			while (result.next()) {
//				String øvelsenavn = result.getString("øvelsenavn");
//				System.out.println(øvelsenavn);
//				int kg = result.getInt("kg");
//				System.out.println(kg);
//				int sett = result.getInt("sett");
//				System.out.println(sett);
//				int reps = result.getInt("reps");
//				System.out.println(reps);
//				Time tidsbruk;
//				if (result.getTime("tidsbruk") == null)
//					tidsbruk = Time.valueOf("00:00:00");
//				else 
//					tidsbruk = result.getTime("tidsbruk");
//				
//				ØvelseIØkt øvelse = new ØvelseIØkt(øktID, øvelsenavn, kg, sett, reps, tidsbruk);
//				
//				øvelseListe.add(øvelse);
//				System.out.println("BAJSKORV");
//			}
////			System.out.println(øvelseListe);
//		} catch (SQLException e) {
//			System.out.println("REKT");
//			e.printStackTrace();
//		}
//		System.out.println(øvelseListe);
//		
//		return øvelseListe;
//	}
	
	public static void main(String[] args) {
		
		System.out.println(LocalDateTime.now());
	}
	
	
	
	
	
	

}

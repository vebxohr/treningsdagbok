package queryHandler;

import java.sql.Date;
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
import main.Apparat;
import main.Gruppe;
import main.Resultat;
import main.Økt;
import main.Øvelse;
import main.ØvelseIØkt;

public class QueryHandler {
	
	
	public void getØvelseResultat(Øvelse øvelse, DatabaseHandler dbh) throws SQLException {
		String øvelsenavn = øvelse.getØvelsenavn();

		String query = "SELECT DISTINCT form, prestasjon, øvelsenavn, notat, dato FROM ((Øvelse NATURAL JOIN ØvelseIØkt) NATURAL JOIN Økt) WHERE Øvelse.øvelsenavn = ?;";
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setString(1, øvelsenavn);
		ResultSet result = statement.executeQuery();
		
		ObservableList<Resultat> resultater = FXCollections.observableArrayList();
		while (result.next()) {
			int form = result.getInt("form");
			int prestasjon = result.getInt("prestasjon");
			
			String notat = result.getString("notat");
			LocalDate date = result.getDate("dato").toLocalDate();
			
			Resultat resultat = new Resultat(form, prestasjon, notat, date);
			resultater.add(resultat);
		}
		øvelse.setResultater(resultater);
		
	}
	
	public int deleteØkt(DatabaseHandler dbh, Økt økt) throws SQLException {
		String query = "DELETE FROM Økt WHERE dato = ? AND starttid = ?;";
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setDate(1, Date.valueOf(økt.getDato()));
		statement.setTime(2, økt.getStarttid());
		return statement.executeUpdate();
	}
	
	public int addØkt(DatabaseHandler dbh, Økt økt) throws SQLException {
		String query = "INSERT INTO ØKT(dato, starttid, varighet, form, prestasjon, notat) VALUES(?, ?, ?, ?, ?, ?);";
		PreparedStatement statement = dbh.prepareQuery(query);
		Date date = Date.valueOf(økt.getDato());
		statement.setDate(1, date);
		statement.setTime(2, økt.getStarttid());
		statement.setTime(3, økt.getVarighet());
		statement.setInt(4, økt.getForm());
		statement.setInt(5, økt.getPreastasjon());
		statement.setString(6, økt.getNotat());
		
		return statement.executeUpdate();
		
		
	}
	
	public int deleteGruppe(DatabaseHandler dbh, Gruppe gruppe) throws SQLException {
		String query = "DELETE FROM Gruppe WHERE gruppenavn = ?;";
		String gruppenavn = gruppe.getGruppenavn();
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setString(1, gruppenavn);
		return statement.executeUpdate();
	}
	
	
	
	public int insertGruppe(DatabaseHandler dbh, Gruppe gruppe) throws SQLException {
		String query = "INSERT INTO Gruppe VALUES(?);";
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setString(1, gruppe.getGruppenavn());
		System.out.println(statement);
		return statement.executeUpdate();
	}
	
	public int deleteØvelse(DatabaseHandler dbh, Øvelse øvelse) throws SQLException {
		String query = "DELETE FROM Øvelse WHERE øvelsenavn = ?;";
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setString(1, øvelse.getØvelsenavn());
		return statement.executeUpdate();
	}
	
	public ObservableList<Gruppe> getGrupper(DatabaseHandler dbh) throws SQLException{
		String query = "SELECT * FROM Gruppe;";
		String query2 = "SELECT Gruppe.gruppenavn, Øvelse.øvelsenavn, Apparat.apparatnavn, beskrivelse, instruksjoner FROM  "
				+ "((Gruppe join ØvelseIGruppe on Gruppe.gruppenavn = ØvelseIGruppe.gruppenavn) "
				+ "join Øvelse on Øvelse.øvelsenavn = ØvelseIGruppe.øvelsenavn) LEFT JOIN Apparat ON Apparat.apparatnavn = Øvelse.apparatnavn"
				+ " WHERE ØvelseIGruppe.gruppenavn = ?;";
		
		PreparedStatement statement = dbh.prepareQuery(query);
		
		ResultSet result = statement.executeQuery();
		
		List<Gruppe> grupper = new ArrayList<>();
		while(result.next()) {
			String gruppenavn = result.getString("gruppenavn");
			Gruppe gruppe = new Gruppe(gruppenavn);
			
			grupper.add(gruppe);
		}
		
		List<Øvelse> øvelseListe=new ArrayList<>();
		for (Gruppe g : grupper) {
			
			
			String gruppenavn = g.getGruppenavn();
			PreparedStatement statement2 = dbh.prepareQuery(query2);
			statement2.setString(1, gruppenavn);
			ResultSet result2 = statement2.executeQuery();
			
			while (result2.next()) {
				String øvelsenavn = result2.getString("øvelsenavn");
				Apparat apparat;
				String apparatnavn = result2.getString("apparatnavn");
				String instruksjoner = result2.getString("instruksjoner");
				if (apparatnavn == null)
					apparat = new Apparat();
				else
					apparat = new Apparat(apparatnavn, instruksjoner);
				String beskrivelse = result2.getString("beskrivelse");
				
				Øvelse øvelse = new Øvelse(øvelsenavn, apparat, beskrivelse);
				øvelse.getGrupper().add(g);
				
				øvelseListe.add(øvelse);
			}
		}
		
		for (Øvelse ø : øvelseListe) {
			for (Gruppe g: ø.getGrupper()) {
				g.getØvelser().add(ø);
			}
		}
		
		return FXCollections.observableArrayList(grupper);
	}
	
	
	
	public int addØvelseIØkt(Økt økt, ØvelseIØkt øvelse, DatabaseHandler dbh) throws SQLException {
		String query = "INSERT INTO ØvelseIØkt VALUES(?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setDate(1, Date.valueOf(økt.getDato()));
		statement.setTime(2, økt.getStarttid());
		statement.setString(3, øvelse.getøvelsenavn());
		statement.setInt(4, øvelse.getKg());
		statement.setInt(5, øvelse.getSett());
		statement.setInt(6, øvelse.getReps());
		statement.setTime(7, øvelse.getTidsbruk());
	
		return statement.executeUpdate();
	}
	
	public int deleteØvelseIØkt(Økt økt, String øvelsenavn, DatabaseHandler dbh) throws SQLException {
		String query = "DELETE FROM ØvelseIØkt WHERE øvelsenavn = ? and dato = ? and starttid = ?;";
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setString(1, øvelsenavn);
		statement.setDate(2, Date.valueOf(økt.getDato()));
		statement.setTime(3, økt.getStarttid());
		return statement.executeUpdate();
		
	}
	

	public ObservableList<Øvelse> getAllØvelser(DatabaseHandler dbh) throws SQLException{
		String query = "SELECT Øvelse.øvelsenavn, beskrivelse, Apparat.apparatnavn, instruksjoner FROM Øvelse "
				+ "Left JOIN Apparat on Apparat.apparatnavn = Øvelse.apparatnavn;";
		PreparedStatement statement = dbh.prepareQuery(query);
		ResultSet result = statement.executeQuery();
		
		List<Øvelse> øvelseList = new ArrayList<>();
		while (result.next()) {
			String øvelsenavn = result.getString("øvelsenavn");
			String apparatnavn = result.getString("apparatnavn");
			String beskrivelse = result.getString("beskrivelse");
			String instruksjoner = result.getString("instruksjoner");
			if (instruksjoner == null)
				instruksjoner = "";
			Apparat apparat;
			if(apparatnavn == null)
				apparat = new Apparat();
			else
				apparat = new Apparat(apparatnavn, instruksjoner);
			Øvelse øvelse = new Øvelse(øvelsenavn, apparat, beskrivelse);
			øvelseList.add(øvelse);
		}
		
		return FXCollections.observableArrayList(øvelseList);
		
	}
	
	public int addApparat(DatabaseHandler dbh, Apparat apparat) throws SQLException {
		String query = "INSERT INTO Apparat VALUES(?, ?)";
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setString(1, apparat.getApparatnavn());
		statement.setString(2, apparat.getInstruksjoner());
		return statement.executeUpdate();
	}
	
	public int addØvelse(DatabaseHandler dbh, Øvelse øvelse) throws SQLException {
		String øvelsenavn = øvelse.getØvelsenavn();
		String beskrivelse = øvelse.getBeskrivelse();
		String apparatnavn = øvelse.getApparat().getApparatnavn();
		if (apparatnavn.trim().equals(""))
			apparatnavn = null;
		
		String query = "INSERT INTO Øvelse VALUES(?, ?, ?);";
		String query2 = "INSERT INTO ØvelseIGruppe VALUES(?, ?);";
		PreparedStatement statement = dbh.prepareQuery(query);
		
		statement.setString(1, øvelsenavn);
		statement.setString(2, beskrivelse);
		statement.setString(3, apparatnavn);
		int resultInt = statement.executeUpdate();
		for (Gruppe g: øvelse.getGrupper()) {
			PreparedStatement statement2 = dbh.prepareQuery(query2);
			statement2.setString(1, g.getGruppenavn());
			statement2.setString(2, øvelse.getØvelsenavn());
			int resultInt2 = statement2.executeUpdate();
		}
		
		return resultInt;
				
	}
	
	public int setApparat(String navn, DatabaseHandler dbh) throws SQLException {
		String query = "INSERT INTO Apparat(navn) VALUES(?);";
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setString(1, navn);
		
		return statement.executeUpdate();
	}
	
	public ObservableList<Økt> getØktList(DatabaseHandler dbh, int n) throws SQLException {
		String query = "SELECT * FROM Økt ORDER BY dato desc, starttid desc LIMIT ?;";
		String query2 = "SELECT Øvelse.øvelsenavn, kg, sett, reps, tidsbruk, apparatnavn, "
				+ "beskrivelse FROM (ØvelseIØkt JOIN Øvelse ON Øvelse.øvelsenavn = ØvelseIØkt.øvelsenavn) "
				+ "JOIN Økt ON Økt.dato = ØvelseIØkt.dato AND Økt.starttid = ØvelseIØkt.starttid WHERE Økt.dato = ? AND Økt.starttid = ?;";
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setInt(1, n);
		ResultSet result = statement.executeQuery();
		
		List<Økt> øktList = new ArrayList<>();
		while (result.next()) {
			
			

			LocalDate dato = LocalDate.parse(result.getDate("dato").toString());
			Time starttid = result.getTime("starttid");
			Time varighet = result.getTime("varighet");
			int form = result.getInt("form");
			int prestasjon = result.getInt("prestasjon");
			String notat = result.getString("notat").toString();
			Økt økt = new Økt(dato, starttid, varighet, form, prestasjon, notat);
			øktList.add(økt);
			
		}
		List<ØvelseIØkt> øvelseListe;

		
		for (int i = 0; i<øktList.size(); i++) {
			øvelseListe = new ArrayList<>();
			Økt økt = øktList.get(i);
			
			PreparedStatement statement2 = dbh.prepareQuery(query2);
			statement2.setDate(1, Date.valueOf(økt.getDato()));
			statement2.setTime(2, økt.getStarttid());
			ResultSet result2 = statement2.executeQuery();
			
			while (result2.next()) {
				String øvelsenavn = result2.getString("øvelsenavn");
				int kg = result2.getInt("kg");
				int sett = result2.getInt("sett");
				int reps = result2.getInt("reps");
				String apparatnavn = result2.getString("apparatnavn");
				String beskrivelse = result2.getString("beskrivelse");
				
				if (apparatnavn == null)
					apparatnavn = "";
				Time tidsbruk;
				if (result2.getTime("tidsbruk") == null)
					tidsbruk = Time.valueOf("00:00:00");
				else 
					tidsbruk = result2.getTime("tidsbruk");
				ØvelseIØkt øvelse = new ØvelseIØkt(Date.valueOf(økt.getDato()), økt.getStarttid(), øvelsenavn, kg, sett, reps, tidsbruk, apparatnavn, beskrivelse);
				
				øvelseListe.add(øvelse);
			}

			økt.setØvelseIØkt(FXCollections.observableArrayList(øvelseListe));		
		}
		return FXCollections.observableArrayList(øktList);
	}
	
	
	public static void main(String[] args) {
		
		System.out.println(LocalDateTime.now());
	}

	public ObservableList<Apparat> getApparater(DatabaseHandler dbh) throws SQLException {
		String query = "SELECT * FROM Apparat;";
		
		PreparedStatement statement = dbh.prepareQuery(query);
		ResultSet result = statement.executeQuery();
		
		List<Apparat> apparater = new ArrayList<>();
		while (result.next()) {
			String apparatnavn = result.getString("apparatnavn");
			String instruksjoner = result.getString("instruksjoner");
			Apparat apparat = new Apparat(apparatnavn, instruksjoner);
			apparater.add(apparat);
		}
		
		return FXCollections.observableArrayList(apparater);
		
	}

	public int addØvelserIGruppe(DatabaseHandler dbh, Gruppe gruppe, ObservableList<Øvelse> selectedØvelser) throws SQLException {
		String query = "INSERT INTO ØvelseIGruppe VALUES (?,?)";
		String gruppenavn = gruppe.getGruppenavn();
		
		int i = 0;
		for (Øvelse ø : selectedØvelser) {
			PreparedStatement statement = dbh.prepareQuery(query);
			statement.setString(1, gruppenavn);
			statement.setString(2, ø.getØvelsenavn());
			i += statement.executeUpdate();
		}
		if (i == selectedØvelser.size()) {
			return 1;
		} else
			return 0;
	}
	
	
	
	
	
	

}

package queryHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import databaseConnection.DatabaseHandler;
import main.Økt;

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
	
	public int setApparat(String navn, DatabaseHandler dbh) throws SQLException {
		String query = "INSERT INTO Apparat(navn) VALUES(?);";
		PreparedStatement statement = dbh.prepareQuery(query);
		statement.setString(1, navn);
		
		return statement.executeUpdate();
	}
	
	
	public List<Økt> getØktList(DatabaseHandler dbh) throws SQLException {
		String query = "SELECT * FROM Økt;";
		PreparedStatement statement = dbh.prepareQuery(query);
		ResultSet result = statement.executeQuery();
		
		List<Økt> øktList = new ArrayList<>();
		while (result.next()) {
			String dato = result.getDate("dato").toString();
			String starttid = result.getTime("starttid").toString();
			String varighet = result.getTime("varighet").toString();
			int form = result.getInt("form");
			int prestasjon = result.getInt("prestasjon");
			String notat = result.getString("notat").toString();
			Økt økt = new Økt(dato, starttid, varighet, form, prestasjon, notat);
			øktList.add(økt);
		}
		return øktList;
	}
	
	public static void main(String[] args) {
		
		System.out.println(LocalDateTime.now());
	}
	
	
	
	
	
	

}

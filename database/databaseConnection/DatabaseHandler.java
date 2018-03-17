package databaseConnection;

import java.sql.*;

public class DatabaseHandler {
	private static DatabaseHandler instance = new DatabaseHandler();
	
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private String DB_url;
	
	private String user;
	private String pwd;
	
	private Connection connection;
	
	private DatabaseHandler() {
		
	}
	
	public static DatabaseHandler getInstance() {
		return instance;
	}
	
	
	
	public void connect(String mysql_database, String user, String pwd) throws Exception {
		this.DB_url = "jdbc:mysql://" + mysql_database;
//				mysql.stud.ntnu.no/" + mysql_NtnuDb_Name;
		this.user = user;
		this.pwd = pwd;
		Class.forName(this.JDBC_DRIVER).newInstance();
		connection = DriverManager.getConnection(DB_url,this.user,this.pwd);
		System.out.println("Connected to DB");	
	}
	
	public Boolean isConnected() {
		try {
			return !(this.connection == null || this.connection.isClosed());
		} catch (SQLException e) {
			return false;
		}
	}
	
	public void closeConnection() {
		try {
			this.connection.close();
			System.out.println("DB connection closed");
		} catch (SQLException e) {
			System.out.println("Couldn't close connection");
			e.printStackTrace();
		}
	}
	
	public PreparedStatement prepareQuery(String query) throws SQLException {
		return this.connection.prepareStatement(query);
	}
	
	

	
	
	
	public static void main(String[] args) throws Exception {
		DatabaseHandler dbcon = DatabaseHandler.getInstance();
		dbcon.connect("vebjoroh_Treningsdagbok", "vebjoroh", "abecederisk");
		System.out.println(dbcon.isConnected());
	
		dbcon.closeConnection();
		
		System.out.println(dbcon.isConnected());
		
	}

}

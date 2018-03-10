package databaseConnection;

import java.sql.*;

public class DatabaseHandler {
	
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private String DB_url;
	
	private String user;
	private String pwd;
	
	private Connection connection;
	
	public DatabaseHandler(String mysql_NtnuDb_Name, String user, String pwd) {
		this.DB_url = "jdbc:mysql://mysql.stud.ntnu.no/" + mysql_NtnuDb_Name;
		this.user = user;
		this.pwd = pwd;
	}
	
	public void connect() throws Exception {
		Class.forName(this.JDBC_DRIVER).newInstance();
		connection = DriverManager.getConnection(DB_url,this.user,this.pwd);
		System.out.println("Connected to DB");
		
			
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
	
	
	
	public static void main(String[] args) {
		DatabaseHandler dbcon = new DatabaseHandler("vebjoroh_Treningsdagbok", "vebjoroh", "abecederisk");
		
		try {
			dbcon.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbcon.closeConnection();
		
	}

}

package at.fh.technikum.wien.koller.krammer.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {	
	private static Connection instance = null;
	private static DatabaseConnection dbc = null;

	public static DatabaseConnection getInstance() {
		if (dbc == null) {
			dbc = new DatabaseConnection();
		}
		return dbc;
	}

	public static Connection getConnection(String connectionString) {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			instance = DriverManager.getConnection(connectionString);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return instance;
	}

	public void closeConnection() {
		if (instance != null) {
			try {
				instance.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

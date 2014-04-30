package at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.temperatur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SensorDao {

	// private static final String URL =
	// "jdbc:oracle:thin:temperatureplugin@//localhost:1521/xe";
	private static final String URL = "jdbc:mysql://localhost/temperatureplugin";

	private static final String USER = "tempplugin";
	private static final String PASSWD = "123456";

	private Connection getConnection() {
		try {
			// Class.forName("oracle.jdbc.OracleDriver");
			Class.forName("com.mysql.jdbc.Driver");

			return DriverManager.getConnection(URL, USER, PASSWD);

		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public void createSensorData(SensorData data) {
		try {

			Connection con = getConnection();

			PreparedStatement stmt = con
					.prepareStatement("insert into sensorData (value, time) values (?, ?);");

			stmt.setDouble(1, data.getValue());
			stmt.setTimestamp(2, new Timestamp(data.getTime()));

			stmt.execute();

			stmt.close();
			con.close();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public List<SensorData> loadData(int year, int month, int day) {
		List<SensorData> result = new ArrayList<SensorData>();
		Timestamp from = getStartOfDay(year, month, day);
		Timestamp to = getStartOfDay(year, month, day + 1);

		try {

			Connection con = getConnection();

			PreparedStatement stmt = con
					.prepareStatement("select id, value, time from sensorData where time >= ? and time < ?;");

			stmt.setTimestamp(1, from);
			stmt.setTimestamp(2, to);

			stmt.execute();

			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				SensorData data = new SensorData();
				data.setId(rs.getLong("id"));
				data.setValue(rs.getDouble("value"));
				data.setTime(rs.getTimestamp("time").getTime());
				result.add(data);
			}

			stmt.close();
			con.close();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}

		return result;
	}

	private Timestamp getStartOfDay(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

}

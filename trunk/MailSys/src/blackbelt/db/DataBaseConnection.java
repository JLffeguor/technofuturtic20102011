package blackbelt.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
	
	public static Connection conn;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/mailing?user=root&password=123");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}

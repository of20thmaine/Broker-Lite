package brokerlite;

import java.sql.*;

public class SqlConnect {
	
	public static Connection connector() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:BrokerLite.sqlite");
			return conn;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}

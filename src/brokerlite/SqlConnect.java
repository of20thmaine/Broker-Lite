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
	
	//Added by Sam
	public static void createNewDatabase(String filename) {
		
		//Did not put in try statement because this should only execute if the connection
		//established earlier.
		String url = "jdbc:sqlite:BrokerLite.sqlite";
		
		DatabaseMetaData meta = conn.getMetaData();
		System.out.println("The driver name is "+ meta.getDriverName());
		System.out.println("Created a new database.");
	} 
	
	//Added by Sam
	public static void createTables(String filename) {
		String sql = "source "+filename+";";
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
		System.out.println("Successfully added tables to the database.")

	}
}

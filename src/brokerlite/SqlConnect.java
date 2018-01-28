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

	// I commented out this test code to test some methods below. I don't think create database code will be
	// necessary for project, but this is best class for "createTable" so I added create table method for adding to
	// currently existing database.
	
//	//Added by Sam
//	public static void createNewDatabase(String filename) {
//		
//		//Did not put in try statement because this should only execute if the connection
//		//established earlier.
//		String url = "jdbc:sqlite:BrokerLite.sqlite";
//		
//		DatabaseMetaData meta = conn.getMetaData();
//		System.out.println("The driver name is "+ meta.getDriverName());
//		System.out.println("Created a new database.");
//	} 
//	
//	//Added by Sam
//	public static void createTables(String filename) {
//		String sql = "source "+filename+";";
//		Statement stmt = conn.createStatement();
//		stmt.execute(sql);
//		System.out.println("Successfully added tables to the database.")
//	
//	}
	

	/**
	 * Adds tables to our "BrokerLite.sql" database. Input "createQuery" must be in proper SQL format
	 * for what goes between CREATE TABLE and final ";" , in example:
	 * contacts (contact_id integer PRIMARY KEY, first_name text NOT NULL, last_name text NOT NULL, email text NOT NULL UNIQUE,
 	 * phone text NOT NULL UNIQUE) ~ that is, name of table and everything in parentheses. 
	 * @param createQuery: Legal SQL Command in format described above.
	 * @throws SQLException: Catches connection issues with database.
	 */
	public void createTable(String createQuery) throws SQLException {
		Connection conn = SqlConnect.connector();
		
		if(conn != null) {
			String createString = "CREATE TABLE [IF NOT EXISTS] " + 
					createQuery + ";";
			
			Statement stmt = null;
			
		    try {
		        stmt = conn.createStatement();
		        stmt.executeUpdate(createString);
		        System.out.println("Table succesfully added to database.");
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        if (stmt != null) { stmt.close(); }
		    }
		
		} else {
			System.out.println("Failed to create table.");
		}

	}
}

package brokerlite;

import java.sql.*;

/**
 * @author Bobby Palmer, Samnang Pann
 * Class allows for SQL-Lite database connection via JDBC.
 */
public class SqlConnect {
	
	/**
	 * Returns the valid connection interface to database.
	 * @return
	 */
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

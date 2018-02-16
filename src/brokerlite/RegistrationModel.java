package brokerlite;

import java.sql.*;

public class RegistrationModel {
	
	private Connection connection;
	
	public RegistrationModel() {
		connection = SqlConnect.connector();

		if(connection == null) {
			System.exit(1);
		}
	
	}
	
	public boolean isDbConnected() {
		try {
			return !connection.isClosed();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void isComplete(String lname, String fname, int phoneNum, String email, String address, int cash) throws SQLException {
		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		String query = "INSERT INTO client VALUES ('"+lname+"','"+fname+"','"+phoneNum+"','"+email+"','"+address+"',"+cash+");";
		String query = "INSERT INTO people (id, last_name, first_name,email) "
				+ "VALUES (?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, 99999);
			preparedStatement.setString(2, lname);
			preparedStatement.setString(3, fname);
			preparedStatement.setString(4, email);
			System.out.println("The query is the issue");
			preparedStatement.execute();
			System.out.println("Success");
		} catch(Exception e) {
			
			e.printStackTrace();
		} finally {
			preparedStatement.close();
		}
//		System.out.println("Success");
	}

}

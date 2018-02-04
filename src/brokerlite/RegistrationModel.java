package brokerlite;

import java.sql.*

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
	
	public boolean isComplete(String lname, String fname, String phoneNum, String email, String address, String cash) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "INSERT INTO client VALUES ('"+lname+"','"+fname+"','"+phoneNum+"','"+email+"','"+address+"',"+cash+");";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			System.out.println("Success");
		} catch(Exception e) {
			return false;
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}

}

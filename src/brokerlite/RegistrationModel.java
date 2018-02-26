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
	
	public void isComplete(String lname, String fname, int phoneNum, String email, String address) throws SQLException {
		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
		String query = "INSERT INTO broker (last_name, first_name, phone_num, email, address) VALUES (?,?,?,?,?)";
		
		/* Need to be able to get broker ID from NewUser registration and pass it into here */
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, lname);
			preparedStatement.setString(2, fname);
			preparedStatement.setInt(3, phoneNum);
			preparedStatement.setString(4, email);
			preparedStatement.setString(5, address);
			preparedStatement.execute();
//			System.out.println("Success");
		} catch(Exception e) {
			
			e.printStackTrace();
		} finally {
			preparedStatement.close();
		}
//		System.out.println("Success");
	}

}

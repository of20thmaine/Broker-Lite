package brokerlite;

import java.sql.*;

public class LoginModel {
	
	private Connection connection;
	private String userData = "";
	
	public LoginModel() {
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
	
	public boolean isLogin(String user, String password) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM Users WHERE Username = ? and Password = ?";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, password);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				String firstName = resultSet.getString(3);
				String lastName = resultSet.getString(4);
				this.userData = firstName + " " + lastName;
				return true;
			} else {
				return false;
			}
					
		} catch(Exception e) {
			return false;
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}
	
	public String getUser() {
		return userData;
	}

}

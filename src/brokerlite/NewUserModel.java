package brokerlite;

import java.sql.*;

public class NewUserModel {
	
	private Connection connection;
		
	public NewUserModel() {
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
	
	public void createUser(String username, String pw, String pwVerify) throws SQLException {
		PreparedStatement ps = null;
		String query = "INSERT INTO credentials (username, password) VALUES (?,?)";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, pw);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ps.close();
		}
	}
}

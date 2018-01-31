package brokerlite;

import java.sql.*;
import java.util.ArrayList;

public class CustomerModel {
	
	private Connection connection;
	private ArrayList<Customer> customers = new ArrayList<Customer>();

	public CustomerModel() {
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
	
	public ArrayList<Customer> getCustomers(String userName) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT FirstName, LastName, Cash FROM Customers"
				+ " WHERE Username = '" + userName + "';";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				customers.add(new Customer(
						resultSet.getString("FirstName"),
						resultSet.getString("LastName"),
						resultSet.getInt("Cash")
						));
			}
			
			return customers;
			
		} catch(Exception e) {
			return customers;
		} 
	}

}

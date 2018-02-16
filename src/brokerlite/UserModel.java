package brokerlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {
	
	private int userId;
	private String userName, firstName, lastName, email, address, phoneNumber;
	private ArrayList<Customer> customers = new  ArrayList<Customer>();
	private Connection connection;
	
	public UserModel() {
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
	
	public boolean authenticateUser(String user, String password) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM credentials WHERE username = ? and password = ?;";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, password);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				userName = resultSet.getString(1);
				userId = resultSet.getInt(4);
				
				this.populateCustomers();
				this.populateUserData();
				
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
	
	private void populateCustomers() throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM client WHERE id IN (SELECT client_id FROM relationship WHERE broker_id = ?);";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				int cId = resultSet.getInt(1);
				String clN = resultSet.getString(2);
				String cfN = resultSet.getString(3);
				String pN = resultSet.getString(4);
				String email = resultSet.getString(5);
				String add = resultSet.getString(6);
				int cash = resultSet.getInt(7);
				
				customers.add(new Customer(cId, clN, cfN, pN, email, add, cash));
			} 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}
	
	private void populateUserData() throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM broker WHERE id= ?;";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				firstName = resultSet.getString(3);
				lastName = resultSet.getString(2);
				email = resultSet.getString(5);
				address = resultSet.getString(6);
				phoneNumber = resultSet.getString(4);
			} 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}
	
	// WIll add customer registration here future.
	public void registerCustomer() {
		
	}
	
	public ArrayList<Customer> getCustomers() {
		return customers;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getEmail() {
		return email;
	}
	
}

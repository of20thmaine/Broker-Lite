package brokerlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Bobby Palmer, Samnang Pann
 * Class communicates between application and database for User-related transactions.
 */
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
	
	/**
	 * Verifies connection status of database.
	 * @return
	 */
	public boolean isDbConnected() {
		try {
			return !connection.isClosed();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Allows new users to be added to database.
	 * @param user
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean createUser(String user, String password) throws SQLException {
		PreparedStatement ps = null;
		String query = "INSERT INTO credentials (username, password) VALUES (?,?)";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, user);
			ps.setString(2, password);
			ps.execute();
			
			return this.authenticateUser(user, password);
		} catch (Exception e) {
			return false;
		} finally {
			ps.close();
		}
	}
	
	/**
	 * Completes new user registration process.
	 * @param lname
	 * @param fname
	 * @param phoneNum
	 * @param email
	 * @param address
	 * @return
	 * @throws SQLException
	 */
	public boolean completeRegistration(String lname, String fname, String phoneNum, String email, String address) throws SQLException {
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO broker (id, last_name, first_name, phone_num, email, address) VALUES (?,?,?,?,?,?)";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, lname);
			preparedStatement.setString(3, fname);
			preparedStatement.setString(4, phoneNum);
			preparedStatement.setString(5, email);
			preparedStatement.setString(6, address);
			preparedStatement.execute();
			
			return true;
		} catch(Exception e) {
			return false;
		} finally {
			preparedStatement.close();
		}
	}
	
	/**
	 * Authenticates users with database.
	 * @param user
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean authenticateUser(String user, String password) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM credentials WHERE username = ? and password = ?";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, password);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				userName = resultSet.getString(2);
				userId = resultSet.getInt(1);
				
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
	
	/**
	 * Gets all the User's Customers.
	 * @throws SQLException
	 */
	public void populateCustomers() throws SQLException {
		customers.clear();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM client WHERE id IN (SELECT client_id FROM relationship WHERE broker_id = ?)";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int cId = resultSet.getInt(1);
				String clN = resultSet.getString(2);
				String cfN = resultSet.getString(3);
				String pN = resultSet.getString(4);
				String email = resultSet.getString(5);
				String add = resultSet.getString(6);
				double cash = resultSet.getDouble(7);

				customers.add(new Customer(cId, clN, cfN, pN, email, add, cash));
			} 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}
	
	/**
	 * Sets user information parameters.
	 * @throws SQLException
	 */
	public void populateUserData() throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM broker WHERE id= ?";
		
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
	
	/**
	 * Allows new customers to be registered to the user.
	 * @param lname
	 * @param fname
	 * @param phoneNum
	 * @param email
	 * @param address
	 * @param investAmount
	 * @return
	 * @throws SQLException
	 */
	public boolean registerCustomer(String lname, String fname, String phoneNum, String email, String address, double investAmount) throws SQLException {
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO client (id, last_name, first_name, phone_num, email, address, cash) VALUES (?,?,?,?,?,?,?)";
		
		int key = this.generateCustomerKey();
		
		while(this.isKeyUsed(key)) {
			key = this.generateCustomerKey();
		}
		
		try {
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, key);
			preparedStatement.setString(2, lname);
			preparedStatement.setString(3, fname);
			preparedStatement.setString(4, phoneNum);
			preparedStatement.setString(5, email);
			preparedStatement.setString(6, address);
			preparedStatement.setDouble(7, investAmount);
			
			preparedStatement.execute();
			
			if (this.insertRelationship(key)) {
				return true;
			}
			
			return false;
			
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			preparedStatement.close();
		}
	}
	
	/**
	 * Helper with giving customer registration.
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public boolean insertRelationship(int key) throws SQLException {
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO relationship (broker_id,client_id) VALUES (?,?)";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, key);
			
			preparedStatement.execute();
			
			return true;
			
		} catch(Exception e) {
			return false;
		} finally {
			preparedStatement.close();
		}
	}
	
	/**
	 * Checks for customer key validness.
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	private boolean isKeyUsed(int key) throws SQLException {
		PreparedStatement preparedStatement = null;
		String query =  "SELECT EXISTS(SELECT 1 FROM client WHERE id=? LIMIT 1)";
		ResultSet resultSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, key);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				int truthiness = resultSet.getInt(1);
				
				if (truthiness == 1) {
					return true;
				}
			} 
			return false;
		} catch(Exception e) {
			return false;
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}
	
	/**
	 * Allows customer to be deleted from user account.
	 * @param client_id
	 * @return
	 */
	public boolean deleteClient(int client_id) {
		PreparedStatement ps = null;
		String query = "DELETE FROM client WHERE id = ?";
		
		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, client_id);
			return ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Returns a valid key.
	 * @return
	 */
	private int generateCustomerKey() {
		return ThreadLocalRandom.current().nextInt(10000, 100000);
	}
	
	/**
	 * Returns the users customers.
	 * @return
	 */
	public ArrayList<Customer> getCustomers() {
		try {
			this.populateCustomers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	/**
	 * Returns user id.
	 * @return
	 */
	public int getUserId() {
		return userId;
	}
	
	/**
	 * Returns user's username.
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Returns user's first name.
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Returns user's last name.
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Returns users phone number.
	 * @return
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * Returns user's address.
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Returns user's email.
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	
}

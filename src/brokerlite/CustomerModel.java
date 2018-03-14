package brokerlite;

import java.sql.Connection;

public class CustomerModel {
	
	private Connection connection;
	private Customer customer;
	
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

}

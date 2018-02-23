package brokerlite;

import java.text.DecimalFormat;

public class Customer {
	
	private int id;
	private String firstName, lastName, phoneNumber, email, address;
	private int cash;

	public Customer(int id, String firstName, String lastName, String phoneNumber, String email, String address, int cash) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
		this.cash = cash;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return firstName + " " + lastName;
	}
	
	public String getCash() {
		double newCash = cash/10.0;
	    DecimalFormat f = new DecimalFormat("##.00");
	  
		return "$" + f.format(newCash);
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

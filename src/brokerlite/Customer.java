package brokerlite;

public class Customer {
	
	private String firstName;
	private String lastName;
	private int cash;

	public Customer(String firstName, String lastName, int cash) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.cash = cash;
	}
	
	public String getName() {
		return firstName + " " + lastName;
	}
	
	public int getCash() {
		return cash;
	}

}

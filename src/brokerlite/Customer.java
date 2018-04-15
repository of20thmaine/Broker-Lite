package brokerlite;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Customer {
	
	private int id;
	private String firstName, lastName, phoneNumber, email, address;
	private double cash;
	private ArrayList<Stock> stocks = new ArrayList<Stock>();
	private ArrayList<Integer> shares = new ArrayList<Integer>();
	private double todayPerformance;
	private double[] portfolio;

	public Customer(int id, String firstName, String lastName, String phoneNumber, String email, String address, double cash) {
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
	    DecimalFormat f = new DecimalFormat("##.00");
	  
		return "$" + f.format(cash);
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
	
	public void setStocks(ArrayList<Stock> customerStocks, ArrayList<Integer> customerShares) {
		stocks = customerStocks;
		shares = customerShares;
		this.calculatetPortfolioHistory();
	}
	
	private void calculatetPortfolioHistory() {
		portfolio = new double[10];
		
		for (int i = 0; i < stocks.size(); i++) {
			double[] history = stocks.get(i).getTenDayHistory();
			
			for (int j = 0; j < history.length; j++) {
				portfolio[j] += (history[j] * shares.get(i));
			}
		}
		
		todayPerformance = portfolio[portfolio.length-1] - portfolio[portfolio.length-2];
	}
	
	public double[] getPortfolioHistory() {
		return portfolio;
	}
	
	public double getTodayPerformance() {
		return todayPerformance;
	}
	
}

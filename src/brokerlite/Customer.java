package brokerlite;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author Bobby Palmer, Samnang Pann
 * Customer class allows storage and retrieval of customer data,a nd getter for customer stocks.
 */
public class Customer {
	
	private int id;
	private String firstName, lastName, phoneNumber, email, address;
	private double cash;
	private ArrayList<Stock> stocks = new ArrayList<Stock>();
	private ArrayList<Integer> shares = new ArrayList<Integer>();
	private double todayPerformance;
	private double[] portfolio;

	/**
	 * Constructor initializes customer with data.
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 * @param email
	 * @param address
	 * @param cash
	 */
	public Customer(int id, String firstName, String lastName, String phoneNumber, String email, String address, double cash) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
		this.cash = cash;
	}
	
	/**
	 * Returns customer ID number.
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns customer full name as String.
	 * @return
	 */
	public String getName() {
		return firstName + " " + lastName;
	}
	
	/**
	 * Returns String representation of current cash level.
	 * @return
	 */
	public String getCash() {
	    DecimalFormat f = new DecimalFormat("##.00");
	  
		return "$" + f.format(cash);
	}
	
	/**
	 * Returns phone number as String.
	 * @return
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * Returns address as String.
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Returns email as String.
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Called by StockModel, passes customer stocks and shares to customer.
	 * @param customerStocks
	 * @param customerShares
	 */
	public void setStocks(ArrayList<Stock> customerStocks, ArrayList<Integer> customerShares) {
		stocks = customerStocks;
		shares = customerShares;
		this.calculatetPortfolioHistory();
	}
	
	/**
	 * Calculates 10-Day cumulative portfolio performance for customer.
	 */
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
	
	/**
	 * Returns the 10-day portfolio history.
	 * @return
	 */
	public double[] getPortfolioHistory() {
		return portfolio;
	}
	
	/**
	 * Returns todays portfolio performance.
	 * @return
	 */
	public double getTodayPerformance() {
		return todayPerformance;
	}
	
	/**
	 * Returns total amount of assets, cash and portfolio, as double.
	 * @return
	 */
	public double totalPortfolio() {
		return portfolio[portfolio.length-1] + cash;
	}
	
	/**
	 * Returns value of portfolio today as double.
	 * @return
	 */
	public double getStocksValue() {
		return portfolio[portfolio.length-1];
	}
	
	/**
	 * Returns double value of current cash level.
	 * @return
	 */
	public double getCashValue() {
		return cash;
	}
	
	/**
	 * Returns the list of owned Stocks.
	 * @return
	 */
	public ArrayList<Stock> getOwnedStocks() {
		return stocks;
	}
	
	/**
	 * Returns list of share counts.
	 * @return
	 */
	public ArrayList<Integer> getShares() {
		return shares;
	}
}

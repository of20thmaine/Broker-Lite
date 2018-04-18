package brokerlite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bobby Palmer, Samnang Pann
 * Class allows for encapsulated Stock objects.
 */
public class Stock {
	
	private String symbol;
	List<StockQuote> stock_quotes = new ArrayList<StockQuote>();
	private boolean owned = false;

	
	public Stock(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol() {
		return this.symbol;
	}
	
	/**
	 * Outputs to sys.out for now. Can change to give back a list of values.
	 */
	public void getDetails() {
		System.out.println(this.symbol);
		for (StockQuote sq: stock_quotes) {
			System.out.println(sq+"\n");
		}
	}
	
	public void newStockQuote(String date, double indexVal, double high, double low) {
		stock_quotes.add(new StockQuote(date,indexVal,high,low));
	}
	
	public double[] getTenDayHistory() {
		owned = true;
		double[] history = new double[10];
		
		int counter = 0;
		
		for (int i = stock_quotes.size() - 1; counter < 10 && i > -1; i--) {
			// Handles Stock-API flaw which zeroes values some days.
			if (i-1 > -1 && stock_quotes.get(i).getIndexPrice() < stock_quotes.get(i-1).getIndexPrice() * 0.75) {
				i--;
			}
			history[counter] = stock_quotes.get(i).getIndexPrice();
			counter++;
		}
		
		double[] adjustment = new double[10];
		int count = 9;
		for (double d : history) {
			adjustment[count] = d;
			count--;
		}
		
		
		return adjustment;
	}
	
	public double getChangeValue() {
		return stock_quotes.get(stock_quotes.size()-1).getIndexPrice() - stock_quotes.get(stock_quotes.size()-2).getIndexPrice();
	}
	
	public boolean isOwned() {
		return owned;
	}
	
	public double todaysValue() {
		return stock_quotes.get(0).getIndexPrice();
	}
	
	@Override
	public String toString() {
		return symbol + "\n" + stock_quotes.get(stock_quotes.size()-1).toString(); 
	}
	
}

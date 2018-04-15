package brokerlite;

import java.util.ArrayList;
import java.util.List;

public class Stock {
	
	private String symbol;
	List<StockQuote> stock_quotes = new ArrayList<StockQuote>();
	
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
		double[] history = new double[10];
		
		int counter = 0;
		
		for (int i = stock_quotes.size() - 1; counter < 10 && i > -1; i--) {
			history[counter] = stock_quotes.get(i).getIndexPrice();
			counter++;
		}
		
		return history;
	}
	
	@Override
	public String toString() {
		return symbol + "\n" + stock_quotes.get(stock_quotes.size()-1).toString(); 
	}
	
}

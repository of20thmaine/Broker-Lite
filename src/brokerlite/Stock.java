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
		for(StockQuote sq: stock_quotes) {
			System.out.println(sq+"\n");
		}
	}
	
	public void newStockQuote(String date, double indexVal, double high, double low) {
		stock_quotes.add(new StockQuote(date,indexVal,high,low));
	}
	
	@Override
	public String toString() {
		return this.symbol; 
	}
	
}

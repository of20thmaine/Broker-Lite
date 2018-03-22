package brokerlite;

import java.util.ArrayList;
import java.util.List;

public class Stock {
	
	private String symbol;
	List<StockQuote> stock_quotes = new ArrayList<StockQuote>();
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public void getDetails() {
		System.out.println(this.symbol);
		for(StockQuote sq: stock_quotes) {
			System.out.println(sq);
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

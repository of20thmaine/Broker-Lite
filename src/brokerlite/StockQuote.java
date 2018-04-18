package brokerlite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Bobby Palmer, Samnang Pann
 * Class stores stock quote data.
 */
@XmlRootElement(name="StockQuote")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class StockQuote {
 
    private String date;
    private double high;
    private double low;
    private double indexPrice;
    
    public StockQuote(String date, double indexVal, double high, double low) {
    	this.high = high;
    	this.low = low;
    	this.indexPrice = indexVal;
    	this.date = date;
    }
    
    public double getIndexPrice() {
    	return this.indexPrice;
    }
    
    public double getHigh() {
    	return this.high;
    }
    
    public String getDate() {
    	return this.date;
    }
 
    public double getLow() {
        return this.low;
    }

    @Override
    public String toString() {
    	return "Date: "+this.date+"\nIndex Value: $"+this.indexPrice+"\nLow: $"+this.low+"\nHigh: $"+this.high;
    }
    
}

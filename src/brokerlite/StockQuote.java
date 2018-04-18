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
    
    /**
     * Constructor gives quote its needed initialization parameters.
     * @param date
     * @param indexVal
     * @param high
     * @param low
     */
    public StockQuote(String date, double indexVal, double high, double low) {
    	this.high = high;
    	this.low = low;
    	this.indexPrice = indexVal;
    	this.date = date;
    }
    
    /**
     * Returns current price on market.
     * @return
     */
    public double getIndexPrice() {
    	return this.indexPrice;
    }
    
    /**
     * Returns the days high price.
     * @return
     */
    public double getHigh() {
    	return this.high;
    }
    
    /**
     * Returns the date of the quote.
     * @return
     */
    public String getDate() {
    	return this.date;
    }
 
    /**
     * Returns the low of the day.
     * @return
     */
    public double getLow() {
        return this.low;
    }

    @Override
    public String toString() {
    	return "Date: "+this.date+"\nIndex Value: $"+this.indexPrice+"\nLow: $"+this.low+"\nHigh: $"+this.high;
    }
    
}

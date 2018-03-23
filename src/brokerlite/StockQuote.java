package brokerlite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
//import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
//import java.text.SimpleDateFormat;

@XmlRootElement(name="StockQuote")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class StockQuote {
 
//    private String name;
//    private String symbol;
    private String date;
    private double high;
    private double low;
    private double indexPrice;
    
    public StockQuote(String date, double indexVal, double high, double low) {
//    	this.symbol = symbol;
    	this.high = high;
    	this.low = low;
    	this.indexPrice = indexVal;
//    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    	
    	this.date = date;
    	
    }
    
 
//    public String getSymbol() {
//        return this.symbol;
//    }
    
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

//    public String getName() {
//        return this.name;
//    } 
 
//    @XmlElement(name="Name")
//    public void setName(String name) {
//        this.name = name;
//    }
 
//    @XmlElement(name="Symbol")
//    public void setSymbol(String symbol) {
//        this.symbol = symbol;
//    }
// 
//    @XmlElement(name="LastPrice")
//    public void setLastPrice(double lastPrice) {
//        this.low = lastPrice;
//    }
 
//    @Override
//    public String toString() {
//        return "StockQuote [name=" + name + ", symbol=" + symbol
//                + ", lastPrice=" +  + "]";
//    }

    @Override
    public String toString() {
    	return "Date: "+this.date+"\nIndex Value: $"+this.indexPrice+"\nLow: $"+this.low+"\nHigh: $"+this.high;
    }
    
}

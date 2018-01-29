package brokerlite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="StockQuote")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class StockQuote {
 
    private String name;
    private String symbol;
    private double lastPrice;
 
    public String getSymbol() {
        return this.symbol;
    }
 
    public String getName() {
        return this.name;
    }
 
    public double getLastPrice() {
        return this.lastPrice;
    }
 
    @XmlElement(name="Name")
    public void setName(String name) {
        this.name = name;
    }
 
    @XmlElement(name="Symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
 
    @XmlElement(name="LastPrice")
    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }
 
    @Override
    public String toString() {
        return "StockQuote [name=" + name + ", symbol=" + symbol
                + ", lastPrice=" + lastPrice + "]";
    }
 
}

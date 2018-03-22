package brokerlite;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Everything works. May not need stock class since can make queries for the data. Uncomment code in main.java to activate this class in the correct order.
 * @author panns
 *
 */

public class StockModel {
	
	private Connection connection;
	private List<String> stock_symbol = new ArrayList<String>();
	private List<StockQuote> stockList = new ArrayList<StockQuote>();
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private String api_key = "kGiW9-TEk_VB6Lexs68_";
	private String quandlDB = "NASDAQOMX";
	
	public StockModel() {
		connection = SqlConnect.connector();

		if(connection == null) {
			System.exit(1);
		}
	
	}
	
	public boolean isDbConnected() {
		try {
			return !connection.isClosed();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void grabStockSymbols() {
		/**
		 * This will eventually be a query that pulls distinct stocks from the database instead of being hardcoded in.
		 * TODO: Grab distinct stocks from databsae
		 * QuickFix: manually inputted symbol list
		 */
		
		String [] symbols = {"XQC", "XNDXT25NNR", "XNDXT25NNER", "XNDXT25E", "XNDXT25", "XNDXS2NNR", "XNDXS2", "XNDXS1NNR", "XNDXS1", "XNDXNNRS3", "XNDXNNRGBP", "XNDXNNRHKD", "XNDXNNREUR", "XNDXNNRCHF", "XNDXNNRCAD", "XNDXL3TR", "XNDXL", "XNDXHKD", "XNDXGBPMH", "XNDXGBP", "XNDXEURMH", "XNDXCHFMH", "XNDXEUR", "XNDXCHF", "XNDXCAD", "XNBINNR", "XCMPNNR"};
		
		for (String sym : symbols) {
			this.stock_symbol.add(sym);
		}
	}

	public void getAPI() throws Exception {
		Long daysInMillis = (long) 604800000; // 7 days in milliseconds
		Timestamp priorDays = new Timestamp(System.currentTimeMillis() - daysInMillis);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String formatPriorDays = formatter.format(priorDays);
		String formatTimestamp = formatter.format(timestamp);
		
		for (String sym: stock_symbol) {
			URL url = new URL("https://www.quandl.com/api/v3/datasets/"+quandlDB+"/"+sym+".csv?start_date="+formatPriorDays+"&end_date="+formatTimestamp+"&api_key="+api_key);
			URLConnection urlConn = url.openConnection();
			InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
			BufferedReader buff = new BufferedReader(inStream);
		
			List<String []> chunks = new ArrayList<String []>();
			
			String line = buff.readLine();
			
			while (line != null) {
				String [] lineSplit = line.split(",");
				chunks.add(lineSplit);
				line = buff.readLine();
			}

			
			/**
			 * This for loop just prints out the contents of chunks.
			 */
//			for (int i=0; i<chunks.size(); i++) {
//				for (int j=0; j < 6; j++)
//				System.out.println(chunks.get(i)[j]);
//			}
			
			/**
			 * Trade Date: chunks[0]
			 * Index Value: chunks[1]
			 * High: chunks[2]
			 * Low: chunks[3]
			 * This needs to be pushed into DB.
			 */
			
			if (this.updateDatabase(sym, chunks.get(1)[0], Double.valueOf(chunks.get(1)[1]), Double.valueOf(chunks.get(1)[2]), Double.valueOf(chunks.get(1)[3]))) {
				System.out.println("Added to "+sym+" successfully for "+chunks.get(1)[0]);
			} else {
				System.out.println("Could not add "+sym+" to database.");
			}
			
//			(chunks.get(1)[0], Double.valueOf(chunks.get(1)[1]), Double.valueOf(chunks.get(1)[2]), Double.valueOf(chunks.get(1)[3]);
		}
	}
	
	public boolean updateDatabase(String symbol, String date, double indexVal, double high, double low) throws SQLException {
		PreparedStatement ps = null;
		String query = "INSERT INTO STOCK VALUES (?,?,?,?,?)";
		Date newDate = Date.valueOf(date);

		try {
			ps = connection.prepareStatement(query);

			ps.setString(1, symbol);
			ps.setDate(2, newDate);
			ps.setDouble(3, indexVal);
			ps.setDouble(4, high);
			ps.setDouble(5, low);
			ps.execute();
			
			return true;

		} catch (Exception e) {
			return false;
		} finally {
			ps.close();
		}
		
	};
	
	
	
	
	
}

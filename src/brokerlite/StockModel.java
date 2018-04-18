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
 * @author Bobby Palmer, Samnang Pann
 * Class communicates between application and database for Stock related transactions.
 * Class also implements live API connection for current market data.
 */
public class StockModel {

//	String [] symbols = {"XQC", "XNDXT25NNR", "XNDXT25NNER", "XNDXT25E", "XNDXT25", "XNDXS2NNR", "XNDXS2", "XNDXS1NNR",
//		"XNDXS1", "XNDXNNRS3", "XNDXNNRGBP", "XNDXNNRHKD", "XNDXNNREUR", "XNDXNNRCHF", "XNDXNNRCAD", "XNDXL3TR", "XNDXL", "XNDXHKD",
//		"XNDXGBPMH", "XNDXGBP", "XNDXEURMH", "XNDXCHFMH", "XNDXEUR", "XNDXCHF", "XNDXCAD", "XNBINNR", "XCMPNNR"};
	private Connection connection;
	public static List<Stock> stockList = new ArrayList<Stock>();
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

	/**
	 * Wrapper for update process.
	 */
	public void startUpdate() {
		try {
			this.populateStockList();
			this.getAPI();
			this.populateStockHistory();
		} catch (Exception e) {
			System.out.println("Unable to execute update.");
		}
		
//		for (Stock stock : stockList) {
//			stock.getDetails();
//		}
		
	}

	private void getAPI() throws Exception {
		Long daysInMillis = (long) 259200000.0 * 5; // 3 days in milliseconds
		Timestamp priorDays = new Timestamp(System.currentTimeMillis() - daysInMillis);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String start_date = formatter.format(priorDays);
		String end_date = formatter.format(timestamp);
		
		for (Stock stock: stockList) {
			try {
				URL url = new URL("https://www.quandl.com/api/v3/datasets/"+quandlDB+"/"+stock.getSymbol()+".csv?start_date="+start_date+"&end_date="+end_date+"&api_key="+api_key);
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
//				for (int i=0; i<chunks.size(); i++) {
//					for (int j=0; j < 6; j++)
//					System.out.println(chunks.get(i)[j]);
//				}
			
			/**
			 * Trade Date: chunks[0]
			 * Index Value: chunks[1]
			 * High: chunks[2]
			 * Low: chunks[3]
			 * This needs to be pushed into DB.
			 */
				for (int i=1; i<chunks.size(); i++) {
					if (this.updateDatabase(stock.getSymbol(), chunks.get(i)[0], Double.valueOf(chunks.get(i)[1]), Double.valueOf(chunks.get(i)[2]), Double.valueOf(chunks.get(i)[3]))) {
//						System.out.println("Added to "+stock.getSymbol()+" successfully for "+chunks.get(i)[0]);
					} else {
//						System.out.println("Could not add "+stock.getSymbol()+" to database.");
					}
				}
			
//				(chunks.get(1)[0], Double.valueOf(chunks.get(1)[1]), Double.valueOf(chunks.get(1)[2]), Double.valueOf(chunks.get(1)[3]);
			
			} catch (Exception e) {
				System.out.println("No updates available.");
			}
		}
	}

	private boolean updateDatabase(String symbol, String date, double indexVal, double high, double low) throws SQLException {

		PreparedStatement ps = null;
		String query = "INSERT INTO STOCK VALUES (?,?,?,?,?)";
//		Date newDate = Date.valueOf(date);

		try {
			ps = connection.prepareStatement(query);

			ps.setString(1, symbol);
			ps.setString(2, date);
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
		
	}

	private void populateStockList() throws SQLException {

		PreparedStatement ps = null;
		String query = "SELECT DISTINCT(NAME) FROM STOCK";

		try {
			ps = connection.prepareStatement(query);
			ResultSet result = ps.executeQuery();
//			int count = 0;
			while (result.next()) {
//				count++;
//				System.out.println(count+": "+result.getString("name"));
				Stock stock = new Stock(result.getString("name"));
				stockList.add(stock);
			}

		} catch (Exception e) {
			System.out.println("Unable to execute query.");
		} finally {
			ps.close();
		}

	}

	private void populateStockHistory() throws SQLException {
		
		PreparedStatement ps = null;
		String query = "select date_time, index_value, high, low from stock where name = ?";
		
		for (Stock stock : stockList) {
			try {
				ps = connection.prepareStatement(query);
				ps.setString(1, stock.getSymbol());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					String date = rs.getString("date_time");
					double indexVal = rs.getDouble("index_value");
					double high = rs.getDouble("high");
					double low = rs.getDouble("low");
					
					stock.newStockQuote(date, indexVal, high, low);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				ps.close();
			}
		}
	}
	
	public void getCustomerStocks(Customer c) throws SQLException {
		ArrayList<Stock> customerStocks = new ArrayList<Stock>();
		ArrayList<Integer> customerShares = new ArrayList<Integer>();
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT stock_name, shares FROM stock_owned WHERE client_id = ?";
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, c.getId());
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				String stockName = resultSet.getString(1);
				int shares = resultSet.getInt(2);
				
				if (shares != 0) {
					for (Stock s : stockList) {
						if (s.getSymbol().equals(stockName)) {
							customerStocks.add(s);
							customerShares.add(shares);
							break;
						}
					}
				}
			}
			
			c.setStocks(customerStocks, customerShares);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}
	

    /**
     * This assumes that you have already checked if the client has enough money to make the transaction and the cost and know the client's ID.
     * @param cost
     * @param shares
     * @param stock_name
     * @param client_id
     */
    public void transaction(double amount, int shares, String stock_name, int client_id) {
        
        PreparedStatement ps_client = null;
        PreparedStatement ps_stock_owned = null;
        
        String query_client = "UPDATE client SET cash = (cash + ?)";
        String query_stock_owned = "UPDATE stock_owned SET shares = (shares + ?) WHERE stock_name = ? AND client_id = ?";
        
        try {
            connection.setAutoCommit(false);
            
            ps_client = connection.prepareStatement(query_client);
            ps_stock_owned = connection.prepareStatement(query_stock_owned);
            
            ps_client.setDouble(1, amount);
            
            ps_stock_owned.setInt(1, shares);
            ps_stock_owned.setString(2, stock_name);
            ps_stock_owned.setInt(3, client_id);
            
            ps_client.executeUpdate();
            ps_stock_owned.executeUpdate();
            connection.commit();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
	
	public List<Stock> getStocks() {
		return stockList;
	}
}
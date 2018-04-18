package brokerlite;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

/**
 * @author Bobby Palmer, Samnang Pann Class implements logic for customer tab
 *         UI.
 */
public class CustomerTabController {

	private Customer customer;
	private ArrayList<Stock> stocks;
	private ArrayList<Integer> shares;
	ObservableList<Stock> allStocks;
	private StockModel stockModel;
	private MainPageController mainPage;

	@FXML
	private ScrollPane scrollPane;
	@FXML
	private AnchorPane backgroundPane;
	@FXML
	private Label titleLabel;
	@FXML
	private LineChart<Number, Number> linechart;
	@FXML
	private NumberAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private Label portfolioValue;
	@FXML
	private Label cashValue;
	@FXML
	private Label stockValue;
	@FXML
	private Label dayPerformance;
	@FXML
	private PieChart pieChart;
	@FXML
	private GridPane ownedStocks;
	@FXML
	private GridPane sellStocks;
	@FXML
	private Pane buySellArea;
	@FXML
	private TextField sharesField;
	@FXML
	private Button buySellButton;
	@FXML
	private Label buySellLabel;
	@FXML
	private Label statusLabel;

	/**
	 * Used to allow controller to be initialized with necessary pointers (JavaFX
	 * controllers don't really have a good constructor interface).
	 * @param customer
	 * @param allStocks
	 * @param stockModel
	 * @param mainPage
	 */
	public void initializer(Customer customer, ObservableList<Stock> allStocks, StockModel stockModel,
			MainPageController mainPage) {
		this.customer = customer;
		stocks = customer.getOwnedStocks();
		shares = customer.getShares();
		this.allStocks = allStocks;
		this.stockModel = stockModel;
		this.mainPage = mainPage;

		statusLabel.setWrapText(true);
		buySellArea.setVisible(false);

		this.displayTitle();
		this.setLabels();
		this.populateChart();
		this.populatePieChart();
		this.ownedStockTable();
	}

	/**
	 * Displays the user's name as title.
	 */
	@FXML
	private void displayTitle() {
		titleLabel.setText(customer.getName() + "'s Portfolio:");
	}

	/**
	 * Sets labels for portfolio values.
	 */
	@FXML
	private void setLabels() {
		DecimalFormat formatter = new DecimalFormat("#,###.00");

		portfolioValue.setText("$" + formatter.format(customer.totalPortfolio()));
		portfolioValue.setId("customer-small-label");
		portfolioValue.setTextAlignment(TextAlignment.LEFT);

		cashValue.setText("$" + formatter.format(customer.getCashValue()));
		cashValue.setId("customer-small-label");
		cashValue.setTextAlignment(TextAlignment.LEFT);

		stockValue.setText("$" + formatter.format(customer.getStocksValue()));
		stockValue.setId("customer-small-label");
		stockValue.setTextAlignment(TextAlignment.LEFT);

		if (customer.getTodayPerformance() >= 0) {
			dayPerformance.setText("↑ " + formatter.format(customer.getTodayPerformance()));
			dayPerformance.setId("positive-performance");
		} else {
			dayPerformance.setText("↓ " + formatter.format((-1) * customer.getTodayPerformance()));
			dayPerformance.setId("negative-performance");
		}

	}

	/**
	 * Populates the performance chart with data.
	 */
	@FXML
	private void populateChart() {
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName(customer.getName());

		yAxis.setLabel("Price ($)");
		xAxis.setLabel("Days (10 is Today)");

		double[] data = customer.getPortfolioHistory();

		for (int i = 0; i < data.length; i++) {
			series.getData().add(new XYChart.Data<>(i + 1, data[i]));
		}

		linechart.getData().add(series);
	}

	/**
	 * Populates asset chart with data.
	 */
	@FXML
	private void populatePieChart() {
		for (int i = 0; i < stocks.size(); i++) {
			PieChart.Data slice = new PieChart.Data(stocks.get(i).getSymbol(), shares.get(i));
			pieChart.getData().add(slice);
		}

	}

	/**
	 * Generates a table of currently owned stocks.
	 */
	@FXML
	private void ownedStockTable() {
		DecimalFormat formatter = new DecimalFormat("#,###.00");

		for (int rowIndex = 0; rowIndex < stocks.size(); rowIndex++) {
			RowConstraints rc = new RowConstraints();
			rc.setVgrow(Priority.ALWAYS);
			rc.setFillHeight(true);
			ownedStocks.getRowConstraints().add(rc);
		}

		for (int colIndex = 0; colIndex < 4; colIndex++) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setHgrow(Priority.ALWAYS);
			cc.setFillWidth(true);
			ownedStocks.getColumnConstraints().add(cc);
		}

		int i = 0;

		// Annoying code. Copied and pasted working bad code from market-tab
		for (Stock s : stocks) {
			StackPane holder1 = new StackPane();
			holder1.setId("stock-holder");
			Label stock1 = new Label();
			stock1.setId("stock-label");
			stock1.setText(s.getSymbol());
			stock1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder1.getChildren().add(stock1);
			ownedStocks.add(holder1, 0, i);
			StackPane.setAlignment(holder1, Pos.CENTER_LEFT);

			StackPane holder2 = new StackPane();
			holder2.setId("stock-holder");
			Label stock2 = new Label();
			stock2.setId("white-number");
			stock2.setText(shares.get(i) + "");
			stock2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder2.getChildren().add(stock2);
			ownedStocks.add(holder2, 1, i);
			StackPane.setAlignment(holder2, Pos.CENTER_LEFT);

			StackPane holder3 = new StackPane();
			holder3.setId("stock-holder");
			Label stock3 = new Label();
			stock3.setId("white-number");
			stock3.setText("$" + formatter.format(s.todaysValue()));
			stock3.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder3.getChildren().add(stock3);
			ownedStocks.add(holder3, 2, i);
			StackPane.setAlignment(holder3, Pos.CENTER_LEFT);

			StackPane holder4 = new StackPane();
			holder4.setId("stock-holder");
			Label stock4 = new Label();
			if (s.getChangeValue() >= 0) {
				stock4.setText("↑ " + formatter.format(s.getChangeValue()));
				stock4.setId("positive-performance");
			} else {
				stock4.setText("↓ " + formatter.format((-1) * s.getChangeValue()));
				stock4.setId("negative-performance");
			}
			stock4.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder4.getChildren().add(stock4);
			ownedStocks.add(holder4, 3, i);
			StackPane.setAlignment(holder4, Pos.CENTER_LEFT);

			i++;
		}
	}

	/**
	 * Allows users to buy stocks.
	 */
	@FXML
	private void buyAssets() {
		sellStocks.getChildren().clear();
		DecimalFormat formatter = new DecimalFormat("#,###.00");

		for (int rowIndex = 0; rowIndex < allStocks.size(); rowIndex++) {
			RowConstraints rc = new RowConstraints();
			rc.setVgrow(Priority.ALWAYS);
			rc.setFillHeight(true);
			sellStocks.getRowConstraints().add(rc);
		}

		for (int colIndex = 0; colIndex < 4; colIndex++) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setHgrow(Priority.ALWAYS);
			cc.setFillWidth(true);
			sellStocks.getColumnConstraints().add(cc);
		}

		int i = 0;

		// Annoying code. Copied and pasted working bad code from market-tab
		for (Stock s : allStocks) {
			StackPane holder1 = new StackPane();
			holder1.setId("stock-holder");
			Label stock1 = new Label();
			stock1.setId("stock-label");
			stock1.setText(s.getSymbol());
			stock1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder1.getChildren().add(stock1);
			sellStocks.add(holder1, 0, i);
			StackPane.setAlignment(holder1, Pos.CENTER_LEFT);

			StackPane holder3 = new StackPane();
			holder3.setId("stock-holder");
			Label stock3 = new Label();
			stock3.setId("white-number");
			stock3.setText("$" + formatter.format(s.todaysValue()));
			stock3.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder3.getChildren().add(stock3);
			sellStocks.add(holder3, 1, i);
			StackPane.setAlignment(holder3, Pos.CENTER_LEFT);

			StackPane holder4 = new StackPane();
			holder4.setId("stock-holder");
			Label stock4 = new Label();
			if (s.getChangeValue() >= 0) {
				stock4.setText("↑ " + formatter.format(s.getChangeValue()));
				stock4.setId("positive-performance");
			} else {
				stock4.setText("↓ " + formatter.format((-1) * s.getChangeValue()));
				stock4.setId("negative-performance");
			}
			stock4.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder4.getChildren().add(stock4);
			sellStocks.add(holder4, 2, i);
			StackPane.setAlignment(holder4, Pos.CENTER_LEFT);

			StackPane holder5 = new StackPane();
			holder5.setId("buy-holder");
			Label stock5 = new Label();
			stock5.setId("stock-label");
			stock5.setText("BUY");
			stock5.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder5.getChildren().add(stock5);
			sellStocks.add(holder5, 3, i);
			StackPane.setAlignment(holder1, Pos.CENTER_LEFT);

			EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					showBuyArea(s);
				}
			};
			holder5.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

			i++;
		}
	}

	/**
	 * Allows users to sell stocks.
	 */
	@FXML
	private void sellAssets() {
		sellStocks.getChildren().clear();
		DecimalFormat formatter = new DecimalFormat("#,###.00");

		for (int rowIndex = 0; rowIndex < stocks.size(); rowIndex++) {
			RowConstraints rc = new RowConstraints();
			rc.setVgrow(Priority.ALWAYS);
			rc.setFillHeight(true);
			sellStocks.getRowConstraints().add(rc);
		}

		for (int colIndex = 0; colIndex < 5; colIndex++) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setHgrow(Priority.ALWAYS);
			cc.setFillWidth(true);
			sellStocks.getColumnConstraints().add(cc);
		}

		int i = 0;

		// Annoying code. Copied and pasted working bad code from market-tab
		for (Stock s : stocks) {
			StackPane holder1 = new StackPane();
			holder1.setId("stock-holder");
			Label stock1 = new Label();
			stock1.setId("stock-label");
			stock1.setText(s.getSymbol());
			stock1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder1.getChildren().add(stock1);
			sellStocks.add(holder1, 0, i);
			StackPane.setAlignment(holder1, Pos.CENTER_LEFT);

			StackPane holder2 = new StackPane();
			holder2.setId("stock-holder");
			Label stock2 = new Label();
			stock2.setId("white-number");
			stock2.setText(shares.get(i) + "");
			stock2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder2.getChildren().add(stock2);
			sellStocks.add(holder2, 1, i);
			StackPane.setAlignment(holder2, Pos.CENTER_LEFT);

			StackPane holder3 = new StackPane();
			holder3.setId("stock-holder");
			Label stock3 = new Label();
			stock3.setId("white-number");
			stock3.setText("$" + formatter.format(s.todaysValue()));
			stock3.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder3.getChildren().add(stock3);
			sellStocks.add(holder3, 2, i);
			StackPane.setAlignment(holder3, Pos.CENTER_LEFT);

			StackPane holder4 = new StackPane();
			holder4.setId("stock-holder");
			Label stock4 = new Label();
			if (s.getChangeValue() >= 0) {
				stock4.setText("↑ " + formatter.format(s.getChangeValue()));
				stock4.setId("positive-performance");
			} else {
				stock4.setText("↓ " + formatter.format((-1) * s.getChangeValue()));
				stock4.setId("negative-performance");
			}
			stock4.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder4.getChildren().add(stock4);
			sellStocks.add(holder4, 3, i);
			StackPane.setAlignment(holder4, Pos.CENTER_LEFT);

			StackPane holder5 = new StackPane();
			holder5.setId("sell-holder");
			Label stock5 = new Label();
			stock5.setId("stock-label");
			stock5.setText("SELL");
			stock5.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder5.getChildren().add(stock5);
			sellStocks.add(holder5, 4, i);
			StackPane.setAlignment(holder1, Pos.CENTER_LEFT);

			int currentShares = shares.get(i);
			EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					showSellArea(s, currentShares);
				}
			};
			holder5.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

			i++;
		}
	}

	/**
	 * Displays sell area.
	 * @param s
	 * @param currentShares
	 */
	private void showSellArea(Stock s, int currentShares) {
		buySellArea.setVisible(true);
		

		buySellLabel.setText("Sell " + s.getSymbol());
		buySellButton.setText("Sell");
		buySellButton.setId("little-sell");

		EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sell(s, currentShares);
			}
		};
		buySellButton.setOnAction(buttonHandler);

	}

	/**
	 * Helper for selling stocks.
	 * @param s
	 * @param shares
	 */
	private void sell(Stock s, int shares) {
		String sharesText = sharesField.getText();

		try {
			int givenShares = Integer.parseInt(sharesText);

			if (givenShares > shares) {
				statusLabel.setText("Too many shares entered.");
			} else if (givenShares < 1) {
				statusLabel.setText("Too few shares entered.");
			} else {
				try {
					stockModel.transaction((s.todaysValue() * givenShares), (-1) * givenShares, s.getSymbol(),
							customer.getId());
					statusLabel.setText("Transaction completed.");
					mainPage.buySellRefresh(customer);
				} catch (Exception e) {
					statusLabel.setText("Transaction failed.");
				}
			}
		} catch (Exception e) {
			statusLabel.setText("Invalid input.");
		}
	}

	/**
	 * Displays buy area.
	 * @param s
	 */
	private void showBuyArea(Stock s) {
		buySellArea.setVisible(true);

		buySellLabel.setText("Buy " + s.getSymbol());
		buySellButton.setText("Buy");
		buySellButton.setId("little-buy");

		EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				buy(s);
			}
		};
		buySellButton.setOnAction(buttonHandler);

	}

	/**
	 * Helper for buying stocks.
	 * @param s
	 */
	private void buy(Stock s) {
		String sharesText = sharesField.getText();

		try {
			int givenShares = Integer.parseInt(sharesText);

			if ((double) (givenShares * s.todaysValue()) > customer.getCashValue()) {
				statusLabel.setText("Customer has insufficient funds.");
			} else if (givenShares < 1) {
				statusLabel.setText("Too few shares entered.");
			} else {
				try {
					stockModel.transaction((-1) * (s.todaysValue() * givenShares), givenShares, s.getSymbol(),
							customer.getId());
					statusLabel.setText("Transaction completed.");
					mainPage.buySellRefresh(customer);
					
				} catch (Exception e) {
					statusLabel.setText("Transaction failed.");
				}

			}
		} catch (Exception e) {
			statusLabel.setText("Invalid input.");
		}
	}
}

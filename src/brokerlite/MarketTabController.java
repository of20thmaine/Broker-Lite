package brokerlite;

import java.text.DecimalFormat;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Bobby Palmer, Samnang Pann
 * Class implements underling logic of Market Tab UI.
 */
public class MarketTabController {
	
	private ObservableList<Customer> customers;
	ObservableList<Stock> stocksOwned;
	
	@FXML
	private AnchorPane backgroundPane;
	@FXML
	private Label titleLabel;
	@FXML
	private GridPane stockList;
	@FXML
	private LineChart<Number, Number> linechart;
	@FXML
	private NumberAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private VBox stockGraphArea;

	
	public void initializer(ObservableList<Customer> customers, ObservableList<Stock> stocksOwned) {
		this.customers = customers;
		this.stocksOwned = stocksOwned;
		this.populateChart();
		this.populateStockTable();
	}
	
	@FXML
	private void populateChart() {
		double[] average = new double[10];
		double globalMin = 10000000000.0;
		double globalMax = 0;
		
		for (Customer c : customers) {
			XYChart.Series<Number, Number> series = new XYChart.Series<>();
			series.setName(c.getName());
			
			double[] data = c.getPortfolioHistory();
			
			for (double d: data) {
				if (d < globalMin) {
					globalMin = d;
				} if (d > globalMax) {
					globalMax = d;
				}
			}
			
			for (int i = 0; i < data.length; i++) {
				series.getData().add(new XYChart.Data<>(i+1, data[i]));
				average[i] += data[i];
			}
			
			linechart.getData().add(series);
		}
		
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName("Average Performance");
		for (int i = 0; i < average.length; i++) {
			average[i] /= customers.size();
			series.getData().add(new XYChart.Data<>(i+1, average[i]));
			
		}
		
		linechart.getData().add(series);
	}
	
	@FXML
	private void populateStockTable() {
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		
		for (int rowIndex = 0; rowIndex < stocksOwned.size(); rowIndex++) {
		    RowConstraints rc = new RowConstraints();
		    rc.setVgrow(Priority.ALWAYS);
		    rc.setFillHeight(true);
		    stockList.getRowConstraints().add(rc);
		}
		
		for (int colIndex = 0; colIndex < 3; colIndex++) {
		    ColumnConstraints cc = new ColumnConstraints();
		    cc.setHgrow(Priority.ALWAYS);
		    cc.setFillWidth(true);
		    stockList.getColumnConstraints().add(cc);
		}
		
		int i = 0;
		
		/* This is an appalling piece of crap code, but there were serious problems
		 * implementing the table class. Allowing for the different CSS-id's required
		 * this terrible implementation, but it does work.
		 */
		for (Stock s : stocksOwned) {
			StackPane holder1 = new StackPane();
			holder1.setId("stock-holder2");
			Label stock1 = new Label();
			stock1.setId("stock-label");
			stock1.setText(s.getSymbol());
			stock1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder1.getChildren().add(stock1);
			stockList.add(holder1, 0, i);
			StackPane.setAlignment(holder1, Pos.CENTER_LEFT);
			
			EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
				   @Override 
				   public void handle(MouseEvent e) { 
					   stockGraph(s);
				   } 
				};
			holder1.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
			
			StackPane holder2 = new StackPane();
			holder2.setId("stock-holder");
			Label stock2 = new Label();
			stock2.setId("white-number");
			stock2.setText("$" + formatter.format(s.todaysValue()));
			stock2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder2.getChildren().add(stock2);
			stockList.add(holder2, 1, i);
			StackPane.setAlignment(holder2, Pos.CENTER_LEFT);
			
			StackPane holder3 = new StackPane();
			holder3.setId("stock-holder");
			Label stock3 = new Label();
			if (s.getChangeValue() >= 0) {
				stock3.setText("↑ " + formatter.format(s.getChangeValue()));
				stock3.setId("positive-performance");
			} else {
				stock3.setText("↓ " + formatter.format((-1)*s.getChangeValue()));
				stock3.setId("negative-performance");
			}
			stock3.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			holder3.getChildren().add(stock3);
			stockList.add(holder3, 2, i);
			StackPane.setAlignment(holder3, Pos.CENTER_LEFT);
			
			i++;
		}
	}
	
	private void stockGraph(Stock stock) {
		stockGraphArea.getChildren().clear();
		stockGraphArea.setMinHeight(300.00);
		
		NumberAxis xAxis = new NumberAxis();
		xAxis.setLabel("10-Day Performance");


		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Price ($)");
		yAxis.setAutoRanging(false);
		
		LineChart<Number, Number> stockChart = new LineChart<Number, Number>(xAxis, yAxis);
		stockChart.setTitle(stock.getSymbol() + " Performance");
		
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName(stock.getSymbol());
		
		double[] data = stock.getTenDayHistory();
		double min = data[0];
		double max = data[0];
		
		for (Double d : data) {
			if (d < min) {
				min = d;
			}
			if (d > max) {
				max = d;
			}
		}
		
		yAxis.setLowerBound(min-20.0);
		yAxis.setUpperBound(max+20.0);
		
		for (int i = 0; i < data.length; i++) {
			series.getData().add(new XYChart.Data<>(i+1, data[i]));
		}
		
		stockChart.getData().add(series);
		
		stockGraphArea.getChildren().add(stockChart);
	}

}

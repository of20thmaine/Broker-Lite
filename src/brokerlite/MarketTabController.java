package brokerlite;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MarketTabController {
	
	private ObservableList<Customer> customers;
	
	@FXML
	private AnchorPane backgroundPane;
	@FXML
	private Label titleLabel;
	@FXML
	private VBox stockList;
	@FXML
	private LineChart<Number, Number> linechart;
	@FXML
	private NumberAxis xAxis;
	@FXML
	private NumberAxis yAxis;

	
	public void initializer(ObservableList<Customer> customers) {
		this.customers = customers;
		this.populateChart();
	}
	
	@FXML
	private void populateChart() {
		for (Customer c : customers) {
			XYChart.Series<Number, Number> series = new XYChart.Series<>();
			series.setName(c.getName());
			
			double[] data = c.getPortfolioHistory();
			
			for (int i = 0; i < data.length; i++) {
				series.getData().add(new XYChart.Data<>(i+1, data[i]));
			}
			
			linechart.getData().add(series);
		}
	}
	

}

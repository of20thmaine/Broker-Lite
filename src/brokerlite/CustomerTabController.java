package brokerlite;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class CustomerTabController {
	
	private Customer customer;
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
	
	public void initializer(Customer customer) {
		this.customer = customer;
		this.displayTitle();
		this.populateChart();
	}
	
	@FXML
	private void displayTitle() {
		titleLabel.setText(customer.getName() + "'s Portfolio:");
	}
	
	@FXML
	private void populateChart() {
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName(customer.getName());
		
		double[] data = customer.getPortfolioHistory();
		
		for (int i = 0; i < data.length; i++) {
			series.getData().add(new XYChart.Data<>(i+1, data[i]));
		}
		
		linechart.getData().add(series);
	}
	

}

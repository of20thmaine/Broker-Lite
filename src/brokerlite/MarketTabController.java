package brokerlite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MarketTabController {
	
	private StockModel stockModel;
	private ObservableList<Stock> stocks;
	
	@FXML
	private AnchorPane backgroundPane;
	@FXML
	private Label titleLabel;
	@FXML
	private VBox stockList;
	
	public void initializer(StockModel stockModel) {
		this.stockModel = stockModel;
		this.displayStocks();
	}
	
	private void displayStocks() {
		stocks = FXCollections.observableArrayList(stockModel.getStocks());
		
		stockList.getChildren().clear();
		stockList.setSpacing(5.0);
		
		for(Stock s : stocks) {
//			Button b = new Button();
//			b.setId("customer-button");
//			b.setText(s.toString());
//			b.setMaxWidth(Double.MAX_VALUE);
//			b.setMaxHeight(200);
			
			HBox stockButton = new HBox();
			Label l = new Label(s.toString());
			l.setWrapText(true);
			
			stockButton.getChildren().add(l);
			stockButton.setAlignment(Pos.BASELINE_CENTER);
			
			stockList.getChildren().add(stockButton);
		}
	}

}

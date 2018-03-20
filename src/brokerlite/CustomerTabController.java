package brokerlite;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class CustomerTabController {
	
	private Customer customer;
	@FXML
	private AnchorPane backgroundPane;
	@FXML
	private Label titleLabel;
	
	public void initializer(Customer customer) {
		this.customer = customer;
		this.displayTitle();
	}
	
	@FXML
	private void displayTitle() {
		titleLabel.setText(customer.getName() + "'s Portfolio:");
	}

}

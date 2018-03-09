package brokerlite;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class RegisterBrokerFinalController {
	
	private UserModel userModel;
	@FXML
	private Pane leftPane;
	@FXML
	private TextField first_name;
	@FXML
	private TextField last_name;
	@FXML
	private TextField address;
	@FXML
	private TextField phone_number;
	@FXML
	private TextField email;
	@FXML
	private Button clearButton;
	@FXML
	private Label statusLabel;
	@FXML
	private Label successLabel;

	public void postInitialize(UserModel um) {
		userModel = um;
	}
	
	@FXML
	public void register(ActionEvent event) {
		try {
			String fn = first_name.getText();
			String ln = last_name.getText();
			String add = address.getText();
			String pn = phone_number.getText();
			String em = email.getText();
			
			if (userModel.completeRegistration(ln, fn, pn, em, add)) {
				leftPane.getChildren().clear();
				leftPane.getChildren().add(successLabel);
				successLabel.setText("You have succesfully registered!");
			} else {
				statusLabel.setText("Invalid Form1");
				this.clearUser(event);
			}
		} catch(Exception e) {
			statusLabel.setText("Invalid Form");
			this.clearUser(event);
		}
	}
	
	@FXML
	public void clearUser(ActionEvent event) {
		address.clear();
		last_name.clear();
		phone_number.clear();
		first_name.clear();
		email.clear();
	}
}

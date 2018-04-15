package brokerlite;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * @author Bobby Palmer, Samnang Pann
 * Class implements underlying logic for the 
 * final step in new-user registration.
 */
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

	/**
	 * Method called immediately after class initialization,
	 * accepts usermodel from RegistrationBrokerController.
	 * @param um
	 */
	public void postInitialize(UserModel um) {
		userModel = um;
	}
	
	/**
	 * Event handler for when user clicks the register button.
	 * Checks inputted data for correctness, and if correct passes
	 * to usermodel for final step in registration process.
	 * @param event
	 */
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
	
	/**
	 * Event handler for when the user clicks the clear-user button.
	 * Causes the textfields on screen to be clear of all data.
	 * @param event
	 */
	@FXML
	public void clearUser(ActionEvent event) {
		address.clear();
		last_name.clear();
		phone_number.clear();
		first_name.clear();
		email.clear();
	}
}

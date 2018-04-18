package brokerlite;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;

/**
 * @author Bobby Palmer, Samnang Pann
 * Class implements the underlying logic of customer registration page.
 */
public class CustomerRegistrationController {
	
	private MainPageController mainPage;
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
	private TextField investment;
	@FXML
	private Button clearButton;
	@FXML
	private Label statusLabel;
	@FXML
	private Label successLabel;
	@FXML
	private Pane mainPane;
	@FXML
	private ScrollPane scrollPane;
	
	/**
	 * Allows reference to mainpage to be included at intialization.
	 * @param mainPage
	 */
	public void initializer(MainPageController mainPage) {
		this.mainPage = mainPage;
	}

	/**
	 * Clears the user input fields.
	 * @param event
	 */
	@FXML
	public void clearUser(ActionEvent event) {
		address.clear();
		last_name.clear();
		phone_number.clear();
		first_name.clear();
		email.clear();
		investment.clear();
	}
	
	/**
	 * Performs user registration, initially checks input fields for validity,
	 * then attempts to register user via UserModel.
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
			String cash = investment.getText();
			double investCash = 0;
			
			if (fn.length() < 1) {
				first_name.clear();
				statusLabel.setText("Thats not a first name!");
			} else if (ln.length() < 1) {
				last_name.clear();
				statusLabel.setText("Thats not a last name!");
			} else if (em.length() < 3) {
				email.clear();
				statusLabel.setText("Please enter a valid email.");
			} else if (pn.length() < 7) {
				phone_number.clear();
				statusLabel.setText("Please enter a valid phone number.");
			} else if (add.length() < 3) {
				address.clear();
				statusLabel.setText("Please enter a valid home address.");
			}
			
			try {
				investCash = Double.parseDouble(cash);
			} catch(Exception e) {
				statusLabel.setText("Numbers only in investment form.");
				investment.clear();
			}
			
			if (mainPage.registerCustomer(ln, fn, pn, em, add, investCash)) {
				mainPane.getChildren().clear();
				mainPane.getChildren().add(successLabel);
				successLabel.setText(fn + " " + ln + " has been succesfully register to your account!");
			} else {
				statusLabel.setText("Invalid Form");
				this.clearUser(event);
			}
		
		} catch(Exception e) {
			statusLabel.setText("Invalid Form");
			this.clearUser(event);
		}
	}
	
	
}

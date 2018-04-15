package brokerlite;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;

/**
 * @author Bobby Palmer, Samnang Pann
 * Class intialized by login screen, implements underlying
 * logic for first step in new user registration.
 */
public class RegisterBrokerController {
	
	private UserModel userModel = new UserModel();
	@FXML
	private Pane leftPane;
	@FXML
	private TextField userName;
	@FXML
	private PasswordField userPassword1;
	@FXML
	private PasswordField userPassword2;
	@FXML
	private Button clearButton;
	@FXML
	private Label statusLabel;

	/**
	 * Event handler for when user clicks registration button.
	 * Retrieves data from textfields and checks for correctness,
	 * before passing to UserModel, which attempts to input data in database.
	 * If succesful, replaces left pane of login screen with next stage in
	 * registration process, else prompts user to try again.
	 * @param event
	 * @throws SQLException
	 * @throws IOException
	 */
	@FXML
	public void register(ActionEvent event) throws SQLException, IOException {
		String un = userName.getText();
		String pw1 = userPassword1.getText();
		String pw2 = userPassword2.getText();
		
		if (un.length() > 12) {
			statusLabel.setText("Username is too long.");
			userName.clear();
		} else if (un.length() < 6) {
			statusLabel.setText("Username is too short.");
			userName.clear();
		} else if (!pw1.equals(pw2)) {
			statusLabel.setText("You failed to re-enter the same password.");
			userPassword1.clear();
			userPassword2.clear();
		} else if (pw1.length() > 12) {
			statusLabel.setText("Password is too long.");
			userPassword1.clear();
			userPassword2.clear();
		} else if (pw1.length() < 6) {
			statusLabel.setText("Password is too short.");
			userPassword1.clear();
			userPassword2.clear();
		} else {
			if (userModel.createUser(un, pw1)) {
				FXMLLoader loader = new FXMLLoader(
					    getClass().getResource("/FXML/RegisterBrokerFinal.fxml"));
				leftPane.getChildren().setAll((Pane)loader.load());
				
				RegisterBrokerFinalController c = (RegisterBrokerFinalController) loader.getController();
				c.postInitialize(userModel);
			} else {
				statusLabel.setText("Sorry, Username is already taken.");
			}
		}
	}

	/**
	 * Event handler for when the user clicks the clear-user button.
	 * Causes the textfields on screen to be clear of all data.
	 * @param event
	 */
	@FXML
	public void clearUser(ActionEvent event) {
		userName.clear();
		userPassword1.clear();
		userPassword2.clear();
	}
	
}

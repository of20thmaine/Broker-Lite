package brokerlite;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegistrationController implements Initializable{
	package brokerlite;

		
	public RegistrationModel registrationModel = new RegistrationModel();
	
	@FXML
	private Label isConnected;
	@FXML
	private TextField last_name,first_name,phone_num,email,address;
	@FXML
	private Slider investment;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(loginModel.isDbConnected()) {
			isConnected.setText("Connection Established");
		} else {
			isConnected.setText("No Connection");
		}
	}
	
	public void register(ActionEvent event) {
		try {
			isConnected.setText("Redirected to registration");
			
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Broker Lite");
			primaryStage.getIcons().add(new Image("/img/icon.png"));
			primaryStage.setMinHeight(600);
			primaryStage.setMinWidth(1000);
			
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/FXML/Registration.fxml").openStream());

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			primaryStage.show();

		} catch (SQLException e) {
			isConnected.setText("Unable to add client.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

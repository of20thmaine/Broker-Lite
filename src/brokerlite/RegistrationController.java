package brokerlite;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegistrationController implements Initializable{

		
	public RegistrationModel registrationModel = new RegistrationModel();
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField address, last_name, phone_number, first_name, email;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private Slider investment;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	public void submitUser(ActionEvent event) {
		
		int money = (int)investment.getValue();
		int phoneNum = Integer.valueOf(phone_number.getText());
		try {
			registrationModel.isComplete(last_name.getText(),
										 first_name.getText(),
										 phoneNum,
										 email.getText(),
										 address.getText(),
										 money);
			backUser(event);
		} catch (SQLException s){
			clearUser();
			System.out.println("Invalid data.");
		}
	}
	
	public void clearUser() {
		address.clear();
		last_name.clear();
		phone_number.clear();
		first_name.clear();
		email.clear();
	}
	
	public void backUser(ActionEvent event) {
		try {
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Broker Lite");
			primaryStage.getIcons().add(new Image("/img/icon.png"));
			primaryStage.setMinHeight(800);
			primaryStage.setMinWidth(600);
			primaryStage.setResizable(false);
			
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/FXML/Login.fxml").openStream());
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

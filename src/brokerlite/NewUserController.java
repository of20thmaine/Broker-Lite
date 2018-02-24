package brokerlite;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NewUserController implements Initializable {

	NewUserModel newUserModel = new NewUserModel();
	
    @FXML
    private PasswordField password;

    @FXML
    private PasswordField vPassword;

    @FXML
    private TextField username;
    
    @FXML
    private Label message;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

    @FXML
    void submit(ActionEvent event) {
    	try {
    		newUserModel.createUser(username.getText(), password.getText(), vPassword.getText());
    		this.enterInfo(event);
    	} catch (SQLException s) {
    		this.clear(event);
    		message.setText("Invalid username or password");
    		s.printStackTrace();
    	}
    }

    @FXML
    void clear(ActionEvent event) {
    	username.clear();
    	password.clear();
    	vPassword.clear();
    	message.setText("Cleared");
    }
    
    private void enterInfo (ActionEvent event) {
		try {
				((Node)event.getSource()).getScene().getWindow().hide();
				
				Stage primaryStage = new Stage();
				primaryStage.setTitle("Broker Lite");
				primaryStage.getIcons().add(new Image("/img/icon.png"));
//				primaryStage.setMinHeight(580);
//				primaryStage.setMinWidth(600);
				
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/FXML/Registration.fxml").openStream());
				RegistrationController registrationController = (RegistrationController)loader.getController();

				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
				primaryStage.setScene(scene);
				
				primaryStage.show();
				
		} catch (IOException e) {
			e.printStackTrace();
		}
    		
    }
}

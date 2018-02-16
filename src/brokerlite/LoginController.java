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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
	public LoginModel loginModel = new LoginModel();
	
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Label isConnected;
	@FXML
	private TextField userName;
	@FXML
	private TextField userPassword;
	@FXML
	private Button register;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(loginModel.isDbConnected()) {
			isConnected.setText("Connection Established");
		} else {
			isConnected.setText("No Connection");
		}
	}
	
	public void registerUser(ActionEvent event) {
		try {
				((Node)event.getSource()).getScene().getWindow().hide();
				
				Stage primaryStage = new Stage();
				primaryStage.setTitle("Broker Lite");
				primaryStage.getIcons().add(new Image("/img/icon.png"));
				primaryStage.setMinHeight(580);
				primaryStage.setMinWidth(600);
				
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
		
//		try {
//			AnchorPane registerUserPage = FXMLLoader.load(getClass().getResource("/FXML/Registration.fxml"));
//			anchorPane.getChildren().add(registerUserPage);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public void login(ActionEvent event) {
		try {
			if(loginModel.isLogin(userName.getText(), userPassword.getText())) {
				isConnected.setText("Login Succesful");
				String[] userData = loginModel.getUser();
				
				((Node)event.getSource()).getScene().getWindow().hide();
				
				Stage primaryStage = new Stage();
				primaryStage.setTitle("Broker Lite");
				primaryStage.getIcons().add(new Image("/img/icon.png"));
				primaryStage.setMinHeight(600);
				primaryStage.setMinWidth(1000);
				
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/FXML/MainPage.fxml").openStream());
				MainPageController mainPageController = (MainPageController)loader.getController();

				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
				primaryStage.setScene(scene);
				
				primaryStage.show();
				mainPageController.postInitialize(userData);
				
			} else {
				isConnected.setText("Username or Password is not correct.");
			}
		} catch (SQLException e) {
			isConnected.setText("Username or Password is not correct.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}

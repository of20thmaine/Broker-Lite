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

public class NewLoginController implements Initializable {
	
	private UserModel userModel = new UserModel();
	@FXML
	private Pane leftPane;
	@FXML
	private Pane rightPane;
	@FXML
	private TextField userName;
	@FXML
	private TextField userPassword;
	@FXML
	private Label isConnected;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {/* Inherited. */}
	
	public void login(ActionEvent event) {
		try {
			if(userModel.authenticateUser(userName.getText(), userPassword.getText())) {
				
				userModel.populateCustomers();
				userModel.populateUserData();
				
				((Node)event.getSource()).getScene().getWindow().hide();
				
				Stage primaryStage = new Stage();
				primaryStage.setTitle("Broker-Lite");
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
				mainPageController.postInitialize(userModel);
				
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
	
	public void registerUser(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
			    getClass().getResource("/FXML/RegisterBroker.fxml"));
		
		leftPane.getChildren().setAll((Pane)loader.load());
	}

}

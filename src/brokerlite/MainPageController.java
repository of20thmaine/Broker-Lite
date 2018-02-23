package brokerlite;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPageController implements Initializable {
	
	private UserModel userModel;
	private ArrayList<Customer> customers;
	
	@FXML
	private Label isConnected;
	@FXML
	private Label userLabel;
	@FXML
	private MenuBar menu;
	@FXML
	private HBox hbox;
	@FXML
	private GridPane gridPane;
	@FXML
	private VBox sideMenu;
	@FXML
	private ScrollPane scrollPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HBox.setHgrow(menu, Priority.ALWAYS);
		isConnected.setText("Connection Established");
	}
	
	public void postInitialize(UserModel user) {
		userModel = user;
		this.displayUser();
		
		customers = userModel.getCustomers();
	    	
	    this.displayCustomers();
	}
	
	private void displayUser() {
		userLabel.setText("Welcome " + userModel.getFirstName() + " " + userModel.getLastName() + "!");
	}
	
	private void displayCustomers() {
		for(Customer c : customers) {
			Button b = new Button();
			b.setId("customer-button");
			b.setText(c.getName() + "\n" + c.getCash());
			b.setMaxWidth(Double.MAX_VALUE);
			sideMenu.getChildren().add(b);
		}
	}
	
	public void userSignOut(ActionEvent event) {
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
	
	@FXML
	private void platformExit() {
		Platform.exit();
	}
	
}

package brokerlite;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class MainPageController implements Initializable {
	
	@FXML
	private Label userLabel;
	@FXML
	private MenuBar menu;
	@FXML
	private HBox hbox;

	@SuppressWarnings("static-access")
	@Override
	public void initialize(URL location, ResourceBundle resources) { 
		hbox.setHgrow(menu, Priority.ALWAYS);
	}
	
	public void getUserWelcome(String user) {
		userLabel.setText("Welcome " + user + "!");
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
			Pane root = loader.load(getClass().getResource("/brokerlite/Login.fxml").openStream());
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}

package brokerlite;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class MainPageController implements Initializable {
	
	private String[] testStocks = new String[]{"AAPL", "AMZN", "GOOG", "ORCL", "MSFT"};
	private String[] userData;
	@FXML
	private Label userLabel;
	@FXML
	private MenuBar menu;
	@FXML
	private HBox hbox;
	@FXML
	private GridPane gridPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) { }
	
	public void postInitialize(String[] userData) {
		HBox.setHgrow(menu, Priority.ALWAYS);
		this.userData = userData;
		this.displayUser();
		this.displayLiveQuotes();
	}
	
	private void displayUser() {
		userLabel.setText("Welcome " + userData[1] + " " + userData[2] + "!");
	}
	
	private void displayLiveQuotes() {
		gridPane.getChildren().clear();
		for(int i = 0; i < testStocks.length; i++) {
			StockQuote s = StockController.getStockQuote(testStocks[i]);
			
			Button button = new Button();
			button.setText(s.getSymbol() + "\n" + s.getLastPrice());
			
			gridPane.add(button, i, 0);
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

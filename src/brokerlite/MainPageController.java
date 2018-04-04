package brokerlite;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPageController implements Initializable {
	
	private UserModel userModel;
	private StockModel stockModel;
	private ObservableList<Customer> customers;
	
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
	@FXML
	private TabPane tabPane;
	@FXML
	private Pane pbPane;
	@FXML
	private ProgressBar pb;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HBox.setHgrow(menu, Priority.ALWAYS);
		isConnected.setText("Connection Established");
		tabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
	}
	
	public void postInitialize(UserModel user) {
		userModel = user;
		this.displayUser();
	    this.displayCustomers();
	    
	    isConnected.setText("Retrieving live market data...");
	    
	    pb.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
	    
	    this.startTask();
	}
	
	public void startTask() {
		Runnable task = new Runnable() {
			public void run() {
				runTask();
			}
		};
		// Run the task in a background thread
		Thread backgroundThread = new Thread(task);
		// Terminate the running thread if the application exits
		backgroundThread.setDaemon(true);
		// Start the thread
		backgroundThread.start();
	}

	public void runTask() {
		try {
		    stockModel = new StockModel();
		    stockModel.startUpdate();
		    Platform.runLater(() -> {
		    	isConnected.setText("Market data up to date!");
				this.displayMarkeyTab();
				pbPane.getChildren().remove(pb);
		    });
        } catch (Exception e) {
            e.printStackTrace();
        }
	}      
	
	private void displayUser() {
		userLabel.setText("Welcome " + userModel.getFirstName() + " " + userModel.getLastName() + "!");
	}
	
	private void displayCustomers() {
		customers = FXCollections.observableArrayList(userModel.getCustomers());
		
		sideMenu.getChildren().clear();
		sideMenu.setSpacing(5.0);
		
		for(Customer c : customers) {
			Button b = new Button();
			b.setId("customer-button");
			b.setText(c.getName() + "\n" + c.getCash());
			b.setMaxWidth(Double.MAX_VALUE);
			
			b.setOnAction( e -> {
				this.generateCustomerTab(c);
			});
			
			sideMenu.getChildren().add(b);
		}
	}
	
	private void generateCustomerTab(Customer c) {
		try {
			FXMLLoader loader = new FXMLLoader(
				    getClass().getResource("/FXML/CustomerTab.fxml"));
			
			CustomerTabController controller = new CustomerTabController();
			loader.setController(controller);
			
			Tab customerTab = new Tab(c.getName());
			
			ScrollPane tabScroller = (ScrollPane)loader.load();
			customerTab.setContent(tabScroller);

			customerTab.setOnClosed(e -> { 
				displayCustomers();
				e.consume();
			});
			
			tabPane.getTabs().add(customerTab);
			tabPane.getSelectionModel().select(customerTab);
			
			controller.initializer(c);
		} catch (IOException e) {
			System.out.println("Duplicate tab handled.");
		}
	}
	
	public void userSignOut(ActionEvent event) {
		try {
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Broker Lite");
			primaryStage.getIcons().add(new Image("/img/icon.png"));
			primaryStage.setMinHeight(500);
			primaryStage.setMinWidth(1000);
			primaryStage.setResizable(false);
			
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/newLogin.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void displayMarkeyTab() {
		FXMLLoader loader = new FXMLLoader(
			    getClass().getResource("/FXML/MarketTab.fxml"));
		MarketTabController controller = new MarketTabController();
		loader.setController(controller);
		
		try {
			Tab market = new Tab("Market");
			market.setContent((ScrollPane)loader.load());
			market.setOnClosed(e -> { e.consume(); });
			
			tabPane.getTabs().add(market);
			tabPane.getSelectionModel().select(market);
			controller.initializer(stockModel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void displayCustomerRegistration() {
		FXMLLoader loader = new FXMLLoader(
			    getClass().getResource("/FXML/CustomerRegistration.fxml"));
		
		try {
			Tab customerRegister = new Tab("Register Customer");
			ScrollPane content = (ScrollPane)loader.load();
			content.prefWidthProperty().bind(tabPane.widthProperty());
			content.prefHeightProperty().bind(tabPane.heightProperty());
			customerRegister.setContent(content);
			
			CustomerRegistrationController c = (CustomerRegistrationController) loader.getController();
			c.initializer(this);
			
			tabPane.getTabs().add(customerRegister);
			tabPane.getSelectionModel().select(customerRegister);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void platformExit() {
		Platform.exit();
	}
	
	public boolean registerCustomer(String lname, String fname, String phoneNum, String email, String address, double investAmount) {
		try {
			if (userModel.registerCustomer(lname, fname, phoneNum, email, address, investAmount)) {
				this.displayCustomers();
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}
	
	
}

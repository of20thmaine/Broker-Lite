package brokerlite;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Bobby Palmer, Samnang Pann
 * Class implements underlying logic of main-page UI.
 */
public class MainPageController implements Initializable {
	
	private UserModel userModel;
	private StockModel stockModel;
	private ObservableList<Customer> customers;
	private ObservableList<Stock> stocks;
	ObservableList<Stock> stocksOwned;
	private ArrayList<String> openTabs = new ArrayList<String>();
	
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
	    
	    isConnected.setText("Retrieving live market data.");
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
		    	isConnected.setText("Market data up to date.");
		    	this.displayCustomers();
		    	this.bestWorstPerformers();
				this.displayMarkeyTab();
				pb.setVisible(false);
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
		sideMenu.setSpacing(7.0);
		
		for(Customer c : customers) {
			try {
				stockModel.getCustomerStocks(c);
				
				HBox customerButton = new HBox();
				VBox labelStack = new VBox(0.25);
				customerButton.setId("customer-button");
				
				Label performance;
				Label cName = new Label(c.getName());
				cName.setId("cname-label");
				
				DecimalFormat formatter = new DecimalFormat("#,###.00");
				
				if (c.getTodayPerformance() >= 0) {
					performance = new Label("↑  " + formatter.format(c.getTodayPerformance()));
					performance.setId("positive-cash-label");
				} else {
					performance = new Label("↓  " + formatter.format((-1)*c.getTodayPerformance()));
					performance.setId("negative-cash-label");
				}
				
				customerButton.setAlignment(Pos.CENTER);
				labelStack.setMaxWidth(Double.MAX_VALUE);
				labelStack.setAlignment(Pos.CENTER);
				
				labelStack.getChildren().add(cName);
				labelStack.getChildren().add(performance);

				customerButton.getChildren().add(labelStack);
				
				customerButton.setMaxWidth(Double.MAX_VALUE);
				
				EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
					   @Override 
					   public void handle(MouseEvent e) { 
						   generateCustomerTab(c);  
					   } 
					};
				customerButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
				
				sideMenu.getChildren().add(customerButton);

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		this.registerCustomerButton();
	}
	
	private void generateCustomerTab(Customer c) {
		for (String t : openTabs) {
			if (t.equals(c.getId()+"")) {
				isConnected.setText("Tab Open.");
				return;
			}
		}
		
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
			
			controller.initializer(c, stocks, stockModel, this);
			openTabs.add(c.getId()+"");
			
			customerTab.setOnClosed(event -> {
				for (int i = 0; i < openTabs.size(); i++) {
					if (openTabs.get(i).equals(c.getId()+"")) {
						openTabs.remove(i);
					}
				}
			});
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
		for (String t : openTabs) {
			if (t.equals("market")) {
				isConnected.setText("Tab Open.");
				return;
			}
		}
		
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
			controller.initializer(customers, stocksOwned);
			openTabs.add("market");
			market.setOnClosed(event -> {
				for (int i = 0; i < openTabs.size(); i++) {
					if (openTabs.get(i).equals("market")) {
						openTabs.remove(i);
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void displayCustomerRegistration() {
		for (String t : openTabs) {
			if (t.equals("register")) {
				isConnected.setText("Tab Open.");
				return;
			}
		}
		
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
			openTabs.add("register");
			customerRegister.setOnClosed(event -> {
				for (int i = 0; i < openTabs.size(); i++) {
					if (openTabs.get(i).equals("register")) {
						openTabs.remove(i);
					}
				}
			});
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
	
	private void bestWorstPerformers() {
		stocks = FXCollections.observableArrayList(stockModel.getStocks());
		
		FXCollections.sort(customers, new Comparator<Customer>() {
			@Override
			public int compare(Customer o1, Customer o2) {
				return (int) (o1.getTodayPerformance() - o2.getTodayPerformance());
			}
		});
		
		stocksOwned = FXCollections.observableArrayList();
		
		for (Stock s : stocks) {
			if (s.isOwned()) {
				stocksOwned.add(s);
			}
		}
		
		FXCollections.sort(stocksOwned, new Comparator<Stock>() {
			@Override
			public int compare(Stock o1, Stock o2) {
				return (int) (o1.getChangeValue() - o2.getChangeValue());
			}
		});
		
	}

	private void registerCustomerButton() {
		HBox registerButton = new HBox();
		VBox labelStack = new VBox(0.25);
		registerButton.setId("customer-button");
		
		Label cName = new Label("Register\nNew Customer");
		cName.setId("register-button-label");
		cName.setWrapText(true);
		
		
		registerButton.setAlignment(Pos.CENTER);
		labelStack.setMaxWidth(Double.MAX_VALUE);
		labelStack.setAlignment(Pos.CENTER);
		
		labelStack.getChildren().add(cName);

		registerButton.getChildren().add(labelStack);
		
		registerButton.setMaxWidth(Double.MAX_VALUE);
		
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
			   @Override 
			   public void handle(MouseEvent e) { 
				   displayCustomerRegistration();  
			   } 
			};
			registerButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
		
		sideMenu.getChildren().add(registerButton);
	}
	
	@FXML
	private void showAboutPage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About Broker-Lite");
		alert.setHeaderText("Broker-Lite v 0.1 (Alpha)");
		alert.setContentText("This application was created by Bobby Palmer and Samnang Pann for a University-class-project");

		alert.showAndWait();
	}
	
	public void buySellRefresh(Customer c) {
		pb.setVisible(true);
		
		isConnected.setText("Executing transaction.");
	    pb.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
	    
		ArrayList<String> newOpen = new ArrayList<String>(openTabs);
		openTabs.clear();
		tabPane.getTabs().clear();
		
		this.refreshTask(c, newOpen);
	}
	
	public void refreshTask(Customer c, ArrayList<String> newOpen) {
		Runnable task = new Runnable() {
			public void run() {
				runRefreshTask(c, newOpen);
			}
		};
		// Run the task in a background thread
		Thread backgroundThread = new Thread(task);
		// Terminate the running thread if the application exits
		backgroundThread.setDaemon(true);
		// Start the thread
		backgroundThread.start();
	}

	public void runRefreshTask(Customer c, ArrayList<String> newOpen) {
		try {
		    stockModel.startUpdate();
		    
		    Platform.runLater(() -> {
		    	isConnected.setText("Transaction succesful.");
		    	
				pb.setVisible(false);
				this.displayCustomers();
				
				for (String t : newOpen) {
					if (t.equals("market")) {
						this.displayMarkeyTab();
					} else if (t.equals("register")) {
						this.displayCustomerRegistration();
					} else if (!t.equals(c.getId() + "")) {
						for (Customer cust : customers) {
							if (t.equals(cust.getId() +"")) {
								this.generateCustomerTab(cust);
							}
						}
					}
				}
				
				this.generateCustomerTab(c);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initStyle(StageStyle.UTILITY);
				alert.setTitle("Transaction Succesful");
				alert.setHeaderText(null);
				alert.setContentText("Your transaction completed succesfully.");

				alert.showAndWait();
		    });
        } catch (Exception e) {
            e.printStackTrace();
        }
	} 
	
	
}

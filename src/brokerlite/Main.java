package brokerlite;
	

import javafx.application.Application;
//import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Broker Lite");
			primaryStage.getIcons().add(new Image("/img/icon.png"));
			primaryStage.setMinHeight(500);
			primaryStage.setMinWidth(1000);
			primaryStage.setResizable(false);
			
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/NewLogin.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		/**
		 * Not multithreading correctly.
		 */
		
		StockModel sm = new StockModel();
		sm.startUpdate();
//		
//		Task<Void> task = new Task<Void> () {
//			@Override
//			public Void call() throws Exception {
//				System.out.println("Test");
//				
//				return null;
//			}
//		};
//		
//		new Thread(task).run();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

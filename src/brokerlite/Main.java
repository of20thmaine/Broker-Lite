package brokerlite;
	
import javafx.application.Application;
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
			primaryStage.setMinHeight(800);
			primaryStage.setMinWidth(600);
			primaryStage.setResizable(false);
			
			Parent root = FXMLLoader.load(getClass().getResource("/brokerlite/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

package brokerlite;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * @author Bobby Palmer, Samnang Pann
 * Class is initialized by the JRE and calls start function when application executed.
 */
public class Main extends Application {
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * Automatically generated JavaFX method for starting the application.
	 */
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
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Allows application to be run under circumstances where
	 * the conventional method fails.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

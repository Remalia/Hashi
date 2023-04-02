package Application.FrontEnd.Controller;
/**
 * @author 	: 	Thibaut
 * @version : 	1.0
 * @date	:	2016-04-12
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Main class of the application Hashi
 */
public class Main extends Application {
	
	// Attributes
	Parent root;
	Scene scene;
	    
	/**
	 * Start the application
	 * @param window : the window of the application
	 */
	@Override
	public void start(Stage window) {
		try {
			root = FXMLLoader.load(getClass().getResource("./FXML/menu_p.fxml"));
			scene = new Scene(root);
			window.setTitle("Hashi");
			window.setScene(scene);
			window.setResizable(false);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Main method of the application
	 * @param args : arguments of the main method
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

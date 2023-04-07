package Application.FrontEnd.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Main class of the application Hashi
 * It is used to launch the application
 * It is also used to switch between the different scenes of the application
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */
public class Main extends Application {

	// Attributes
	Parent root;
	Scene scene;
	private static final String CSS_PATH = "../assets/dark_mode.css";

	/**
	 * Start the application Hashi
	 * @param window : the window of the application
	 */
	@Override
	public void start(Stage window) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/menu_p.fxml"));
			this.root = loader.load();
			this.scene = new Scene(root);
			this.scene.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
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


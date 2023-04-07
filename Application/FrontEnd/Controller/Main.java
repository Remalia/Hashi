package Application.FrontEnd.Controller;

import java.io.IOException;
import java.util.Objects;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

/**
 * This class is the controller of the main scene
 * It allows to switch between the different scenes of the application
 * It also allows to quit the application
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */
public class Main extends Application {

	// Attributes
	Stage window;
	Parent root;
	Scene scene;
	static final String DEFAULT_CSS = "file:/home/thibaut/Hashi2/out/production/Hashi2/Application/FrontEnd/assets/light_mode.css"; // define the default CSS value first
	String css = DEFAULT_CSS; // initialize the css variable
	protected Preferences prefs = Preferences.userNodeForPackage(Main.class);

	/**
	 * Method to switch to a new scene
	 *
	 * @param file  the file of the scene to switch to
	 * @param event the event that triggers the switch
	 * @throws IOException if the file is not found
	 */
	public void switchToScene(String file, Event event) throws IOException {
		window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(file)));
		scene = new Scene(root);
		scene.getStylesheets().add(css);
		window.setResizable(false);
		window.setScene(scene);
		window.show();
	}

	/**
	 * Method to go to the settings menu
	 *
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception thrown if the file is not found
	 */
	@FXML
	public void parametres(MouseEvent event) throws IOException {
		switchToScene("../FXML/paramètres.fxml", event);
	}

	/**
	 * Method to switch to the credits scene
	 *
	 * @param event : the event that triggers the switch
	 */
	@FXML
	public void credits(Event event) throws IOException {
		switchToScene("../FXML/crédits.fxml", event);
	}

	/**
	 * Method to switch to the menu scene
	 *
	 * @param event : the event that triggers the switch
	 */
	@FXML
	public void menu_principal(Event event) throws IOException {
		switchToScene("../FXML/menu_p.fxml", event);
	}

	/**
	 * Start the application Hashi
	 * @param window : the window of the application
	 */
	@Override
	public void start(Stage window) throws BackingStoreException {
		try {
			this.root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/menu_p.fxml")));
			this.scene = new Scene(root);
			this.scene.getStylesheets().add(getClass().getResource("../assets/dark_mode.css").toExternalForm());
			window.setTitle("Hashi");
			window.setScene(scene);
			window.setResizable(false);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

		css = prefs.get("mon_css", DEFAULT_CSS);
		scene = new Scene(new Group());
		scene.getStylesheets().add(css);

		String[] keys = prefs.keys();
		for (String key : keys) {
			System.out.println(key + " : " + prefs.get(key, ""));
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

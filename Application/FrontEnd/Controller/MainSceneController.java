package Application.FrontEnd.Controller;

import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
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
public class MainSceneController {

	// Attributes
	Stage window;
	Parent root;
	Scene scene;
	String css = DEFAULT_CSS; // initialize the css variable
	static final String DEFAULT_CSS = "../assets/dark_mode.css";


	protected Preferences prefs = Preferences.userNodeForPackage(MainSceneController.class);

	/**
	 * Method to switch to a new scene
	 *
	 * @param file  the file of the scene to switch to
	 * @param event the event that triggers the switch
	 * @throws IOException if the file is not found
	 */
	public void switchToScene(String file, Event event) throws IOException {
		window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource(file));
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

	@FXML
	public void initialize() throws IOException, BackingStoreException {
		css = prefs.get("mon_css", DEFAULT_CSS);
		scene = new Scene(new Group());
		scene.getStylesheets().add(css);

		String[] keys = prefs.keys();
		for (String key : keys) {
			System.out.println(key + " : " + prefs.get(key, ""));
		}
	}
}

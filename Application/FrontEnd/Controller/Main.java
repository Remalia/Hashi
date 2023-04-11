package Application.FrontEnd.Controller;

import java.io.IOException;
import java.util.Objects;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
	static final String DEFAULT_CSS = "../assets/light_mode.css";
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
		css = prefs.get("mon_css", DEFAULT_CSS);
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
	public void start(Stage window) {
		css = prefs.get("mon_css", DEFAULT_CSS);

		try {
			this.root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/menu_p.fxml")));
			this.scene = new Scene(root);
			scene.getStylesheets().add(css);
			window.setTitle("Hashi");
			window.setScene(scene);
			window.setResizable(false);
			Image iconPrincipale = new Image("Application/FrontEnd/assets/IconHashi.png");
			window.getIcons().add(iconPrincipale);
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

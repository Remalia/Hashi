package Application.FrontEnd.Controller;

import java.io.IOException;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXML;
/**
 * This class is the controller of the main scene
 * It allows to switch between the different scenes of the application
 * It also allows to quit the application
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */
public class MenuController extends Main {

	/**
	 * Method to show the tutorial for the game board
	 *
	 * @param event the event that triggers the switch
	 * @throws IOException if the file is not found
	 */
	@FXML
	public void tutoriel_plateau(Event event) throws IOException {
		switchToScene("../FXML/tutoriel_plateau.fxml", event);
	}

	/**
	 * Method to start the game
	 *
	 * @param event the event that triggers the switch
	 * @throws IOException if the file is not found
	 */
	@FXML
	public void plateau(Event event) throws IOException {
		switchToScene("../FXML/plateau.fxml", event);
	}

	/**
	 * Method to go to the second menu
	 *
	 * @param event the event that triggers the switch
	 * @throws IOException if the file is not found
	 */
	@FXML
	public void menu_secondaire(Event event) throws IOException {
		switchToScene("../FXML/menu_s.fxml", event);
	}

	/**
	 * Method to go to the settings menu
	 *
	 * @param event the event that triggers the switch
	 * @throws IOException if the file is not found
	 */
	@FXML
	public void parametres(Event event) throws IOException {
		switchToScene("../FXML/paramètres.fxml", event);
	}

	/**
	 * Method to switch to the tutorial scene
	 *
	 * @param event the event that triggers the switch
	 * @throws IOException if the file is not found
	 */
	@FXML
	public void tutoriel(Event event) throws IOException {
		switchToScene("../FXML/tutoriel.fxml", event);
	}

	/**
	 * Method to quit the application
	 *
	 * @param event the event that triggers the quit
	 */
	@FXML
	public void quitter(Event event) {
		window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.close();
	}

}

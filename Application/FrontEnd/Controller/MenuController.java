package Application.FrontEnd.Controller;

import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
/**
 * This class is the controller of the main scene
 * It allows to switch between the different scenes of the application
 * It also allows to quit the application
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */
public class MenuController extends MainSceneController {

	@FXML private CheckBox checkbox;


	@FXML
	public void mode_sombre(ActionEvent event) {
		if (checkbox.isSelected()) {
			System.out.println("La checkbox est cochée");
		} else {
			System.out.println("La checkbox n'est pas cochée");
		}
	}


	/**
	 * method to go to the settings menu
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception thrown if the file is not found
	 */
	@FXML
	public void menu_param_m(MouseEvent event) throws IOException{
		img_scene("../FXML/paramètres.fxml",event);
	}

	/**
	 * method to go to the settings menu
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception thrown if the file is not found
	 */
	@FXML
	public void retour_mode(MouseEvent event) throws IOException{
		img_scene("../FXML/menu_s.fxml",event);
	}

	/**
	 * method to go to the settings menu
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception thrown if the file is not found
	 */
	@FXML
	public void showplateau(ActionEvent event) throws IOException{
		scene("../FXML/tutoriel_plateau.fxml",event);
	}

	/**
	 * method to go to the game
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception thrown if the file is not found
	 */
	@FXML
	public void lancer(ActionEvent event) throws IOException{
		scene("../FXML/plateau.fxml",event);
	}

	/**
	 * method to switch to the credits scene
	 * @param event : the event that triggers the switch
	 */
	@FXML
	public void credits(MouseEvent event) throws IOException {
		img_scene("../FXML/crédits.fxml",event);
	}

	/**
	 * method to switch to the menu scene
	 * @param event : the event that triggers the switch
	 */
	@FXML
	public void retour_menu_p(MouseEvent event) throws IOException {
		img_scene("../FXML/menu_p.fxml",event);
	}

	/**
	 * method to go to the second menu
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception thrown if the file is not found
	 */
	@FXML
	public void menu_jouer(ActionEvent event) throws IOException{
        scene("../FXML/menu_s.fxml",event);
	}
	
	/**
	 * method to go to the settings menu
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception thrown if the file is not found
	 */
	@FXML
	public void menu_param(ActionEvent event) throws IOException{
		scene("../FXML/paramètres.fxml",event);
	}
	
	/**
	 * method to switch to the tutorial scene
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception thrown if the file is not found
	 */
	@FXML
	public void switchtuto(ActionEvent event) throws IOException{
        scene("../FXML/tutoriel.fxml",event);
	}
	
	/**
	 * method to quit the application
	 * @param event : the event that triggers the quit
	 */
	@FXML
	public void quitter(ActionEvent event) {
		window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	    window.close();
	}


}

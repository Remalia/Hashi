package Application.FrontEnd.Controller;

import java.io.IOException;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

/**
 * This class is the controller of the main scene
 * It allows to switch between the different scenes of the application
 * It also allows to quit the application
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */
public class CreditController extends MainSceneController {
	
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

}

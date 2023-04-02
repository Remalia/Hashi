package Application.FrontEnd.Controller;
/*
 * @author 	: 	Thibaut
 * @version : 	1.0
 * @date	:	2016-04-12
 */

import java.io.IOException;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

/**
 * This class is the controller of the main scene
 * It allows to switch between the different scenes of the application
 * It also allows to quit the application
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

	@FXML
	public void retour_menu_p(MouseEvent event) throws IOException {
		img_scene("../FXML/menu_p.fxml",event);
	}

}

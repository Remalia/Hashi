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
public class ParamController extends MainSceneController{
	
	/**
	 * method to go to the settings menu
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception thrown if the file is not found
	 */
	@FXML
	public void menu_param_m(MouseEvent event) throws IOException{
		img_scene("../FXML/paramètres.fxml",event);
	}
	


}

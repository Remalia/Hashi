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
public class ParamController extends MainSceneController{
	
	/**
	 * method to go to the settings menu
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void menu_param_m(MouseEvent event) throws IOException{
		img_scene("./FXML/parametre.fxml",event);
	}
	


}

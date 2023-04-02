package Application.FrontEnd.Controller;/*
 * @author 	: 	Thibaut
 * @version : 	1.0
 * @date	:	2016-04-12
 */

import javafx.fxml.FXML;
import java.io.IOException;
import javafx.event.ActionEvent;
/**
 * This class is the controller of the main scene
 * It allows to switch between the different scenes of the application
 * It also allows to quit the application
 */
public class ModeController extends RetourController{
	
	/**
	 * method to switch to the mode libre scene
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void switchlibre(ActionEvent event) throws IOException{
		scene("../FXML/jeulibre.fxml",event);
	}
	
	/**
	 * method to switch to the mode aventure scene
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void switchaventure(ActionEvent event) throws IOException{
		scene("../FXML/jeuaventure.fxml",event);
	}

}

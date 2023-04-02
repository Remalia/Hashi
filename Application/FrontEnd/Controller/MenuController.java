package Application.FrontEnd.Controller;/*
 * @author 	: 	Thibaut
 * @version : 	1.0
 * @date	:	2016-04-12
 */

import java.io.IOException;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
/**
 * This class is the controller of the main scene
 * It allows to switch between the different scenes of the application
 * It also allows to quit the application
 */
public class MenuController extends CreditController{
	
	/**
	 * method to go to the second menu
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void menu_jouer(ActionEvent event) throws IOException{
        scene("../FXML/menu_s.fxml",event);
	}
	
	/**
	 * method to go to the settings menu
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void menu_param(ActionEvent event) throws IOException{
		scene("../FXML/parametre.fxml",event);
	}
	
	/**
	 * method to switch to the tutorial scene
	 * @param event : the event that triggers the switch
	 * @throws IOException
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

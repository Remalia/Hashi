package Application.FrontEnd.Controller;

/*
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */

import java.io.IOException;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

/**
 * This class is the controller of the main scene
 * It allows to switch between the different scenes of the application
 * It also allows to quit the application
 */
public class RetourController extends CreditController {
	
	/**
	 * method to switch to the menu principal scene
	 * @param event : the event that triggers the switch
	 * @throws IOException 
	 */
	@FXML
	public void retour_menu_p(MouseEvent event) throws IOException {
	    img_scene("../FXML/menu_p.fxml",event);
	}
	
	/**
	 * method to go to the settings menu
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void menu_param_m(MouseEvent event) throws IOException{
		img_scene("../FXML/parametre.fxml",event);
	}
	
	/**
	 * method to go to the settings menu
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void retour_mode(MouseEvent event) throws IOException{
		img_scene("../FXML/menu_s.fxml",event);
	}
	
	/**
	 * method to go to the settings menu
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void showplateau(ActionEvent event) throws IOException{
		scene("../FXML/tutoriel_plateau.fxml",event);
	}
	
	/**
	 * method to go to the game
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void lancer(ActionEvent event) throws IOException{
		scene("../FXML/plateau.fxml",event);
	}
	
	

}

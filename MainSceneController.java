package application;

/*
 * @author 	: 	Thibaut
 * @version : 	1.0
 * @date	:	2016-04-12
 */

import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

/**
 * This class is the controller of the main scene
 * It allows to switch between the different scenes of the application
 * It also allows to quit the application
 */
public class MainSceneController{
	
	// Attributes
	Stage window;
	Parent root;
	Scene scene;
	
	/**
	 * Switch between the different scenes of the application
	 * @param file : the file of the scene to switch to
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	public void scene(String file, ActionEvent event) throws IOException{
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource(file));
        scene = new Scene(root);
		window.setResizable(false);
        window.setScene(scene);
        window.show();
	}
	
	/**
	 * Switch between the different scenes of the application with an image
	 * @param file : the file of the scene to switch to 
	 * @param event : the event that triggers the switch 
	 * @throws IOException
	 */
	public void img_scene(String file, MouseEvent event) throws IOException{
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource(file));
        scene = new Scene(root);
		window.setResizable(false);
        window.setScene(scene);
        window.show();
	}
	
	/**
	 * method to switch to the menu secondary scene
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void switchmenu(ActionEvent event) throws IOException{
        scene("menu_s.fxml",event);
	}
	
	/**
     * method to switch to the parameters
     * @param event : the event that triggers the switch
     * @throws IOException
     */
	@FXML
	public void switchparam(ActionEvent event) throws IOException{
		scene("parametre.fxml",event);
	}
	
	/**
	 * method to switch to the tutorial scene
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void switchtuto(ActionEvent event) throws IOException{
        scene("tutoriel.fxml",event);
	}
	
	/**
	 * method to switch to the mode libre scene
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void switchlibre(ActionEvent event) throws IOException{
		scene("jeulibre.fxml",event);
	}
	
	/**
	 * method to switch to the mode aventure scene
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void switchaventure(ActionEvent event) throws IOException{
		scene("jeuaventure.fxml",event);
	}
	
	/**
	 * method to switch to the mode redo scene
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void retour_mode(MouseEvent event) throws IOException {
	    img_scene("menu_s.fxml",event);
	}
	
	/**
	 * method to switch to the menu principal scene
	 * @param event : the event that triggers the switch
	 * @throws IOException 
	 */
	@FXML
	public void retour_menu_p(MouseEvent event) throws IOException {
	    img_scene("menu_p.fxml",event);
	}
	
	/**
	 * method to switch to the credits scene
	 * @param event : the event that triggers the switch
	 * @throws IOException 
	 */
	@FXML
	public void credits(MouseEvent event) throws IOException {
	    img_scene("crédits.fxml",event);
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

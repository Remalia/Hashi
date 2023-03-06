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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
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
	
	private int clickcount=0;
	
	@FXML private ImageView arrow;
	@FXML private ImageView arrow2;
	@FXML private ImageView arrow3;
	@FXML private ImageView arrow4;
	@FXML private Line line;
	@FXML private Line line2;
	@FXML private Line line1;
	@FXML private Line lineR1;
	@FXML private Line lineR2;
	@FXML private Line lineR3;
	@FXML private Line lineR4;
	@FXML private Circle circle1;
	@FXML private Circle circle2;
	@FXML private Circle circle3;
	@FXML private Circle circle4;
	@FXML private Text text;
	@FXML private Text txtR1;
	@FXML private Text txtR2;
	
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
	public void switchparam1(ActionEvent event) throws IOException{
		scene("parametre.fxml",event);
	}
	
	/**
     * method to switch to the parameters
     * @param event : the event that triggers the switch
     * @throws IOException
     */
	@FXML
	public void switchparam(MouseEvent event) throws IOException{
		img_scene("parametre.fxml",event);
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
	 * method to switch to the mode aventure scene
	 * @param event : the event that triggers the switch
	 * @throws IOException
	 */
	@FXML
	public void showplateau(ActionEvent event) throws IOException{
		scene("tutoriel_plateau.fxml",event);
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
	
	@FXML
	public void clic(MouseEvent event) throws IOException{	
		clickcount++;
		circle1.setDisable(true);
		circle2.setDisable(false);
		arrow.setVisible(false);
		arrow2.setVisible(true);
		text.setText("Puis sur ce rond.");
		if(clickcount==2) {
			line.setVisible(true);
			lineR1.setVisible(true);
			lineR2.setVisible(true);
			arrow2.setVisible(false);
			circle2.setDisable(true);
			text.setText("Tu as réussit ! Maintenant faite de même pour les ronds en dessous ");
			clickcount=0;
			
			txtR1.setVisible(true);
			txtR2.setVisible(true);
			circle3.setVisible(true);
			circle4.setVisible(true);
			arrow3.setVisible(true);
		}	
	}
	
	@FXML
	public void clic2(MouseEvent event) throws IOException{	
		clickcount++;
		circle3.setDisable(true);
		circle4.setDisable(false);
		arrow3.setVisible(false);
		arrow4.setVisible(true);
		text.setText("Puis sur ce rond.");
		if(clickcount==2) {
			circle3.setDisable(false);
			circle4.setDisable(true);
			line2.setVisible(true);
			arrow4.setVisible(false);
			arrow3.setVisible(true);
			text.setText("Répète ces actions pour construire le deuxième pont");
		}
		if(clickcount==3) {
			arrow3.setVisible(false);
			arrow4.setVisible(true);
			circle3.setDisable(true);
			circle4.setDisable(false);
		}
		
		if(clickcount==4){
			circle3.setDisable(false);
			lineR4.setVisible(true);
			lineR3.setVisible(true);
			line1.setVisible(true);
		}
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
	
	@FXML 
	public void initialize () {
		if(text != null)
			text.setText("Pour commencer, appuie sur ce rond");
		if (circle2 != null) {
	        circle2.setDisable(true);
	    }
	}

}

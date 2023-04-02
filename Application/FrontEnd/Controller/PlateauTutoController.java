package Application.FrontEnd.Controller;

import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
/**
 * This class is the controller of the main scene
 * It allows to switch between the different scenes of the application
 * It also allows to quit the application
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */
public class PlateauTutoController extends CreditController{
	
	private int clickcount=0;
	private ImageView arrow;


	@FXML private Circle circle1;
	@FXML private Circle circle2;
	@FXML private Circle circle3;
	@FXML private Circle circle4;
	@FXML private Text text;
	@FXML private Text txtR1;
	@FXML private Text txtR2;
	@FXML private Pane pane;
	
	/**
	 * method to go to the settings menu
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception if the file is not found
	 */
	@FXML
	public void menu_param_m(MouseEvent event) throws IOException{
		img_scene("../FXML/parametre.fxml",event);
	}
	
	/**
	 * method to switch to the tutorial scene
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception if the file is not found
	 */
	@FXML
	public void switchtuto2(MouseEvent event) throws IOException{
        img_scene("../FXML/tutoriel.fxml",event);
	}
	
	/**
	 * method to switch to add a line to the tutorial
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception if the file is not found
	 */
	@FXML
	public void clic(MouseEvent event) throws IOException{
		Line line;
		Line lineR1;
		Line lineR2;


	    clickcount++;
	    circle1.setDisable(true);
	    circle2.setDisable(false);
	    
	    //redimensionner mes flèches + mieux les placer
	    // réorganisé le code
	    // faire également le 0 pont
	    arrow.setLayoutX(circle2.getLayoutX() - 110); // Coordonnée X de l'image
	    arrow.setLayoutY(circle2.getLayoutY() + 31); // Coordonnée Y de l'image
	    
	    text.setText("Puis sur ce rond.");
	    if(clickcount==2) {
	        line = new Line(circle1.getLayoutX(), circle1.getLayoutY() - 5, circle2.getLayoutX(), circle2.getLayoutY() - 5);
	        pane.getChildren().add(line);
	        line.toBack();
	        
	        lineR1 = new Line(circle1.getLayoutX()+24, circle1.getLayoutY()+24, circle1.getLayoutX() -24, circle1.getLayoutY()-24);
	        pane.getChildren().add(lineR1);
	        
	        lineR2 = new Line(circle2.getLayoutX()+24, circle2.getLayoutY()+24, circle2.getLayoutX() -24, circle2.getLayoutY()-24);
	        pane.getChildren().add(lineR2);
	        
	        circle2.setDisable(true);
	        text.setText("Tu as réussit ! Maintenant faite de même pour les ronds en dessous ");
	        clickcount=0;
	        
	        txtR1.setVisible(true);
	        txtR2.setVisible(true);
	        circle3.setVisible(true);
	        circle4.setVisible(true);
	        
	        arrow.setLayoutX(circle3.getLayoutX() - 110); // Coordonnée X de l'image
		    arrow.setLayoutY(circle3.getLayoutY() + 31); // Coordonnée Y de l'image
	    }   
	}
	
	/**
	 * method to switch to add a line to the tutorial
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception if the file is not found
	 */
	@FXML
	public void clic2(MouseEvent event) throws IOException{
		Line line2;
		Line line1;
		Line lineR3;
		Line lineR4;

		clickcount++;
		circle3.setDisable(true);
		circle4.setDisable(false);
		
		
		arrow.setLayoutX(circle4.getLayoutX() - 110); // Coordonnée X de l'image
	    arrow.setLayoutY(circle4.getLayoutY() + 31); // Coordonnée Y de l'image
	    
		text.setText("Puis sur ce rond.");
		if(clickcount==2) {
			circle3.setDisable(false);
			circle4.setDisable(true);
			
			line2 = new Line(circle3.getLayoutX(), circle3.getLayoutY() - 10, circle4.getLayoutX(), circle4.getLayoutY() - 10);
			pane.getChildren().add(line2);
			line2.toBack();
			
			arrow.setLayoutX(circle3.getLayoutX() - 110); // Coordonnée X de l'image
		    arrow.setLayoutY(circle3.getLayoutY() + 31); // Coordonnée Y de l'image
		    
			text.setText("Répète ces actions pour construire le deuxième pont");
		}
		if(clickcount==3) {
			arrow.setLayoutX(circle4.getLayoutX() - 110); // Coordonnée X de l'image
		    arrow.setLayoutY(circle4.getLayoutY() + 31); // Coordonnée Y de l'image
			circle3.setDisable(true);
			circle4.setDisable(false);
		}
		
		if(clickcount==4){
			lineR3 = new Line(circle3.getLayoutX()+24, circle3.getLayoutY()+24, circle3.getLayoutX() -24, circle3.getLayoutY()-24);
			pane.getChildren().add(lineR3);
			lineR4 = new Line(circle4.getLayoutX()+24, circle4.getLayoutY()+24, circle4.getLayoutX() -24, circle4.getLayoutY()-24);
			pane.getChildren().add(lineR4);
		
			line1 = new Line(circle3.getLayoutX(), circle3.getLayoutY() + 10, circle4.getLayoutX(), circle4.getLayoutY() + 10);
			pane.getChildren().add(line1);
			line1.toBack();
			
			
			
		}
	}
	
	/**
	 * method to explain the game to the player with a tutorial
	 */
	@FXML 
	public void initialize () {
		if(text != null)
			text.setText("Pour commencer, appuie sur ce rond");
		if (circle2 != null) {
	        circle2.setDisable(true);
	    }
		if(circle1 != null) {
			  Image image = new Image(getClass().getResourceAsStream("background/arrow.png"));
		        arrow = new ImageView(image);
		        arrow.setLayoutX(circle1.getLayoutX() - 110); // Coordonnée X de l'image
		        arrow.setLayoutY(circle1.getLayoutY() + 31); // Coordonnée Y de l'image
		        arrow.setFitWidth(70);
		        arrow.setFitHeight(70);
		        pane.getChildren().add(arrow);
		}
	}

}

package Application.FrontEnd.Controller;/*

/**
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */

import javafx.fxml.FXML;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.PopupWindow;
import javafx.util.Duration;

/**
 * This class is the controller of the main scene
 * It allows to switch between the different scenes of the application
 * It also allows to quit the application
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */
public class ModeController extends RetourController{

	@FXML private Button libre;
	@FXML private Button aventure;

	@FXML
	public void initialize(){

		Tooltip tooltipL = new Tooltip();
		tooltipL.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);
		tooltipL.setConsumeAutoHidingEvents(false);
		tooltipL.setText("Résout une grille selon la difficulté choisit sans limites d'aides et techniques.");
		libre.setTooltip(tooltipL);

		Tooltip tooltipA = new Tooltip();
		tooltipA.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);
		tooltipA.setConsumeAutoHidingEvents(false);
		tooltipA.setText("Résout les grilles avec un nombre limité d'aides et de techniques.");
		aventure.setTooltip(tooltipA);

	}
	/**
	 * method to switch to the mode libre scene
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception thrown if the file is not found
	 */
	@FXML
	public void switchlibre(ActionEvent event) throws IOException{
		scene("../FXML/jeulibre.fxml",event);
	}
	
	/**
	 * method to switch to the mode aventure scene
	 * @param event : the event that triggers the switch
	 * @throws IOException Exception thrown if the file is not found
	 */
	@FXML
	public void switchaventure(ActionEvent event) throws IOException{
		scene("../FXML/jeuaventure.fxml",event);
	}



}

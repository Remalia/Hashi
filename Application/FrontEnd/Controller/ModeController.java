package Application.FrontEnd.Controller;/*

/**
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */

import javafx.event.Event;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.PopupWindow;

/**
 * Cette classe représente le controller du mode
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */
public class ModeController extends Main{

	@FXML private Button libre;
	@FXML private Button aventure;

	/**
	 * Méthode permettant d'initialiser une popup
	 */
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
	 * Cette méthode permet d'accéder au niveaux du mode jeu libre
	 * @param event : déclenche l'évènement
	 * @throws IOException Une exception est levée si le fichier n'est pas trouvé
	 */
	@FXML
	public void jeulibre(Event event) throws IOException{
		switchToScene("../FXML/jeulibre.fxml",event);
	}

	/**
	 * Cette méthode permet d'accéder au niveaux du mode aventure
	 * @param event : déclenche l'évènement
	 * @throws IOException Une exception est levée si le fichier n'est pas trouvé
	 */
	@FXML
	public void aventure(Event event) throws IOException{
		switchToScene("../FXML/plateau.fxml",event);
	}

	/**
	 * Méthode permettant d'aller dans le menu du plateau
	 * @param event : l'évènement qui déclenche le changement
	 * @throws IOException lève une exception si on ne trouve pas le fichier
	 */
	@FXML
	public void plateau(Event event) throws IOException{
		switchToScene("../FXML/plateau.fxml",event);
	}



}

package Application.FrontEnd.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;
/**
 * Cette classe représente le controller du menu
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */
public class MenuController extends Main {
	private static int niveau;
	private static String difficulte;

	public void lancer_classement(Event event) throws IOException {
		Button button = (Button) event.getSource();
		String id = button.getId();
		niveau = Integer.parseInt(id.substring(id.length() - 1));
		Scene scene = button.getScene();
		// je récupère le fichier FXML correspondant à la difficulté
		this.difficulte = scene.getRoot().getId() ;
		switchToScene("../FXML/classement.fxml", event);
	}


	public int getNiveau() {
		return niveau;
	}
	public String getDifficulte() {
		return difficulte;
	}


	/**
	 * Méthode permettant d'aller dans le menu du tutoriel
	 *
	 * @param event : l'évènement qui déclenche le changement
	 * @throws IOException lève une exception si on ne trouve pas le fichier
	 */
	@FXML
	public void tutoriel_plateau(Event event) throws IOException {
		switchToScene("../FXML/tutoriel_plateau.fxml", event);
	}

	/**
	 * Méthode permettant d'aller dans le menu du plateau
	 * @param event : l'évènement qui déclenche le changement
	 * @throws IOException lève une exception si on ne trouve pas le fichier
	 */
	@FXML
	public void plateau(Event event) throws IOException {
		switchToScene("../FXML/plateau.fxml", event);
	}

	/**
	 * Méthode permettant d'aller dans le menu secondaire
	 *
	 * @param event : l'évènement qui déclenche le changement
	 * @throws IOException lève une exception si on ne trouve pas le fichier
	 */
	@FXML
	public void menu_secondaire(Event event) throws IOException {
		switchToScene("../FXML/menu_s.fxml", event);
	}

	/**
	 * Méthode permettant d'aller dans le menu des paramètres
	 *
	 * @param event : l'évènement qui déclenche le changement
	 * @throws IOException lève une exception si on ne trouve pas le fichier
	 */
	@FXML
	public void parametres(Event event) throws IOException {
		switchToScene("../FXML/paramètres.fxml", event);
	}

		/**
		 * Cette méthode permet d'accéder à la liste des techniques
		 * @param event : Event
		 * @throws IOException Une exception est levée si le fichier n'est pas trouvé
		 */
	@FXML
	public void techniqueInterface(Event event) throws IOException{
		switchToScene("../FXML/technique.fxml",event);
	}

	/**
	 * Cette méthode permet d'accéder au menu tutoriel
	 * @param event : Event
	 * @throws IOException Une exception est levée si le fichier n'est pas trouvé
	 */
	@FXML
	public void tutoriel(Event event) throws IOException {
		switchToScene("../FXML/tutoriel.fxml", event);
	}

	/**
	 * Méthode permettant de quitter le jeu
	 *
	 * @param event déclenche l'évènement
	 */
	@FXML
	public void quitter(Event event) {
		window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.close();
	}

}

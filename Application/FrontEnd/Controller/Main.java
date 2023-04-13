package Application.FrontEnd.Controller;

import java.io.IOException;
import java.util.Objects;
import java.util.Stack;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

/**
 * Cette classe représente le controller du Main
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */
public class Main extends Application {
	Stage window;
	Parent root;
	Scene scene;
	String css = Main.class.getResource("../assets/light_mode.css").toExternalForm(); // CSS par défaut lors de la première connexion
	protected Preferences prefs = Preferences.userNodeForPackage(Main.class);

	/**
	 * Méthode permettant de changer de scène
	 *
	 * @param file  le fichier de la scène à changer
	 * @param event l'évènement qui déclenche le changement
	 * @throws IOException lève une exception si on ne trouve pas le fichier
	 */
	public void switchToScene(String file, Event event) throws IOException {
		css = prefs.get("mon_css", css);
		window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(file)));
		scene = new Scene(root);
		scene.getStylesheets().add(css);
		window.setResizable(false);
		window.setScene(scene);
		window.show();
	}


	@FXML
	public void niveauxDifficiles(Event event) throws IOException {
		switchToScene("../FXML/jeuLibreNiveauDifficile.fxml", event);
	}

	@FXML
	public void niveauxMoyens(Event event) throws IOException {
		switchToScene("../FXML/jeuLibreNiveauMoyen.fxml", event);
	}

	@FXML
	public void niveauxFaciles(Event event) throws IOException {
		switchToScene("../FXML/jeuLibreNiveauFacile.fxml", event);
	}

	/**
	 * Méthode permettant d'aller dans le menu des paramètres
	 *
	 * @param event : l'évènement qui déclenche le changement
	 * @throws IOException lève une exception si on ne trouve pas le fichier
	 */
	@FXML
	public void parametres(MouseEvent event) throws IOException {
		switchToScene("../FXML/paramètres.fxml", event);
	}

	/**
	 * Méthode permettant d'aller dans le menu des crédits
	 *
	 * @param event : l'évènement qui déclenche le changement
	 * @throws IOException lève une exception si on ne trouve pas le fichier
	 */
	@FXML
	public void credits(Event event) throws IOException {
		switchToScene("../FXML/crédits.fxml", event);
	}

	/**
	 * Méthode permettant d'aller dans le menu principal
	 *
	 * @param event : l'évènement qui déclenche le changement
	 * @throws IOException lève une exception si on ne trouve pas le fichier
	 */
	@FXML
	public void menu_principal(Event event) throws IOException {
		switchToScene("../FXML/menu_p.fxml", event);
	}

	/**
	 * Commence l'application Hashi
	 * @param window : la fenêtre de l'application
	 */
	@Override
	public void start(Stage window) {
		css = prefs.get("mon_css", css);
		try {
			this.root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/menu_p.fxml")));
			this.scene = new Scene(root);
			this.scene.getStylesheets().add(css);
			window.setTitle("Hashi");
			window.setScene(scene);
			window.setResizable(false);
			Image iconPrincipale = new Image("Application/FrontEnd/assets/IconHashi.png");
			window.getIcons().add(iconPrincipale);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode main
	 * @param args : arguments du main
	 */
	public static void main(String[] args) {
		launch(args);
	}

}

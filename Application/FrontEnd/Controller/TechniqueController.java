package Application.FrontEnd.Controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;

import java.awt.*;
import java.io.IOException;

/**
 * Cette classe représente  le controller de technique
 */
public class TechniqueController extends MenuController {
    @FXML
    private WebView webView;

    /**
     * Méthode permettant d'initialiser une webView
     */
    @FXML
    public void initialize() {
        webView.getEngine().load("https://www.conceptispuzzles.com/index.aspx?uri=puzzle/hashi/techniques");
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
}

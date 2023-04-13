package Application.FrontEnd.Controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;

import java.io.IOException;

public class TechniqueController extends Main{
    @FXML
    private WebView webView;

    @FXML
    public void initialize() {
        webView.getEngine().load("https://www.conceptispuzzles.com/index.aspx?uri=puzzle/hashi/techniques");
    }

    /**
     * Method to switch to the tutorial scene
     *
     * @param event the event that triggers the switch
     * @throws IOException if the file is not found
     */

    @FXML
    public void tutoriel(Event event) throws IOException {
        switchToScene("../FXML/tutoriel.fxml", event);
    }
}

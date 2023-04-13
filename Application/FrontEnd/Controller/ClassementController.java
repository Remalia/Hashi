package Application.FrontEnd.Controller;
import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Sauvegarde.Classement;

import Application.BackEnd.Sauvegarde.Classement;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class ClassementController extends MenuController {

    @FXML private TextFlow classement;
    @FXML private AnchorPane principal;

    @FXML
    public void initialize() {

        int niv = super.getNiveau(); // Utilisation de "super" pour appeler la méthode de la classe mère
        System.out.println(niv);
        System.out.println(super.getDifficulte());
        Text text = new Text(String.valueOf(niv)); // Utilisation de la variable locale "niv"
        Text text2 = new Text(super.getDifficulte()); // Utilisation de "super" pour appeler la méthode de la classe mère
        classement.getChildren().addAll(text, text2);
        principal.getChildren().add(classement);
    }
}


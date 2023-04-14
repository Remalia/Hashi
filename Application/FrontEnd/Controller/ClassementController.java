package Application.FrontEnd.Controller;
import Application.BackEnd.Grille.Difficulte;
import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Sauvegarde.Classement;

import Application.BackEnd.Sauvegarde.Classement;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class ClassementController extends MenuController {

    @FXML private TextFlow classement;
    @FXML private AnchorPane principal;

    private Difficulte getDifficulteFromString(String dif) {
        switch (dif) {
            case "facile":
                return Difficulte.Facile();
            case "moyen":
                return Difficulte.Moyen();
            case "difficile":
                return Difficulte.Difficile();
            default:
                return null;
        }
    }
    @FXML
    public void initialize() {

        int niv = super.getNiveau(); // Utilisation de "super" pour appeler la méthode de la classe mère
        System.out.println(niv);
        System.out.println(super.getDifficulte());

        try {
            Grille grille = new Grille("Niveau"+niv , getDifficulteFromString(super.getDifficulte()));
            Text text = new Text(grille.getClassement().getClassementToS());
            System.out.println("On recup"+grille.getClassement().getClassementToS());
            text.setFont(new Font(40));
            text.setFill(Color.WHITE);
            classement.getChildren().addAll(text);
            classement.setLayoutX(220);
            classement.setLayoutY(350);
            principal.getChildren().add(classement);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}


package Application.FrontEnd;

import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Grille.Ile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.IOException;

public class InterfaceGrille extends MainSceneController {

    private Timeline timer; // Ajouter une variable timer
    private int tempsEcoule = 0;
    @FXML
    private Pane panneau;

    @FXML
    private Text chronometre;

    @FXML
    private AnchorPane principal;
    public static final Color etatNormal = Color.YELLOW;
    public static final Color etatSelect = Color.GREEN;

    // Dimensions de notre grille et de ces composants
    private int NB_CERCLES;
    private int RAYON;
    private int ESPACE;
    boolean chronometreDemarre = false;

    private CircleHashi[] cerclesHashi;

    // Sauvegardes temporelle
    private Circle premierCercle = null;
    private Circle deuxiemeCercle = null;
    private boolean premierCercleClique = false;
    private Integer indicePremierCercle;
    private Integer indiceSecondCercle;


    @FXML
    public void initialize () throws IOException {

        this.NB_CERCLES = 10;
        this.RAYON = 25;
        this.ESPACE = RAYON * 3;

        // Calcul de la taille du panneau pour afficher correctement la grille
        double panneauWidth = NB_CERCLES * ESPACE + RAYON;
        double panneauHeight = NB_CERCLES * ESPACE + RAYON;

        this.cerclesHashi = new CircleHashi[this.NB_CERCLES * this.NB_CERCLES];

        this.timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tempsEcoule++;
            int minutes = tempsEcoule / 60;
            int secondes = tempsEcoule % 60;
            chronometre.setText(String.format("%02d:%02d", minutes, secondes));
        }));
        timer.setCycleCount(Timeline.INDEFINITE);

        // Définition de la taille du panneau
        panneau.setPrefWidth(panneauWidth);
        panneau.setPrefHeight(panneauHeight);

        // Centrage du panneau dans la fenêtre
        principal.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newX = (newVal.doubleValue() - panneauWidth) / 2;
            panneau.setLayoutX(newX);
        });
        principal.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newY = (newVal.doubleValue() - panneauHeight) / 2;
            panneau.setLayoutY(newY);
        });

        Grille grille = new Grille("NiveauTest");
        grille.getGrilleFromYAML(grille.getFileNiveau());
        grille.saveGrilleToYAML();
        System.out.println(grille.getListIle());
        for(Ile ile : grille.getListIle()){
            double coordX = ESPACE * (ile.getAbs()+1);
            double coordY = ESPACE * (ile.getOrd()+1);
            CircleHashi cercle = new CircleHashi(ile,coordX,coordY , RAYON, etatNormal); // Ici, les coordonnées des cercles ne sont pas initialisé
            cercle.setStrokeWidth(6.0);
            cercle.setStroke(Color.ORANGE);
            cerclesHashi[ile.getAbs() * NB_CERCLES + ile.getOrd()] = cercle;
            cercle.setOnMouseClicked(this::interactionCouleur);
            panneau.getChildren().add(cercle);
            panneau.getChildren().add(cerclesHashi[ile.getAbs() * NB_CERCLES + ile.getOrd()].text);
        }
    }

    /**
     * Fonction qui donne le CircleHashi à l'aide de son cercle
     * @param c cercle appartenant au CircleHashi
     */
    private CircleHashi getCircleHashi(Circle c){
        for(CircleHashi cercleHashi : cerclesHashi){
            if(cercleHashi == c){
                return cercleHashi;
            }
        }
        return null;
    }

    // Méthode appelée lorsqu'un cercle est cliqué. Elle change la couleur du cercle + gestion des ponts.
    private void interactionCouleur(MouseEvent event) {

        if (!chronometreDemarre) {
            chronometreDemarre = true;
            timer.play();
        }
        Circle cercle = (Circle) event.getSource();

        // Cas de réhinitialisation du clique de cercle
        if(cercle == premierCercle) {
            premierCercle.setFill(Color.YELLOW);
            premierCercle = null;
            deuxiemeCercle = null;
            premierCercleClique = false;
            indicePremierCercle = null;
            indiceSecondCercle = null;
            System.out.println("Réhinitialisé");
        }

        // Cas ou 2 cercles ont été cliqué
        else if (premierCercleClique && cercle != premierCercle) {
            deuxiemeCercle = cercle;
            double c1x = premierCercle.getCenterX();
            double c2x = deuxiemeCercle.getCenterX();
            double c1y = premierCercle.getCenterY();
            double c2y = deuxiemeCercle.getCenterY();

            if(c1x == c2x || c1y == c2y) {
                premierCercle.setFill(Color.YELLOW);
                premierCercleClique = false;
                for (int i = 0; i < cerclesHashi.length; i++) {
                    if (cerclesHashi[i] != null) {
                        if ((cerclesHashi[i].getCenterX() == deuxiemeCercle.getCenterX()) && (cerclesHashi[i].getCenterY() == deuxiemeCercle.getCenterY())) {
                            indiceSecondCercle = i;
                            //System.out.println(indicePremierCercle +"-"+ indiceSecondCercle);
                            break;
                        }
                    }
                }
                dessinerLigne(premierCercle, deuxiemeCercle, panneau);
                premierCercle.setFill(Color.YELLOW);
                premierCercle = null;
                deuxiemeCercle = null;
                premierCercleClique = false;
                indicePremierCercle = null;
                indiceSecondCercle = null;
                System.out.println("New Etats");
            }
            else {
                premierCercle.setFill(Color.YELLOW);
                premierCercle = null;
                deuxiemeCercle = null;
                premierCercleClique = false;
                indicePremierCercle = null;
                indiceSecondCercle = null;
                System.out.println("Combinaison interdit");
            }

        }

        // Cas ou seulement 1 cercles est cliqué
        else {
            premierCercle = cercle;
            premierCercle.setFill(Color.GREEN);
            premierCercleClique = true;
            for (int i = 0; i < cerclesHashi.length; i++) {
                if (cerclesHashi[i] != null) {
                    if((cerclesHashi[i].getCenterX() == premierCercle.getCenterX()) && (cerclesHashi[i].getCenterY() == premierCercle.getCenterY())) {
                        indicePremierCercle = i;
                        break;
                    }
                }
            }
        }
    }

    // Dessiner la ligne entre les cercles

    private void dessinerLigne(Circle cercle1, Circle cercle2, Pane panneau) {
        Line ligne1 = new Line(cercle1.getCenterX(), cercle1.getCenterY(), cercle2.getCenterX(), cercle2.getCenterY());
        Line ligne2 = new Line(cercle1.getCenterX()+5, cercle1.getCenterY()+5, cercle2.getCenterX()+5, cercle2.getCenterY()+5);
        Line ligne3 = new Line(cercle1.getCenterX()-5, cercle1.getCenterY()-5, cercle2.getCenterX()-5, cercle2.getCenterY()-5);
        //System.out.println(ligne1);

        if(!cerclesHashi[indicePremierCercle].ligneEstDansListe(ligne2) && !cerclesHashi[indicePremierCercle].ligneEstDansListe(ligne3)) {

            if((!cerclesHashi[indicePremierCercle].ligneEstDansListe(ligne1))) {
                cerclesHashi[indicePremierCercle].ajouterLigne(ligne1);
                cerclesHashi[indiceSecondCercle].ajouterLigneInverse(ligne1);
                panneau.getChildren().removeAll(cercle1,cercle2,cerclesHashi[indicePremierCercle].text,cerclesHashi[indiceSecondCercle].text);
                panneau.getChildren().addAll(cerclesHashi[indicePremierCercle].retournerLigne(ligne1));
                panneau.getChildren().addAll(cercle1,cercle2,cerclesHashi[indicePremierCercle].text,cerclesHashi[indiceSecondCercle].text);
            }

            else {
                cerclesHashi[indicePremierCercle].ajouterLigne(ligne2);
                cerclesHashi[indicePremierCercle].ajouterLigne(ligne3);
                cerclesHashi[indiceSecondCercle].ajouterLigneInverse(ligne2);
                cerclesHashi[indiceSecondCercle].ajouterLigneInverse(ligne3);
                panneau.getChildren().removeAll(cercle1,cercle2, cerclesHashi[indicePremierCercle].retournerLigne(ligne1), cerclesHashi[indiceSecondCercle].retournerLigne(ligne1), cerclesHashi[indicePremierCercle].retournerLigneInverse(ligne1),cerclesHashi[indiceSecondCercle].retournerLigneInverse(ligne1),cerclesHashi[indicePremierCercle].text,cerclesHashi[indiceSecondCercle].text);
                cerclesHashi[indicePremierCercle].supprimerLigne(ligne1);
                cerclesHashi[indiceSecondCercle].supprimerLigne(ligne1);
                cerclesHashi[indicePremierCercle].supprimerLigneInverse(ligne1);
                cerclesHashi[indiceSecondCercle].supprimerLigneInverse(ligne1);
                panneau.getChildren().addAll(cerclesHashi[indicePremierCercle].retournerLigne(ligne2), cerclesHashi[indicePremierCercle].retournerLigne(ligne3));
                panneau.getChildren().addAll(cercle1,cercle2,cerclesHashi[indicePremierCercle].text,cerclesHashi[indiceSecondCercle].text);
            }
        }
        else {
            panneau.getChildren().removeAll(cerclesHashi[indicePremierCercle].retournerLigne(ligne2), cerclesHashi[indiceSecondCercle].retournerLigne(ligne2), cerclesHashi[indicePremierCercle].retournerLigneInverse(ligne2),cerclesHashi[indiceSecondCercle].retournerLigneInverse(ligne2),cerclesHashi[indicePremierCercle].retournerLigne(ligne3), cerclesHashi[indiceSecondCercle].retournerLigne(ligne3), cerclesHashi[indicePremierCercle].retournerLigneInverse(ligne3),cerclesHashi[indiceSecondCercle].retournerLigneInverse(ligne3));
            cerclesHashi[indicePremierCercle].supprimerLigne(ligne2);
            cerclesHashi[indicePremierCercle].supprimerLigne(ligne3);
            cerclesHashi[indiceSecondCercle].supprimerLigne(ligne2);
            cerclesHashi[indiceSecondCercle].supprimerLigne(ligne3);
            cerclesHashi[indicePremierCercle].supprimerLigneInverse(ligne2);
            cerclesHashi[indicePremierCercle].supprimerLigneInverse(ligne3);
            cerclesHashi[indiceSecondCercle].supprimerLigneInverse(ligne2);
            cerclesHashi[indiceSecondCercle].supprimerLigneInverse(ligne3);
        }
    }

}
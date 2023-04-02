package Application.FrontEnd.Controller;

/**
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */

import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Grille.Ile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import java.io.IOException;

/**
 * This class is the grid of the game
 */
public class InterfaceGrille extends MainSceneController {

    
    private Timeline timer;
    private int tempsEcoule = 0;

    @FXML
    private Pane panneau;

    @FXML
    private Text chronometre;

    @FXML
    private AnchorPane principal;
    public static final Color etatNormal = Color.YELLOW;
    public static final Color etatSelect = Color.GREEN;

    // 
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

    /**
     * Function initializing the grid
     * @throws IOException if the file is not found
     */
    @FXML
    public void initialize () throws IOException {

        this.NB_CERCLES = 10;
        this.RAYON = 25;
        this.ESPACE = RAYON * 3;
        this.cerclesHashi = new CircleHashi[this.NB_CERCLES * this.NB_CERCLES];

        // Timer
        this.timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tempsEcoule++;
            int minutes = tempsEcoule / 60;
            int secondes = tempsEcoule % 60;
            chronometre.setText(String.format("%02d:%02d", minutes, secondes));
        }));
        timer.setCycleCount(Timeline.INDEFINITE);

        // Initialize the pane
        double panneauWidth = NB_CERCLES * ESPACE + RAYON;
        double panneauHeight = NB_CERCLES * ESPACE + RAYON;

        panneau.setPrefWidth(panneauWidth);
        panneau.setPrefHeight(panneauHeight);

        principal.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newX = (newVal.doubleValue() - panneauWidth) / 2;
            panneau.setLayoutX(newX);
        });
        principal.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newY = (newVal.doubleValue() - panneauHeight) / 2;
            panneau.setLayoutY(newY);
        });

        // Initialize the grid
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
     * Function to get the circle
     * @param c the circle
     * @return the circle
     */
    private CircleHashi getCircleHashi(Circle c){
        for(CircleHashi cercleHashi : cerclesHashi){
            if(cercleHashi == c){
                return cercleHashi;
            }
        }
        return null;
    }

    /**
     * Function to change the color of the circle and the bridge
     * @param event the event
     */
    private void interactionCouleur(MouseEvent event) {

        if (!chronometreDemarre) {
            chronometreDemarre = true;
            timer.play();
        }
        Circle cercle = (Circle) event.getSource();

        // Case of renitialisation of the circle
        if(cercle == premierCercle) {
            premierCercle.setFill(Color.YELLOW);
            premierCercle = null;
            deuxiemeCercle = null;
            premierCercleClique = false;
            indicePremierCercle = null;
            indiceSecondCercle = null;
            System.out.println("Réhinitialisé");
        }

        // Case where 2 circles are clicked
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

        // Case where 1 circle is clicked
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

    // Design the bridge
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
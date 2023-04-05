package Application.FrontEnd.Controller;

import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Grille.Ile;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */
public class InterfaceGrille extends MainSceneController {

    private Timeline timer=null; // Ajouter une variable timer
    private int tempsEcoule = 0;
    @FXML
    private Pane panneau;

    @FXML
    private Text chronometre;

    @FXML
    private AnchorPane principal;

    @FXML private ImageView switch_timer;

    private Image newImage;

    public static Color etatNormal = Color.YELLOW;
    public static Color etatSelect = Color.GREEN;

    private int NB_CERCLES;
    private int RAYON;
    private int ESPACE;

    private CircleHashi[] cerclesHashi;

    private CircleHashi premierCercle = null;
    private CircleHashi deuxiemeCercle = null;
    private boolean premierCercleClique = false;
    private Integer indicePremierCercle;
    private Integer indiceSecondCercle;

    private Grille grille;


    /**
     * method to switch to the mode
     * @param event : the event that triggers the switch
     */
    @FXML
    public void retour_mode(MouseEvent event) throws IOException {
        img_scene("../FXML/jeuaventure.fxml",event);
    }

    /**
     * method to stop the timer
     * @param event : the event that triggers the switch
     */
    @FXML
    public void stop_timer(ActionEvent event) throws IOException {
        Image newImage;
        if (timer.getStatus() == Animation.Status.PAUSED || !(timer.getStatus() == Animation.Status.RUNNING)) {
            newImage = new Image("Application/FrontEnd/background/bouton-pause.png");
            switch_timer.setImage(newImage);
            timer.play();
        }else{
            newImage = new Image("Application/FrontEnd/background/bouton-jouer.png");
            switch_timer.setImage(newImage);
            timer.pause();
        }
    }


    /**
     * Function initializing the grid
     * @throws IOException if the file is not found
     */
    @FXML
    public void initialize() throws IOException {
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
        this.grille = new Grille(grille);
        System.out.println(grille);
        for(Ile ile : grille.getListIle()){
            double coordX = ESPACE * (ile.getAbs()+1);
            double coordY = ESPACE * (ile.getOrd()+1);
            CircleHashi cercle = new CircleHashi(ile,coordX,coordY , RAYON, etatNormal); // Ici, les coordonnées des cercles ne sont pas initialisé
            cercle.setStrokeWidth(6.0);
            cercle.setStroke(Color.ORANGE);
            cerclesHashi[ile.getAbs() * NB_CERCLES + ile.getOrd()] = cercle;
            cercle.setOnMouseClicked(this::interactionCouleur);
            panneau.getChildren().add(cercle);
            panneau.getChildren().add(cerclesHashi[ile.getAbs() * NB_CERCLES + ile.getOrd()].getText());
        }
    }


    /**
     * Function to change the color of the circle and the bridge
     * @param event the event
     */
    private void interactionCouleur(MouseEvent event) {
        CircleHashi cercle = (CircleHashi) event.getSource();

        if (!(timer.getStatus() == Animation.Status.RUNNING)) {
            newImage = new Image("Application/FrontEnd/background/bouton-pause.png");
            switch_timer.setImage(newImage);
            timer.play();
        }

        // Case of renitialisation of the circle
        if(cercle == premierCercle) {
            reinitialiserCercles();
        }

        // Case where 2 circles are clicked
        else if (premierCercleClique && cercle != premierCercle) {
            deuxiemeCercle = cercle;
            if (memeLigneOuColonne(premierCercle, deuxiemeCercle)) {
                indiceSecondCercle = trouverIndiceCercle(deuxiemeCercle);
                dessinerLigne(premierCercle, deuxiemeCercle, panneau);
                reinitialiserCercles();
            } else {
                reinitialiserCercles();
            }
        }

        // Case where 1 circle is clicked
        else {
            premierCercle = cercle;
            premierCercle.setFill(Color.GREEN);
            premierCercleClique = true;
            indicePremierCercle = trouverIndiceCercle(premierCercle);
        }
    }

    /**
     * This method returns true if the two circles are on the same line or the same column
     * @param cercle1 first circle to compare
     * @param cercle2 second circle to compare
     * @return true if the two circles are on the same line or the same column
     */
    private boolean memeLigneOuColonne(CircleHashi cercle1, CircleHashi cercle2) {
        double c1x = cercle1.getCenterX();
        double c2x = cercle2.getCenterX();
        double c1y = cercle1.getCenterY();
        double c2y = cercle2.getCenterY();

        return (c1x == c2x || c1y == c2y);
    }

    /**
     * Méthod to reinitialize the circles
     */
    private void reinitialiserCercles(){
        premierCercle.setFill(Color.YELLOW);
        premierCercle = null;
        deuxiemeCercle = null;
        premierCercleClique = false;
        indicePremierCercle = null;
        indiceSecondCercle = null;
    }

    /**
     * Méthod who returns the index of the circle in the array
     * @param cercle the circle to find
     * @return the index of the circle in the array
     */
    private int trouverIndiceCercle(CircleHashi cercle) {
        for (int i = 0; i < cerclesHashi.length; i++) {
            if (cerclesHashi[i] != null) {
                if((cerclesHashi[i].getCenterX() == cercle.getCenterX()) && (cerclesHashi[i].getCenterY() == cercle.getCenterY())) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Méthod to draw the bridge between the two circles
     * @param cercle1 the first circle
     * @param cercle2 the second circle
     * @param panneau the pane where the bridge will be drawn
     */
    private void dessinerLigne(Circle cercle1, Circle cercle2, Pane panneau) {
        if(!this.grille.estIncrementable(cerclesHashi[indicePremierCercle].getIle(), cerclesHashi[indiceSecondCercle].getIle())){
            System.out.println("Erreur : pont impossible");
            return;
        }
        Line ligne1 = new Line(cercle1.getCenterX(), cercle1.getCenterY(), cercle2.getCenterX(), cercle2.getCenterY());
        Line ligne2 = new Line(cercle1.getCenterX()+5, cercle1.getCenterY()+5, cercle2.getCenterX()+5, cercle2.getCenterY()+5);
        Line ligne3 = new Line(cercle1.getCenterX()-5, cercle1.getCenterY()-5, cercle2.getCenterX()-5, cercle2.getCenterY()-5);
        //System.out.println(ligne1);

        if(!cerclesHashi[indicePremierCercle].ligneEstDansListe(ligne2) && !cerclesHashi[indicePremierCercle].ligneEstDansListe(ligne3)) {

            if((!cerclesHashi[indicePremierCercle].ligneEstDansListe(ligne1))) {
                cerclesHashi[indicePremierCercle].ajouterLigne(ligne1);
                cerclesHashi[indiceSecondCercle].ajouterLigneInverse(ligne1);
                panneau.getChildren().removeAll(cercle1,cercle2,cerclesHashi[indicePremierCercle].getText(),cerclesHashi[indiceSecondCercle].getText());
                panneau.getChildren().addAll(cerclesHashi[indicePremierCercle].retournerLigne(ligne1));
                panneau.getChildren().addAll(cercle1,cercle2,cerclesHashi[indicePremierCercle].getText(),cerclesHashi[indiceSecondCercle].getText());
                grille.ajouterPont(cerclesHashi[indicePremierCercle].getIle(), cerclesHashi[indiceSecondCercle].getIle(),1);
            }

            else {
                cerclesHashi[indicePremierCercle].ajouterLigne(ligne2);
                cerclesHashi[indicePremierCercle].ajouterLigne(ligne3);
                cerclesHashi[indiceSecondCercle].ajouterLigneInverse(ligne2);
                cerclesHashi[indiceSecondCercle].ajouterLigneInverse(ligne3);
                panneau.getChildren().removeAll(cercle1,cercle2, cerclesHashi[indicePremierCercle].retournerLigne(ligne1), cerclesHashi[indiceSecondCercle].retournerLigne(ligne1), cerclesHashi[indicePremierCercle].retournerLigneInverse(ligne1),cerclesHashi[indiceSecondCercle].retournerLigneInverse(ligne1),cerclesHashi[indicePremierCercle].getText(),cerclesHashi[indiceSecondCercle].getText());
                cerclesHashi[indicePremierCercle].supprimerLigne(ligne1);
                cerclesHashi[indiceSecondCercle].supprimerLigne(ligne1);
                cerclesHashi[indicePremierCercle].supprimerLigneInverse(ligne1);
                cerclesHashi[indiceSecondCercle].supprimerLigneInverse(ligne1);
                panneau.getChildren().addAll(cerclesHashi[indicePremierCercle].retournerLigne(ligne2), cerclesHashi[indicePremierCercle].retournerLigne(ligne3));
                panneau.getChildren().addAll(cercle1,cercle2,cerclesHashi[indicePremierCercle].getText(),cerclesHashi[indiceSecondCercle].getText());
                grille.ajouterPont(cerclesHashi[indicePremierCercle].getIle(), cerclesHashi[indiceSecondCercle].getIle(),2);
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
            grille.ajouterPont(cerclesHashi[indicePremierCercle].getIle(), cerclesHashi[indiceSecondCercle].getIle(),0);
        }

        System.out.println(this.grille);
    }

}
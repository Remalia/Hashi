package Application.FrontEnd.Controller;

import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Grille.Ile;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import java.util.Collection;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
    private double ESPACE;

    private CircleHashi[] cerclesHashi;

    private CircleHashi premierCercle = null;
    private CircleHashi deuxiemeCercle = null;
    private boolean premierCercleClique = false;
    private Integer indicePremierCercle;
    private Integer indiceSecondCercle;

    private boolean modehypothese = false;

    private Grille grille;


    /**
     * Cette méthode permet de passer à la scène aventure
     * @param event : l'évènement qui déclenche le passage à la scène aventure
     * @throws IOException Exception thrown if the file is not found
     */
    @FXML
    public void retour_mode(MouseEvent event) throws IOException {
        img_scene("../FXML/jeulibre.fxml",event);
    }

    /**
     * method to switch to the mode libre/aventure
     * @param event : the event that triggers the switch
     */
    @FXML
    public void hypothese(ActionEvent event) throws IOException {
        if(modehypothese == false){
            modehypothese = true;
            System.out.println("Mode hypothese activé");
        }
        else{
            modehypothese = false;
            System.out.println("Mode hypothese désactivé");
        }
    }

    /**
     * Cette méthode permet de changer l'état d'un cercle
     * @param cercles : les cercles de la grille
     * @param colorFill : la couleur de remplissage du cercle
     * @param colorBord : la couleur du bord du cercle
     * @param text : la couleur du texte
     * @param disable : si le cercle est désactivé ou non
     */
    public void changement_pause(CircleHashi[] cercles, Color colorFill, Color colorBord, Color text, Boolean disable){
            for(CircleHashi c : cercles)
                if(c != null) {
                    c.setFill(colorFill);
                    c.getText().setFill(text);
                    c.setStroke(colorBord);
                    c.setDisable(disable);
                }
    }

    /**
     * Cette méthode permet de mettre en pause le chronometre
     * @param event : l'évènement qui déclenche la pause
     */
    @FXML
    public void stop_timer(ActionEvent event) throws IOException {
        Image newImage;
        if (timer.getStatus() == Animation.Status.PAUSED || !(timer.getStatus() == Animation.Status.RUNNING)) {
            newImage = new Image("Application/FrontEnd/assets/bouton-pause.png");
            switch_timer.setImage(newImage);
            changement_pause(this.cerclesHashi, Color.YELLOW, Color.ORANGE, Color.BLACK, false);
            timer.play();
        }else{
            newImage = new Image("Application/FrontEnd/assets/bouton-jouer.png");
            switch_timer.setImage(newImage);
            changement_pause(this.cerclesHashi, Color.GREY, Color.GREY, Color.WHITE, true);
            timer.pause();
        }
    }


    /**
     * Cette fonction permet d'initialiser la grille
     * @throws IOException Cette exception est levée si le fichier n'est pas trouvé
     */
    @FXML
    public void initialize() throws IOException {
        this.NB_CERCLES = 10;
        this.RAYON = 20;
        this.ESPACE = RAYON * 2.5;

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
     * Cette méthode permet de gérer les interactions avec les cercles
     * @param event : l'évènement qui déclenche l'interaction
     */
    private void interactionCouleur(MouseEvent event) {
        CircleHashi cercle = (CircleHashi) event.getSource();

        if (!(timer.getStatus() == Animation.Status.RUNNING)) {
            newImage = new Image("Application/FrontEnd/assets/bouton-pause.png");
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
     * Cette méthode permet de vérifier si deux cercles sont sur la même ligne ou la même colonne
     * @param cercle1 : le premier cercle
     * @param cercle2 : le deuxième cercle
     * @return true si les deux cercles sont sur la même ligne ou la même colonne, false sinon
     */
    private boolean memeLigneOuColonne(CircleHashi cercle1, CircleHashi cercle2) {
        double c1x = cercle1.getCenterX();
        double c2x = cercle2.getCenterX();
        double c1y = cercle1.getCenterY();
        double c2y = cercle2.getCenterY();

        return (c1x == c2x || c1y == c2y);
    }

    /**
     * Cette méthode permet de réinitialiser les cercles
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
     * Cette méthode permet de trouver l'indice d'un cercle dans le tableau de cercles
     * @param cercle : le cercle dont on cherche l'indice
     * @return l'indice du cercle dans le tableau de cercles
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
     * Cette méthode permet de dessiner une ligne entre deux cercles
     * @param cercle1 : le premier cercle
     * @param cercle2 : le deuxième cercle
     * @param panneau : la grille
     */
    private void dessinerLigne(Circle cercle1, Circle cercle2, Pane panneau) {
        if(!this.grille.estIncrementable(cerclesHashi[indicePremierCercle].getIle(), cerclesHashi[indiceSecondCercle].getIle())){
            System.out.println("Erreur : pont impossible");
            return;
        }
        Line ligne1 = new Line(cercle1.getCenterX(), cercle1.getCenterY(), cercle2.getCenterX(), cercle2.getCenterY());
        Line ligne2 = new Line(cercle1.getCenterX()+5, cercle1.getCenterY()+5, cercle2.getCenterX()+5, cercle2.getCenterY()+5);
        Line ligne3 = new Line(cercle1.getCenterX()-5, cercle1.getCenterY()-5, cercle2.getCenterX()-5, cercle2.getCenterY()-5);
        ligne1.setStrokeWidth(3);
        ligne2.setStrokeWidth(3);
        ligne3.setStrokeWidth(3);
        ligne1.setStroke(Color.GREY);
        ligne2.setStroke(Color.GREY);
        ligne3.setStroke(Color.GREY);
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
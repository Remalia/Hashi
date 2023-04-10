package Application.FrontEnd.Controller;

import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Grille.Ile;
import Application.BackEnd.Grille.Plateau;
import Application.FrontEnd.Controller.Plateau.CircleHashi;
import Application.FrontEnd.Controller.Plateau.GrilleF;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;

/**
 * This class is the grid of the game
 * @author Remi Ilango Allan Jarrier Alex Choux Anna Beranger Arthur Boullier Alexis Guimbert Mohamed Al Aftan Thibaut Duchesne
 * @version 1.0
 * @since 2023-04-02
 */
public class InterfacePlateau extends Main {

    private GrilleF grille;
    private Timeline timer=null; // Ajouter une variable timer
    private int tempsEcoule = 0;
    @FXML
    private Pane panneau;

    @FXML
    private Label chronometre;

    @FXML
    private AnchorPane principal;

    @FXML private ImageView switch_timer;

    private Image newImage;

    public static Color etatNormal = Color.YELLOW;
    public static Color etatSelect = Color.GREEN;


    private boolean modeHypothese = false;

    private Plateau plateau;

    private boolean choixHypothese = true;

    /**
     * Cette méthode permet de vérifier si la grille est correcte en appuyant sur le bouton
     * @param event
     */
    @FXML
    public void checkBouton(ActionEvent event){
        ButtonType ouiButton = new ButtonType("Oui");
        ButtonType nonButton = new ButtonType("Non");

        if (plateau.getGrille().grilleCorrecte()){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Hashi");
            alert.setHeaderText("La grille est correcte !");
            Optional<ButtonType> result = alert.showAndWait();

        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Hashi");
            alert.setHeaderText("Voulez-vous retourner à l’état précédant votre première erreur ?");
            alert.getButtonTypes().setAll(ouiButton, nonButton);
            Optional<ButtonType> result = alert.showAndWait();
        }
    }


    /**
     * Cette méthode permet revenir à l'état précédent
     * @param event : Event
     */
    @FXML
    public void undoBouton(ActionEvent event){
            this.plateau.undo();
    }

    /**
     * Cette méthode permet revenir à l'état suivant
     * @param event : Event
     */
    @FXML
    public void redoBouton(ActionEvent event){
        this.plateau.redo();
    }


    /**
     * Cette méthode permet de passer à la scène libre
     * @param event : l'évènement qui déclenche le passage à la scène aventure
     * @throws IOException Exception thrown if the file is not found
     */
    @FXML
    public void jeulibre(Event event) throws IOException {
        switchToScene("../FXML/jeulibre.fxml",event);
    }

    /**
     * method to switch to the mode libre/aventure
     * @param event : the event that triggers the switch
     */
    @FXML
    public void hypothese(ActionEvent event){
        if(!modeHypothese){
            modeHypothese = true;
            System.out.println("Mode hypothese activé");
            avantHypothese(this.grille.getCerclesHashi());
            plateau.getGrille().avantHypothese();
        }
        else{
            modeHypothese = false;
            System.out.println("Mode hypothese désactivé");
            this.choixHypothese = popupHypothese();
            if(choixHypothese == false){
                apresHypothese(this.grille.getCerclesHashi());
                this.plateau.getGrille().apresHypothese();
                reinitialiserHypothse(this.panneau, this.grille.getCerclesHashi());
            }
            else{
                changerCouleurLignes(panneau, Color.RED);
            }
            choixHypothese = true;
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
    public void stop_timer(ActionEvent event){
        Image newImage;
        if (timer.getStatus() == Animation.Status.PAUSED || !(timer.getStatus() == Animation.Status.RUNNING)) {
            newImage = new Image("Application/FrontEnd/assets/bouton-pause.png");
            switch_timer.setImage(newImage);
            changement_pause(this.grille.getCerclesHashi(), Color.YELLOW, Color.ORANGE, Color.BLACK, false);
            timer.play();
        }else{
            newImage = new Image("Application/FrontEnd/assets/bouton-jouer.png");
            switch_timer.setImage(newImage);
            changement_pause(this.grille.getCerclesHashi(), Color.GREY, Color.GREY, Color.WHITE, true);
            timer.pause();
        }
    }


    /**
     * Cette fonction permet d'initialiser la grille Hashi
     * @throws IOException Cette exception est levée si le fichier n'est pas trouvé
     */
    @FXML
    public void initialize() throws IOException {
        this.grille = new GrilleF();
        this.grille.setNB_CERCLES(10);
        this.grille.setRAYON(20);
        this.grille.setESPACE(this.grille.getRAYON()*2.5);

        // Calcul de la taille du panneau pour afficher correctement la grille
        double panneauWidth = this.grille.getNB_CERCLES() * this.grille.getESPACE() + this.grille.getRAYON();
        double panneauHeight = this.grille.getNB_CERCLES() * this.grille.getESPACE() + this.grille.getRAYON();

        this.grille.setCirclesHashi(new CircleHashi[this.grille.getNB_CERCLES() * this.grille.getNB_CERCLES()]);


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
        plateau = new Plateau(new Grille("NiveauxMoyen/Niveau1"));
        plateau.getPlateauFromYAML(false);
        System.out.println(plateau.getGrille());
        for(Ile ile : plateau.getGrille().getListIle()){
            double coordX = this.grille.getESPACE() * (ile.getAbs()+1);
            double coordY = this.grille.getESPACE() * (ile.getOrd()+1);
            CircleHashi cercle = new CircleHashi(ile,coordX,coordY , this.grille.getRAYON(), etatNormal); // Ici, les coordonnées des cercles ne sont pas initialisé
            cercle.setStrokeWidth(6.0);
            cercle.setStroke(Color.ORANGE);
            this.grille.setCircleHashi(ile.getAbs() * this.grille.getNB_CERCLES() + ile.getOrd(),cercle);
            cercle.setOnMouseClicked(this::interactionCouleur);
            panneau.getChildren().add(cercle);
            panneau.getChildren().add(this.grille.getVal_cercles(ile.getAbs() * this.grille.getNB_CERCLES() + ile.getOrd()).getText());
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
        if(cercle == this.grille.getPremierCercle()) {
            this.grille.reinitialiserCercles();
        }

        // Case where 2 circles are clicked
        else if (this.grille.isPremierCercleClique() && cercle != this.grille.getPremierCercle()) {
            this.grille.setDeuxiemeCercle(cercle);
            if (this.grille.memeLigneOuColonne(this.grille.getPremierCercle(), this.grille.getDeuxiemeCercle())) {
                this.grille.setIndiceSecondCercle(this.grille.trouverIndiceCercle(this.grille.getDeuxiemeCercle()));
                dessinerLigne(this.grille.getPremierCercle(), this.grille.getDeuxiemeCercle(), panneau);
                this.grille.reinitialiserCercles();
            } else {
                this.grille.reinitialiserCercles();
            }
        }

        // Case where 1 circle is clicked
        else {
            this.grille.setPremierCercle(cercle);
            this.grille.getPremierCercle().setFill(Color.GREEN);
            this.grille.setPremierCercleClique(true);
            this.grille.setIndicePremierCercle(this.grille.trouverIndiceCercle(this.grille.getPremierCercle()));
        }
    }



    /**
     * Cette méthode permet de dessiner une ligne entre deux cercles
     * @param cercle1 : le premier cercle
     * @param cercle2 : le deuxième cercle
     * @param panneau : la grille
     */
    private void dessinerLigne(Circle cercle1, Circle cercle2, Pane panneau) {
        if(this.plateau.getGrille().collisionCreationPont(plateau.getGrille().chercherPont(this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).getIle(), this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).getIle()))){
            System.out.println("Erreur : pont impossible");
            return;
        }
        Line ligne1 = new Line(cercle1.getCenterX(), cercle1.getCenterY(), cercle2.getCenterX(), cercle2.getCenterY());
        Line ligne2 = new Line(cercle1.getCenterX()+5, cercle1.getCenterY()+5, cercle2.getCenterX()+5, cercle2.getCenterY()+5);
        Line ligne3 = new Line(cercle1.getCenterX()-5, cercle1.getCenterY()-5, cercle2.getCenterX()-5, cercle2.getCenterY()-5);
        ligne1.setStrokeWidth(3);
        ligne2.setStrokeWidth(3);
        ligne3.setStrokeWidth(3);
        if(this.modeHypothese){
            ligne1.setStroke(Color.GREEN);
            ligne2.setStroke(Color.GREEN);
            ligne3.setStroke(Color.GREEN);
        }
        else{
            ligne1.setStroke(Color.RED);
            ligne2.setStroke(Color.RED);
            ligne3.setStroke(Color.RED);
        }

        //System.out.println(ligne1);
        if(plateau.incrementerPont(this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).getIle(), this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).getIle())){
            if(!this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).ligneEstDansListe(ligne2) && !this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).ligneEstDansListe(ligne3)) {
                if((!this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).ligneEstDansListe(ligne1))) {
                    this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).ajouterLigne(ligne1);
                    this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).ajouterLigneInverse(ligne1);
                    panneau.getChildren().removeAll(cercle1,cercle2,this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).getText(),this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).getText());
                    panneau.getChildren().addAll(this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).retournerLigne(ligne1));
                    panneau.getChildren().addAll(cercle1,cercle2,this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).getText(),this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).getText());
                }else{
                    this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).ajouterLigne(ligne2);
                    this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).ajouterLigne(ligne3);
                    this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).ajouterLigneInverse(ligne2);
                    this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).ajouterLigneInverse(ligne3);
                    panneau.getChildren().removeAll(cercle1,cercle2, this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).retournerLigne(ligne1), this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).retournerLigne(ligne1), this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).retournerLigneInverse(ligne1),this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).retournerLigneInverse(ligne1),this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).getText(),this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).getText());
                    this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).supprimerLigne(ligne1);
                    this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).supprimerLigne(ligne1);
                    this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).supprimerLigneInverse(ligne1);
                    this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).supprimerLigneInverse(ligne1);
                    panneau.getChildren().addAll(this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).retournerLigne(ligne2), this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).retournerLigne(ligne3));
                    panneau.getChildren().addAll(cercle1,cercle2,this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).getText(),this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).getText());
                }
            }else{
                panneau.getChildren().removeAll(this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).retournerLigne(ligne2), this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).retournerLigne(ligne2), this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).retournerLigneInverse(ligne2),this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).retournerLigneInverse(ligne2),this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).retournerLigne(ligne3), this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).retournerLigne(ligne3), this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).retournerLigneInverse(ligne3),this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).retournerLigneInverse(ligne3));
                this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).supprimerLigne(ligne2);
                this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).supprimerLigne(ligne3);
                this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).supprimerLigne(ligne2);
                this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).supprimerLigne(ligne3);
                this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).supprimerLigneInverse(ligne2);
                this.grille.getVal_cercles(this.grille.getIndicePremierCercle()).supprimerLigneInverse(ligne3);
                this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).supprimerLigneInverse(ligne2);
                this.grille.getVal_cercles(this.grille.getIndiceSecondCercle()).supprimerLigneInverse(ligne3);
            }
        }
        if(plateau.getGrille().grilleCorrecte()){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Félicitation");
            alert.setHeaderText("Vous avez réussi à compléter le puzzle");
            alert.setContentText("Vous avez réussi à compléter le puzzle");
            alert.showAndWait();
        }
        System.out.println(this.plateau.getGrille().grilleCorrecte());
        System.out.println(this.plateau.getGrille());
    }

    /**
     * Cette méthode permet de d'afficher un pop-up qui donne le choix à l'utilisateur de soit revenir à l'état d'origine, soit confirmer son hypothèse.
     */
    public boolean popupHypothese() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Choix option de la fonctionalité hypothèse");
        alert.setHeaderText("Voulez-vous appliquer votre hypothèse sur le jeu ou revenir sur le point initiale ?");

        ButtonType ouiButton = new ButtonType("Nouveau état");
        ButtonType nonButton = new ButtonType("État d'origine");

        alert.getButtonTypes().setAll(ouiButton, nonButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ouiButton) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Cette méthode permet de rétablir la couleur initiale des lignes du mode hypothèse.
     * @param panneau : la grille
     * @param nouvelleCouleur : la nouvelle couleur à appliquer
     */
    public static void changerCouleurLignes(Pane panneau, Color nouvelleCouleur) {
        for (Node node : panneau.getChildren()) {
            if (node instanceof Line) {
                Line ligne = (Line) node;
                ligne.setStroke(nouvelleCouleur);
            }
        }
    }

    /**
     * Cette méthode permet de gèrer le mode hypothese dans le cas ou l'utilisateur souhaite effacer son brouillon
     * @param panneau : la grille
     * @param cercles : les cercles qui composent la grille de jeu
     */
    public void reinitialiserHypothse(Pane panneau, CircleHashi[] cercles) {
        panneau.getChildren().removeIf(node -> node instanceof Line);
        for(CircleHashi c : cercles){
            if(c != null){
                for(Line ligne : c.getListeLignesHypotheseSauvegarde()){
                    Line ligneInverse = c.retournerLigneInverse(ligne);
                    if(dejaPresent(cercles, ligneInverse) != true){
                        panneau.getChildren().addAll(ligne);
                        ligne.toBack();
                    }
                }
            }
        }
    }

    /**
     * Cette méthode permet de sauvegarder l'état initiale de la grille avant que le joueur effectue son brouillon d'hypothèse
     * @param cercles : les cercles qui composent la grille de jeu
     */
    public void avantHypothese(CircleHashi[] cercles){
        for(CircleHashi c : cercles){
            if(c != null){
                c.sauvegardeInitial();
            }
        }
    }

    /**
     * Cette méthode permet de revenir à l'état de sauvegarde d'avant brouillon de la grille
     * @param cercles : les cercles qui composent la grille de jeu
     */
    public void apresHypothese(CircleHashi[] cercles){
        for(CircleHashi c : cercles){
            if(c != null){
                c.EtablirsauvegardeInitial();
            }
        }
    }

    /**
     * Cette méthode permet de savoir si la ligne est déjà presente dans l'interface grille
     * @param cercles : les cercles qui composent la grille de jeu
     * @param ligneInverse : la ligne inversée que l'on souhaite savoir si elle est déjà prèsente.
     */
    public boolean dejaPresent(CircleHashi[] cercles, Line ligneInverse){
        for(CircleHashi c : cercles){
            if(c != null){
                if(c.estPresent(ligneInverse)){
                    return true;
                }
            }
        }
        return false;
    }

}
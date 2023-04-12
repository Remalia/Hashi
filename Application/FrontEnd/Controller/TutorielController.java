package Application.FrontEnd.Controller;

import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Grille.Ile;
import Application.BackEnd.Grille.Plateau;
import Application.FrontEnd.Controller.Plateau.CircleHashi;
import Application.FrontEnd.Controller.Plateau.GrilleF;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class TutorielController extends InterfacePlateau {
    @FXML
    private Pane panneau;
    @FXML
    private Label chronometre;
    @FXML
    private AnchorPane principal;

    @FXML
    protected void interactionCouleur(MouseEvent event) {
        CircleHashi cercle = (CircleHashi) event.getSource();

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
     * Cette fonction permet d'initialiser la grille Hashi
     * @throws IOException Cette exception est levée si le fichier n'est pas trouvé
     */
    @Override
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

        // Définition de la taille du panneau
        this.panneau.setPrefWidth(panneauWidth);
        this.panneau.setPrefHeight(panneauHeight);

        // Centrage du panneau dans la fenêtre
        this.principal.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newX = (newVal.doubleValue() - panneauWidth) / 2;
            this.panneau.setLayoutX(newX);
        });
        this.principal.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newY = (newVal.doubleValue() - panneauHeight) / 2;
            panneau.setLayoutY(newY);
        });
        this.plateau = new Plateau(new Grille("NiveauTutoriel"));
        boolean isNew = true;
        this.plateau.getPlateauFromYAML(isNew);
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
        if(!isNew)
            dessinerPontSauvegarde(plateau.getGrille(), this.grille.getCerclesHashi());
        // Autres initialisations spécifiques au TutorielController

    }
}


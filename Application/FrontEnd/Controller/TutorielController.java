package Application.FrontEnd.Controller;

import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Grille.Ile;
import Application.BackEnd.Grille.Plateau;
import Application.FrontEnd.Controller.Plateau.CircleHashi;
import Application.FrontEnd.Controller.Plateau.GrilleF;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.Objects;

public class TutorielController extends InterfacePlateau {

    @FXML
    private TextFlow instructions;

    private Text text1;

    @FXML
    private Group aides;

    @FXML
    private Group check;

    @FXML
    private Group croix;

    @FXML
    private Group hypo;

    @FXML
    private Group pause;

    @FXML
    private Group undo;

    private boolean active=false;

    @FXML
    private Group redo;

    private ImageView arrow;
    @FXML
    private Pane panneau;
    @FXML
    private AnchorPane principal;

    private int compteur=0;

    public void placer_arrow(double x, double y){
        arrow.setLayoutX(x);
        arrow.setLayoutY(y);
    }

    @Override
    protected void dessinerLigne(Circle cercle1, Circle cercle2, Pane panneau) {
        compteur++;
        super.dessinerLigne(cercle1, cercle2, panneau);
        double centerX = (cercle1.getCenterX() + cercle2.getCenterX()) / 2;
        double centerY = (cercle1.getCenterY() + cercle2.getCenterY()) / 2;

        if (compteur == 1){
            active=true;
            placer_arrow(centerX - 60,centerY-60);
            text1.setText("Répetez cette action encore une fois");
            panneau.getChildren().add(arrow);
        }else if(compteur == 2) {
            Point2D point = undo.localToScene(0.0, 0.0);
            placer_arrow(point.getX()-250,point.getY()-180);
            text1.setText("On a fait un pont de trop, appuie sur le bouton 'undo' pour résoudre cela. \n\nLe pont va être enlever.");
            this.undo.setVisible(true);
            active=false;
        }

    }

    /**
     * Cette méthode permet revenir à l'état précédent
     * @param event : Event
     */
    @Override
    @FXML
    public void undoBouton(ActionEvent event){
        super.undoBouton(event);
        this.redo.setVisible(true);
        Point2D point = this.redo.localToScene(0.0, 0.0);
        placer_arrow(point.getX()-250,point.getY()-180);
        text1.setText("Maintenant clique sur le 'redo', pour que le pont puisse revenir.");
    }

    /**
     * Cette méthode permet revenir à l'état suivant
     * @param event : Event
     */
    @Override
    @FXML
    public void redoBouton(ActionEvent event){
        super.redoBouton(event);
        Point2D point = this.pause.localToScene(0.0, 0.0);
        placer_arrow(point.getX()-250,point.getY()-180);
        text1.setText("Tu peux cliquer de nouveau sur le cercle pour passer ton nombre de ponts à 0. \n\nPuis clique sur le bouton pause.");
        this.pause.setVisible(true);
    }

    @Override
    @FXML
    public void hypothese(ActionEvent event){
        super.hypothese(event);
        Point2D point = this.hypo.localToScene(0.0, 0.0);
        placer_arrow(point.getX()-250,point.getY()-180);
        text1.setText("Je te laisse terminer la partie ! N'oublie pas de cliquer de nouveau sur le mode hypothèse pour désactiver le mode hypothèse et enregistrer ou non tes modifications");
        this.hypo.setVisible(true);
    }

    @Override
    @FXML
    public void stop_timer(ActionEvent event) {
        Image newImage;
        if (active) {
            panneau.setVisible(true);
            newImage = new Image("Application/FrontEnd/assets/bouton-pause.png");
            this.switch_timer.setImage(newImage);
            active=false;
        }else{
            panneau.setVisible(false);
            newImage = new Image("Application/FrontEnd/assets/bouton-jouer.png");
            switch_timer.setImage(newImage);
            active=true;
        }
        this.undo.setDisable(false);
        Point2D point = this.croix.localToScene(0.0, 0.0);
        placer_arrow(point.getX()-250,point.getY()-180);
        text1.setText("Tu peux cliquer sur la croix pour remettre la partie à 0:") ;
        this.croix.setVisible(true);
    }

    @Override
    @FXML
    public void reinitialisationAZero(ActionEvent event){
        super.reinitialisationAZero(event);
        Point2D point = this.check.localToScene(0.0, 0.0);
        placer_arrow(point.getX()-250,point.getY()-180);
        text1.setText("Maintenant essaie de résoudre la game ! Bonne chance ! Si tu souhaites vérifier ta grille pour voir si tu as des erreurs, clique sur ce bouton.") ;
        this.check.setVisible(true);
        this.aides.setVisible(true);
    }

    /**
     * Cette méthode permet de vérifier si la grille est correcte en appuyant sur le bouton
     * @param event
     */
    @FXML
    public void checkBouton(ActionEvent event){
        super.checkBouton(event);
        Point2D point = this.hypo.localToScene(0.0, 0.0);
        placer_arrow(point.getX()-250,point.getY()-180);
        text1.setText("Maintenant que tu as vérifier tes potentielles erreurs tu peux utiliser le mode hypothèse : les ponts que tu placeras sont pris en compte seulement si tu le souhaites.") ;
        this.hypo.setVisible(true);
    }

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
    public void initialize() throws IOException{

        this.aides.setVisible(false);
        this.check.setVisible(false);
        this.croix.setVisible(false);
        this.hypo.setVisible(false);
        this.pause.setVisible(false);
        this.undo.setVisible(false);
        this.redo.setVisible(false);

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


            text1 = new Text("Bienvenue dans ce tutoriel ! \n\nIci tu vas comprendre comment fonctionne tous les boutons !\n\nPour commencer clique sur deux cercles l'un après l'autre");
            text1.setFill(Color.BLACK);
            text1.setFont(new Font(25));
            instructions.getChildren().addAll(text1);
            Image image = new Image("Application/FrontEnd/assets/arrow.png");
            arrow = new ImageView(image);
            arrow.setFitHeight(50);
            arrow.setFitWidth(50);
    }
}


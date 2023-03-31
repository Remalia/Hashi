package Application.FrontEnd;

import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Grille.Ile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.IOException;

public class GrilleV4 extends Application {

    public static final Color etatNormal = Color.YELLOW;
    public static final Color etatSelect = Color.GREEN;

    // Dimensions de notre grille et de ces composants
    private final int NB_LIGNES = 10;
    private final int NB_COLONNES = 10;
    private final int RAYON = 20;
    private final int ESPACE = 30;
    private final int LARGEUR_FENETRE = NB_COLONNES * (RAYON * 2 + ESPACE) + ESPACE*2;
    private final int HAUTEUR_FENETRE = NB_LIGNES * (RAYON * 2 + ESPACE) + ESPACE*2;
    private final int COORDPRIMCERCLEX = 70;
    private final int COORDPRIMCERCLEY = 70;

    private CircleHashi[] cerclesHashi = new CircleHashi[NB_LIGNES * NB_COLONNES];

    // Sauvegardes temporelle
    private Circle premierCercle = null;
    private Circle deuxiemeCercle = null;
    private boolean premierCercleClique = false;
    private Integer indicePremierCercle;
    private Integer indiceSecondCercle;

    // Panneau qui contiendra notre grille
    Pane panneau = new Pane();


    @Override
    public void start(Stage primaryStage) throws IOException {
        /*
        // Création des cercles hashi
        for (int i = 0; i < NB_LIGNES ; i++) {
            for (int j = 0; j < NB_COLONNES ; j++) {
                Circle cercle = new Circle(COORDPRIMCERCLEX * (i+1), COORDPRIMCERCLEY * (j+1), RAYON, Color.BLACK); // Ici, les coordonnées des cercles ne sont pas initialisé
                cerclesHashi[i * NB_COLONNES + j] = new CircleHashi(cercle);
                cercle.setOnMouseClicked(this::changerCouleur);
            }
        }


        //	Ajout des cerlces dans le panneau

        for (int i = 0; i < NB_LIGNES; i++) {
            for (int j = 0; j < NB_COLONNES; j++) {
                Circle cercle = cerclesHashi[i * NB_COLONNES + j].cercle;
            	panneau.getChildren().add(cercle);
            }
        }
        */
        Grille grille = new Grille("NiveauTest");
        grille.getGrilleFromYAML(grille.getFileNiveau());
        grille.saveGrilleToYAML();
        System.out.println(grille.getListIle());
        for(Ile ile : grille.getListIle()){
            System.out.println(ile.getAbs() + " " + ile.getOrd() + " " + ile.getNbPonts());
            Circle cercle = new Circle(COORDPRIMCERCLEX * (ile.getAbs()+1), COORDPRIMCERCLEY * (ile.getOrd()+1), RAYON, etatNormal); // Ici, les coordonnées des cercles ne sont pas initialisé
            cercle.setStrokeWidth(6.0);
            cercle.setStroke(Color.ORANGE);
            cerclesHashi[ile.getAbs() * NB_COLONNES + ile.getOrd()] = new CircleHashi(cercle,ile);
            cercle.setOnMouseClicked(this::interactionCouleur);
            panneau.getChildren().add(cercle);
            panneau.getChildren().add(cerclesHashi[ile.getAbs() * NB_COLONNES + ile.getOrd()].text);
        }



        // Affichage du panneau qui contiendra notre grilleHashi
        Scene scene = new Scene(panneau, LARGEUR_FENETRE, HAUTEUR_FENETRE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Application.BackEnd.Grille.Grille Hashi");
        primaryStage.show();
    }

    /**
     * Fonction qui donne le CircleHashi à l'aide de son cercle
     * @param c cercle appartenant au CircleHashi
     */
    private CircleHashi getCircleHashi(Circle c){
        for(CircleHashi cercleHashi : cerclesHashi){
            if(cercleHashi.cercle == c){
                return cercleHashi;
            }
        }
        return null;
    }

    // Méthode appelée lorsqu'un cercle est cliqué. Elle change la couleur du cercle + gestion des ponts.
    private void interactionCouleur(MouseEvent event) {
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
                        if ((cerclesHashi[i].cercle.getCenterX() == deuxiemeCercle.getCenterX()) && (cerclesHashi[i].cercle.getCenterY() == deuxiemeCercle.getCenterY())) {
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
                    if((cerclesHashi[i].cercle.getCenterX() == premierCercle.getCenterX()) && (cerclesHashi[i].cercle.getCenterY() == premierCercle.getCenterY())) {
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

        if(cerclesHashi[indicePremierCercle].ligneEstDansListe(ligne2) != true && cerclesHashi[indicePremierCercle].ligneEstDansListe(ligne3) != true) {

            if((cerclesHashi[indicePremierCercle].ligneEstDansListe(ligne1) != true)) {
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

    public static void main(String[] args) {
        launch(args);
    }

}
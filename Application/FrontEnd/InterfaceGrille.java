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

public class InterfaceGrille extends Application {

    public static Color etatNormal = Color.YELLOW;
    public static Color etatSelect = Color.GREEN;

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
    private CircleHashi premierCercle = null;
    private CircleHashi deuxiemeCercle = null;
    private boolean premierCercleClique = false;
    private Integer indicePremierCercle;
    private Integer indiceSecondCercle;

    private Grille grille;

    // Panneau qui contiendra notre grille
    Pane panneau = new Pane();


    /**
     * Redéfinition de la méthode start de Application
     * @param primaryStage la fenêtre principale
     * @throws IOException si le fichier YAML n'est pas trouvé
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Grille grille = new Grille("NiveauTest");
        grille.getGrilleFromYAML(grille.getFileNiveau());
        grille.saveGrilleToYAML();
        this.grille = new Grille(grille);
        System.out.println(grille);
        for(Ile ile : grille.getListIle()){
            double coordX = COORDPRIMCERCLEX * (ile.getAbs()+1);
            double coordY = COORDPRIMCERCLEY * (ile.getOrd()+1);
            CircleHashi cercle = new CircleHashi(ile,coordX,coordY , RAYON, etatNormal); // Ici, les coordonnées des cercles ne sont pas initialisé
            cercle.setStrokeWidth(6.0);
            cercle.setStroke(Color.ORANGE);
            cerclesHashi[ile.getAbs() * NB_COLONNES + ile.getOrd()] = cercle;
            cercle.setOnMouseClicked(this::interactionCouleur);
            panneau.getChildren().add(cercle);
            panneau.getChildren().add(cerclesHashi[ile.getAbs() * NB_COLONNES + ile.getOrd()].getText());
        }




        // Affichage du panneau qui contiendra notre grilleHashi
        Scene scene = new Scene(panneau, LARGEUR_FENETRE, HAUTEUR_FENETRE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Application.BackEnd.Grille.Grille Hashi");
        primaryStage.show();
    }


    /**
     * Méthode appelée lorsqu'un cercle est cliqué. Elle change la couleur du cercle + gestion des ponts.
     * @param event évènement de la souris
     */
    private void interactionCouleur(MouseEvent event) {
        CircleHashi cercle = (CircleHashi) event.getSource();

        // Cas de réinitialisation du clique de cercle
        if(cercle == premierCercle) {
            reinitialiserCercles();
        }

        // Cas ou deux cercles ont été cliqués
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

        // Cas ou seulement un cercle est cliqué
        else {
            premierCercle = cercle;
            premierCercle.setFill(Color.GREEN);
            premierCercleClique = true;
            indicePremierCercle = trouverIndiceCercle(premierCercle);
        }
    }

    /**
     * Méthode qui retourne vrai si les deux cercles sont sur la même ligne ou la même colonne
     * @param cercle1 premier cercle à comparer
     * @param cercle2 deuxième cercle à comparer
     * @return vrai si les deux cercles sont sur la même ligne ou la même colonne
     */
    private boolean memeLigneOuColonne(CircleHashi cercle1, CircleHashi cercle2) {
        double c1x = cercle1.getCenterX();
        double c2x = cercle2.getCenterX();
        double c1y = cercle1.getCenterY();
        double c2y = cercle2.getCenterY();

        return (c1x == c2x || c1y == c2y);
    }

    /**
     * Méthode qui réinitialise les variables de la classe
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
     * Méthode qui retourne l'indice du cercle dans le tableau de cercles
     * @param cercle cercle dont on veut l'indice
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

    // Dessiner la ligne entre les cercles

    /**
     * Fonction qui dessine une ligne entre 2 cercles
     * @param cercle1 cercle de départ
     * @param cercle2 cercle d'arrivé
     * @param panneau panneau sur lequel dessiner la ligne
     */
    private void dessinerLigne(Circle cercle1, Circle cercle2, Pane panneau) {
        if(this.grille.estIncrementable(cerclesHashi[indicePremierCercle].getIle(), cerclesHashi[indiceSecondCercle].getIle()) == false){
            System.out.println("Erreur : pont impossible");
            return;
        }
        Line ligne1 = new Line(cercle1.getCenterX(), cercle1.getCenterY(), cercle2.getCenterX(), cercle2.getCenterY());
        Line ligne2 = new Line(cercle1.getCenterX()+5, cercle1.getCenterY()+5, cercle2.getCenterX()+5, cercle2.getCenterY()+5);
        Line ligne3 = new Line(cercle1.getCenterX()-5, cercle1.getCenterY()-5, cercle2.getCenterX()-5, cercle2.getCenterY()-5);
        //System.out.println(ligne1);

        if(cerclesHashi[indicePremierCercle].ligneEstDansListe(ligne2) != true && cerclesHashi[indicePremierCercle].ligneEstDansListe(ligne3) != true) {

            if((cerclesHashi[indicePremierCercle].ligneEstDansListe(ligne1) != true)) {
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

    public static void main(String[] args) {
        launch(args);
    }

}

package Application.BackEnd.Technique;

import Application.BackEnd.Grille.Grille;
import Application.BackEnd.Grille.Ile;
import javafx.scene.paint.Color;

/**
 * Classe de test pour les surbrillance de réseau et de voisins
 * @author Anna Beranger
 */
public class SurbrillanceTest {
    public static void main(String [] args) {
        Color c = Color.rgb(0, 0, 255);

        Grille grilleTest = new Grille();

        System.out.println("On affiche la grille");

        System.out.println(grilleTest);

        /**
         On ajoute des îles
         */

        System.out.println("On ajoute des îles");

        Ile ile1 = new Ile(1, 1, 0, 0, c);
        Ile ile2 = new Ile(2, 2, 0, 2, c);
        Ile ile3 = new Ile(3, 2, 0, 9, c);
        Ile ile4 = new Ile(4, 2, 3, 2, c);
        Ile ile5 = new Ile(5, 2, 3, 9, c);
        grilleTest.ajouterIle(ile1);
        grilleTest.ajouterIle(ile2);
        grilleTest.ajouterIle(ile3);
        grilleTest.ajouterIle(ile4);
        grilleTest.ajouterIle(ile5);
        grilleTest.ajouterPont(ile1, ile2, 1);
        grilleTest.ajouterPont(ile4, ile2, 1);
        System.out.println(grilleTest);

        SurbrillanceReseau surbriR = new SurbrillanceReseau(grilleTest, Color.rgb(0,0,255), Color.rgb(0,255,255));
        SurbrillanceVoisins surbriV = new SurbrillanceVoisins(grilleTest, Color.rgb(0,0,255), Color.rgb(0,255,255));

        /* ile1 ile source pour tester la surbrillance reseau */
        surbriR.activer(ile1);
        surbriR.desactiver();

        surbriV.activer(ile1);
        surbriV.desactiver();

    }



}

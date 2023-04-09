package Application.BackEnd.Technique;

import Application.BackEnd.Grille.*;

import java.util.ArrayList;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;
import java.lang.Math;

import java.util.*;

public class TestTechnique {

    public static void main(String [] args)
    {
        Color c = Color.rgb(0,0,255);

        Grille grilleTest = new Grille();

        System.out.println("On affiche la grille");

        System.out.println(grilleTest);

        if(Technique.trouverTechniqueGrille(grilleTest) == null)
        {
            System.out.println("Il n'y a pas de technique appliquable");
        }
        else
        {
            System.out.println("Il y a bien une technique appliquable");
        }

        /**
         On ajoute des îles pour regarder s'il y a des techniques appliquables
         */

        System.out.println("On ajoute des îles");

        Ile ile1 = new Ile(1,1,0,0,c);
        Ile ile2 = new Ile(2,2,0,2,c);
        Ile ile3 = new Ile(3,2,0,9,c);
        Ile ile4 = new Ile(4,2,3,2,c);
        Ile ile5 = new Ile(5,2,3,9,c);
        grilleTest.ajouterIle(ile1);
        grilleTest.ajouterIle(ile2);
        grilleTest.ajouterIle(ile3);
        grilleTest.ajouterIle(ile4);
        grilleTest.ajouterIle(ile5);
        //grilleTest.ajouterPont(ile1,ile2,1);
        System.out.println("On affiche la grille");

        System.out.println(grilleTest);

        Technique t = new Technique();

        if((t =Technique.trouverTechniqueGrille(grilleTest)) == null)
        {
            System.out.println("Il n'y a pas de technique appliquable après ajout des îles");
        }
        else
        {
            System.out.println("Il y a bien une technique appliquable après ajout des îles");
        }

        System.out.println(t.getDescription());
    }
}

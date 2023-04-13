package Application.BackEnd.Technique;

import Application.BackEnd.Grille.*;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;
import java.lang.Math;

import java.util.*;

public class TestTechnique {

    public static void main(String [] args) throws IOException
    {
        Technique t = new Technique();

        Color c = Color.rgb(0,0,255);

        Grille grilleTest = new Grille();

        System.out.println("On affiche la grille");

        System.out.println(grilleTest);

        if(t.trouverTechniqueGrille(grilleTest) == null)
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
        System.out.println("On affiche la grille");

        System.out.println(grilleTest);

        if((t = t.trouverTechniqueGrille(grilleTest)) == null)
        {
            System.out.println("Il n'y a pas de technique appliquable après ajout des îles");
        }
        else
        {
            System.out.println("Il y a bien une technique appliquable après ajout des îles");
        }

        System.out.println(t.getDescription());



        // Deuxième test
        System.out.println("\n----------------\nVersion 2\n---------------");


        Grille grille2 = new Grille();

        System.out.println("On affiche la grille");
        System.out.println(grille2);
        System.out.println("-------------");

        if(t.trouverTechniqueGrille(grille2) == null)
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

        System.out.println("-----------------");
        System.out.println("On ajoute des îles");
        System.out.println("------------------");
        //ile1 = new Ile(1,1,0,0,c);
        ile2 = new Ile(2,3,0,2,c);
        ile3 = new Ile(3,2,0,9,c);
        ile4 = new Ile(4,2,3,2,c);
        ile5 = new Ile(5,2,3,9,c);
        //grille2.ajouterIle(ile1);
        grille2.ajouterIle(ile2);
        grille2.ajouterIle(ile3);
        grille2.ajouterIle(ile4);
        grille2.ajouterIle(ile5);

        System.out.println("On affiche la grille");
        System.out.println("--------------------");
        System.out.println(grille2);
        System.out.println("--------------------");

        if(t.ajoutPontSimple(ile2, ile3, grille2))
        {
            System.out.println("On peut créer un pont simple !");
        }
        else
        {
            System.out.println("On ne peut pas créer un pont simple !");
        }
        if(t.ajoutPontDouble(ile2, ile3, grille2))
        {
            System.out.println("On peut créer un pont double !");
        }
        else
        {
            System.out.println("On ne peut pas créer un pont double !");
        }

        System.out.println("-----------\nPremière île:");
        System.out.println(ile2.toStringConsole());
        System.out.println("-------------\nSeconde île:");
        System.out.println(ile3.toStringConsole());

        System.out.println("------------");

        System.out.println("On ajoute un pont");
        grille2.ajouterPont(ile2, ile3, 2);
        System.out.println("------------");
        System.out.println(grille2);
        System.out.println("\n--------------\n");

        if(t.ajoutPontSimple(ile2, ile3, grille2))
        {
            System.out.println("On peut créer un pont simple !");
        }
        else
        {
            System.out.println("On ne peut pas créer un pont simple !");
        }
        if(t.ajoutPontDouble(ile2, ile3, grille2))
        {
            System.out.println("On peut créer un pont double !");
        }
        else
        {
            System.out.println("On ne peut pas créer un pont double !");
        }

        System.out.println("------------\nPremière île:");
        System.out.println(ile2.toStringConsole());
        System.out.println("-------------\nSeconde île:");
        System.out.println(ile3.toStringConsole());
        System.out.println("--------------\n");

        System.out.println(grille2);
        System.out.println("--------------\n");

        if(t.ajoutPontSimple(ile2, ile4, grille2))
        {
            System.out.println("On peut créer un pont simple !");
        }
        else
        {
            System.out.println("On ne peut pas créer un pont simple !");
        }
        if(t.ajoutPontDouble(ile2, ile4, grille2))
        {
            System.out.println("On peut créer un pont double !");
        }
        else
        {
            System.out.println("On ne peut pas créer un pont double !");
        }

        System.out.println("------------\nPremière île:");
        System.out.println(ile2.toStringConsole());
        System.out.println("------------\nSeconde île:");
        System.out.println(ile4.toStringConsole());

        System.out.println("------------\n");
        System.out.println(grille2);
        System.out.println("\n----------------\nOn cherche une technique");

        if((t = t.trouverTechniqueGrille(grille2)) == null)
        {
            System.out.println("Il n'y a pas de technique appliquable après ajout des îles");
        }
        else
        {
            System.out.println("Il y a bien une technique appliquable après ajout des îles");
        }

        System.out.println(t.getDescription());

        System.out.println("\n---------------\n");

        System.out.println(" Version 3 ");

        System.out.println("\n--------------\n");

        Grille grille3 = new Grille();

        ile1 = new Ile(1,1,3,0,c);
        ile2 = new Ile(2,4,0,2,c);
        ile3 = new Ile(3,3,0,9,c);
        ile4 = new Ile(4,2,4,2,c);
        ile5 = new Ile(5,2,3,9,c);

        System.out.println(grille3);
        System.out.println("\n-----------\n");
        System.out.println("On ajoute des îles");
        System.out.println("\n-----------\n");

        grille3.ajouterIle(ile1);
        grille3.ajouterIle(ile2);
        grille3.ajouterIle(ile3);
        grille3.ajouterIle(ile4);
        grille3.ajouterIle(ile5);

        System.out.println(grille3);
        System.out.println("\n-----------\n");

        System.out.println("On ajoute un pont");
        grille3.ajouterPont(ile2, ile4, 2);
        System.out.println("\n-----------\nPremière île:");
        System.out.println(ile2.toStringConsole());
        System.out.println("\n-----------\nSeconde île:");
        System.out.println(ile4.toStringConsole());
        System.out.println("\n-----------\n");
        System.out.println(grille3);
        System.out.println("\n------------\n");

        if(t.ajoutPontSimple(ile1, ile5, grille3))
        {
            System.out.println("On peut créer un pont simple !");
        }
        else
        {
            System.out.println("On ne peut pas créer un pont simple !");
        }
        if(t.ajoutPontDouble(ile1, ile5, grille3))
        {
            System.out.println("On peut créer un pont double !");
        }
        else
        {
            System.out.println("On ne peut pas créer un pont double !");
        }

        System.out.println("------------\nPremière île:");
        System.out.println(ile1.toStringConsole());
        System.out.println("------------\nSeconde île:");
        System.out.println(ile5.toStringConsole());
        System.out.println("\n----------\n");
        System.out.println(grille3);
        System.out.println("\n----------\n");

        if(t.ajoutPontSimple(ile2, ile3, grille3))
        {
            System.out.println("On peut créer un pont simple !");
        }
        else
        {
            System.out.println("On ne peut pas créer un pont simple !");
        }
        if(t.ajoutPontDouble(ile2, ile3, grille3))
        {
            System.out.println("On peut créer un pont double !");
        }
        else
        {
            System.out.println("On ne peut pas créer un pont double !");
        }

        System.out.println("------------\nPremière île:");
        System.out.println(ile2.toStringConsole());
        System.out.println("------------\nSeconde île:");
        System.out.println(ile3.toStringConsole());
        System.out.println("\n----------\n");
        System.out.println(grille3);
        System.out.println("\n----------------\nOn cherche une technique");

        if((t = t.trouverTechniqueGrille(grille3)) == null)
        {
            System.out.println("Il n'y a pas de technique appliquable après ajout des îles");
        }
        else
        {
            System.out.println("Il y a bien une technique appliquable après ajout des îles");
        }

        System.out.println(t.getDescription());

        System.out.println("\n---------------\n");
        System.out.println("Version 5");
        System.out.println("\n---------------\n");


        Grille grille5 = new Grille("NiveauxFacile/Niveau10");
        grille5.getGrilleFromYAML(false);

        System.out.println("\n-----------\n");
        System.out.println("On affiche la grille");
        System.out.println(grille5);
        System.out.println("\n-----------\n");
        System.out.println("On cherche une technique");
        System.out.println("\n-----------\n");



        if((t = t.bloquagePont(grille5)) != null)
        {
            System.out.println("Il y a une technique applicable");
        }
        else{
            System.out.println("Il n'y a pas de technique applicable");
        }
        System.out.println("\n-----------\n");
        System.out.println(t.getDescription());
        System.out.println(t.getIleCour().toStringConsole());
    }
}

package Application.BackEnd.Grille;

import java.awt.Color;
import java.util.*;

public class TestIle{
    public static void main(String [] args)
    {
        Color c = new Color(100, 0, 0);

        Ile ileTest1 = new Ile(1, 5, 4, 2, c);
        Ile ileTest2 = new Ile(2, 5, 8, 2, c);
        Ile ileTest3 = new Ile(2, 5, 4, 5, c);

        Grille g = new Grille();

        g.ajouterIle(ileTest1);
        g.ajouterIle(ileTest2);

        System.out.println(g);

        g.ajouterPont(ileTest1, ileTest2,1);
        System.out.println(g);

        g.ajouterPont(ileTest1, ileTest2, 2);
        System.out.println(g);

        g.ajouterPont(ileTest1, ileTest2, 0);
        System.out.println(g);

        List<Ile> i = ileTest1.getIlesVoisines();

        System.out.println("Il y a "+i.size()+" iles voisines pour l'île test 1");

        g.ajouterIle(ileTest3);
        System.out.println(g);

        i = ileTest1.getIlesVoisines();

        System.out.println("Il y a "+i.size()+" iles voisines pour l'île test 1");
    }
}
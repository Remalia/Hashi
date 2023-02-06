import java.io.*;
import java.util.*;
import java.awt.Color;

public class Grille {
    Stack<Pont> pileSvg;
    Stack<Pont> pileRecup;
    Object[][] matriceGrille;
    boolean modeHyp;
    int difficulte;

    Grille(){
        /** remplissage de la grille temporaire pour les tests */
        try{
            matriceGrille = new Object[10][10];
            int i, j;
            for(i = 0; i < 10; i++){
                for(j = 0; j < 10; j++){
                    matriceGrille[i][j] = new Object();
                }
            }
            Ile ile1 = new Ile(1, 5, 4, 2);
            Ile ile2 = new Ile(2, 6, 2, 2);
            Pont pont1 = new Pont(ile1, ile2, new Color(100, 0, 0));
            matriceGrille[ile1.getAbs()][ile1.getOrd()] = ile1;
            matriceGrille[ile2.getAbs()][ile2.getOrd()] = ile2;
            matriceGrille[3][2] = pont1;
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public String toString(){
        int i, j;
        String s = "";
        for(i = 0; i < 10; i++){
            for(j = 0; j < 10; j++){
                if(matriceGrille[i][j].getClass() == Ile.class)
                    s += "I ";
                else if(matriceGrille[i][j].getClass() == Pont.class)
                    s += "P ";
                else
                    s += "X ";
            }
            s += "\n";
        }
        return s;
    }
    

    public static void main(String[] args){
        
        Grille grilleTest = new Grille();
        System.out.println(grilleTest.toString());
        
    }
}

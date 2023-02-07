import java.io.*;
import java.util.*;
import java.awt.Color;

public class Grille {

    Color c = new Color(0, 0, 255);

    Stack<Pont> pileSvg;
    Stack<Pont> pileRecup;
    Object[][] matriceGrille;
    boolean modeHyp;
    int difficulte;

    Grille(int[][] init){
        /** remplissage de la grille temporaire pour les tests */
        try{
            this.pileSvg = new Stack<Pont>();
            this.pileRecup = new Stack<Pont>();
            matriceGrille = new Object[10][10]; 
            int i, j;
            /** initialisation de la grille en dur TEMPORAIRE*/
            for(i = 0; i < 10; i++){
                for(j = 0; j < 10; j++){
                    if(init[i][j] < 0)
                        matriceGrille[i][j] = new Object();
                    else if(init[i][j] > 0){
                        /** init contient les numéros des îles */
                        matriceGrille[i][j] = new Ile(1, init[i][j], i, j, c);
                    }
                }
            }   
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void ajouterPont(Ile ile1, Ile ile2){
        int i;
        Pont pont = new Pont(ile1, ile2, c);
        if(ile1.getAbs() == ile2.getAbs()){
            if(ile1.getOrd() < ile2.getOrd()){
                for(i = ile1.getOrd() + 1; i < ile2.getOrd() - 1; i++)
                    matriceGrille[ile1.getAbs()][i] = pont;
            }else{
                for(i = ile2.getOrd() + 1; i < ile1.getOrd() - 1; i++)
                    matriceGrille[ile1.getAbs()][i] = pont; 
            }
        } else if(ile1.getOrd() == ile2.getOrd()){
            if(ile1.getAbs() < ile2.getAbs()){
                for(i = ile1.getAbs() + 1; i < ile2.getAbs() - 1; i++)
                    matriceGrille[i][ile2.getOrd()] = pont;
            }else{
                for(i = ile2.getAbs() + 1; i < ile1.getAbs() - 1; i++)
                    matriceGrille[i][ile2.getOrd()] = pont; 
            }
        }
        this.pileSvg.push(pont);
    }

    public void retirerPont(Pont pont){
        int i;
        Ile ile1 = pont.getIle1();
        Ile ile2 = pont.getIle2();
        if(ile1.getAbs() == ile2.getAbs()){
            if(ile1.getOrd() < ile2.getOrd()){
                for(i = ile1.getOrd() + 1; i < ile2.getOrd() - 1; i++)
                    matriceGrille[ile1.getAbs()][i] = -1;
            }else{
                for(i = ile2.getOrd() + 1; i < ile1.getOrd() - 1; i++)
                    matriceGrille[ile1.getAbs()][i] = -1; 
            }
        } else if(ile1.getOrd() == ile2.getOrd()){
            if(ile1.getAbs() < ile2.getAbs()){
                for(i = ile1.getAbs() + 1; i < ile2.getAbs() - 1; i++)
                    matriceGrille[i][ile2.getOrd()] = -1;
            }else{
                for(i = ile2.getAbs() + 1; i < ile1.getAbs() - 1; i++)
                    matriceGrille[i][ile2.getOrd()] = -1; 
            }
        }
        this.pileSvg.push(pont);
    }

    public String toString(){
        int i, j;
        String s = "";
        for(i = 0; i < 10; i++){
            for(j = 0; j < 10; j++){
                if(matriceGrille[i][j] instanceof  Ile)
                    s += ((Ile)matriceGrille[i][j]).getNum() + " ";
                /** un même pont dans plusieurs cases de la matrice?? */
                else if(matriceGrille[i][j].getClass() == Pont.class)
                    s += "- ";
                else
                    s += ". ";
            }
            s += "\n";
        }
        return s;
    }

    public int getTaille(){
        return 10;
    }
    

    public static void main(String[] args){
        
        int[][] init1 = {
            {3, -1, 2, -1, 2, -1, -1, -1, -1, 4},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {4, -1, -1, -1, -1, -1, 2, -1, -1, 4},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {4, -1, 2, -1, 2, -1, 7, -1, 2, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, 1, -1, 1, -1, 4, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, 2, -1, 5, -1, 6, -1, 2, -1},
            {2, -1, -1, -1, -1, -1, -1, -1, -1, 3}

        };
        int[][] init2 = {
            {2, -1, -1, -1, -1, -1, -1, -1, -1, 2},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {2, -1, -1, -1, -1, -1, -1, -1, -1, 2}

        };
        Grille grilleTest = new Grille(init2);
        Color c = new Color(0, 0, 255);
        try {
            grilleTest.ajouterPont(new Ile(1,2,0,0,c), new Ile(2,2,0,10,c));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(grilleTest.toString());
        
    }
}

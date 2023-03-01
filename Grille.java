import java.util.*;
import java.awt.Color;

public class Grille {

    Color c = new Color(0, 0, 255);

    Stack<Pont> pileSvg;
    Stack<Pont> pileRecup;
    Object[][] matriceGrille;
    boolean modeHyp;
    int difficulte;

    /*
     * Constructeur de la grille
     * @param init la grille initiale
     */
    public Grille(int[][] init){
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

    /*
     * Ajoute un pont à la grille
     * @param ile1 l'île de départ du pont
     * @param ile2 l'île d'arrivée du pont
     * @return void
     */
    public void ajouterPont(Ile ile1, Ile ile2){
        int i;
        Pont pont = new Pont(ile1, ile2, c);
        //On ne peut pas créer de pont entre ces îles
        if(!verifCreationPont(ile1, ile2)) return;
        //Si on peut onvérifie si le pont est horizontal ou vertical
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
        //On ajoute le pont à la liste des ponts créés
        this.pileSvg.push(pont);
    }

    /*
     * Vérifie si la création d'un pont est possible
     * @param ile1 l'île de départ du pont
     * @param ile2 l'île d'arrivée du pont
     * @return true si le pont peut être créé, false sinon
     */
    private boolean verifCreationPont(Ile ile1, Ile ile2){
        int i;
        // si pont vertical
        if(ile1.getAbs() == ile2.getAbs()){
            if(ile1.getOrd() < ile2.getOrd()){
                for(i = ile1.getOrd() + 1; i < ile2.getOrd() - 1; i++)
                    if(matriceGrille[ile1.getAbs()][i].getClass() != Object.class)return false;
            }else{
                for(i = ile2.getOrd() + 1; i < ile1.getOrd() - 1; i++)
                    if(matriceGrille[ile1.getAbs()][i].getClass() != Object.class) return false; 
            }
        } else if(ile1.getOrd() == ile2.getOrd()){
            if(ile1.getAbs() < ile2.getAbs()){
                for(i = ile1.getAbs() + 1; i < ile2.getAbs() - 1; i++)
                if(matriceGrille[ile1.getAbs()][i].getClass() != Object.class) return false; 
            }else{
                for(i = ile2.getAbs() + 1; i < ile1.getAbs() - 1; i++)
                if(matriceGrille[ile1.getAbs()][i].getClass() != Object.class) return false; 
            }
        }
        return true;
    }
    
    /*
     * Retire un pont de la grille
     * @param pont le pont à retirer
     * @return void
     * 
     */
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

    /*
     * Retourne la pile des ponts sauvegardés
     * @return la pile des ponts sauvegardés
     */
    public String toString(){
        int i, j;
        String s = "";
        for(i = 0; i < 10; i++){
            for(j = 0; j < 10; j++){
                if(matriceGrille[i][j] instanceof  Ile)
                    s += ((Ile)matriceGrille[i][j]).getNum() + " ";
                else if(matriceGrille[i][j].getClass() == Pont.class)
                    s += "= ";
                else
                    s += ". ";
            }
            s += "\n";
        }
        return s;
    }

    /*
     * Retourne la pile des ponts sauvegardés
     * @return la pile des ponts sauvegardés
     */
    public int getTaille(){
        return 10;
    }
    

    public static void main(String[] args){
        /* 
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
        */
        int[][] init2 = {
            {2, -1, -1, -1, -1, 2, -1, -1, -1, 2},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {2, -1, -1, -1, -1, -1, -1, -1, -1, 2},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {2, -1, -1, -1, -1, 2, -1, -1, -1, 2}

        };
        Grille grilleTest = new Grille(init2);
        Color c = new Color(0, 0, 255);
        try {
            grilleTest.ajouterPont(new Ile(1,2,4,0,c), new Ile(2,2,4,10,c));
            grilleTest.ajouterPont(new Ile(1,2,0,5,c), new Ile(2,2,10,5,c));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(grilleTest.toString());
        
    }
}

import java.util.*;
import java.awt.Color;

public class Grille {

    Color c = new Color(0, 0, 255);

    Stack<Pont> pileSvg;
    Stack<Pont> pileRecup;
    private Object[][] matriceGrille;
    boolean modeHyp;
    int difficulte;

    /**
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

    // potentiels voisins dans creation ile
    // chnager verif ponts

    /** 
     * Ajoute un pont à la grille
     * @param ile1 l'île de départ du pont
     * @param ile2 l'île d'arrivée du pont
     * @return void
     */
    public void ajouterPont(Ile ile1, Ile ile2){
        int i;
        Object objet = verifCreationPont(ile1, ile2);
        Pont pont;

        //Si il y'a une autre île entre les deux îles données on ne fait rien
        if(objet != null && objet.getClass() == Ile.class){
            return;
        }
        
        //si pont traitement 
        if(objet != null && (objet.getClass() == Pont.class)){
            // si le pont existant est simple
            pont = (Pont)objet;
            if(pont.getNombrePont() == 1){
                pont.ajoutNombrePont();
                // sinon on le supprime
            }else{
                retirerPont(pont);
                return;
            }
            
            
        }else{
            //sinon le créer
            pont = new Pont(ile1, ile2, 1);
            System.out.println("Création d'un pont");
        }

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
        this.pileSvg.push((Pont)pont);
    }

    /**
     * Vérifie si la création d'un pont est possible
     * @param ile1 l'île de départ du pont
     * @param ile2 l'île d'arrivée du pont
     * @return true si le pont peut être créé, false sinon
     */
    public Object verifCreationPont(Ile ile1, Ile ile2){
        int i;
        // si pont vertical
        if(ile1.getAbs() == ile2.getAbs()){
            if(ile1.getOrd() < ile2.getOrd()){
                for(i = ile1.getOrd() + 1; i < ile2.getOrd() - 1; i++)
                    if(matriceGrille[ile1.getAbs()][i].getClass() != Object.class) return matriceGrille[ile1.getAbs()][i];
                    
            }else{
                for(i = ile2.getOrd() + 1; i < ile1.getOrd() - 1; i++)
                    if(matriceGrille[ile1.getAbs()][i].getClass() != Object.class) return matriceGrille[ile1.getAbs()][i]; 
            }
        } else if(ile1.getOrd() == ile2.getOrd()){
            if(ile1.getAbs() < ile2.getAbs()){
                for(i = ile1.getAbs() + 1; i < ile2.getAbs() - 1; i++){
                    if(matriceGrille[ile1.getOrd()][i].getClass() != Object.class) return matriceGrille[ile1.getOrd()][i]; 
                }
            }else{
                for(i = ile2.getAbs() + 1; i < ile1.getAbs() - 1; i++)
                if(matriceGrille[ile1.getOrd()][i].getClass() != Object.class) return matriceGrille[ile1.getOrd()][i]; 
            }
        }
        System.out.println("pont possible à créé");
        return null;
    }
    
    /**
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
                for(i = ile1.getAbs() + 1; i < ile2.getAbs() - 1; i++){
                    matriceGrille[i][ile2.getOrd()] = -1;
                }
            }else{
                for(i = ile2.getAbs() + 1; i < ile1.getAbs() - 1; i++)
                    matriceGrille[i][ile2.getOrd()] = -1; 
            }
        }
        this.pileSvg.push(pont);
    }

    /**
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
                else if(matriceGrille[i][j].getClass() == Pont.class){
                    if(((Pont) matriceGrille[i][j]).getNombrePont() == 1)
                        s += "─ ";
                    else 
                        s += "═ ";
                }
                else
                    s += ". ";
            }
            s += "\n";
        }
        return s;
    }

    /**
     * Retourne la pile des ponts sauvegardés
     * @return la pile des ponts sauvegardés
     */
    public int getTaille(){
        return 10;
    }

    /**
     * Retourne la matrice de la grille
     * @return la matrice de la grille
     */
    public Object[][] getMatriceGrille(){
        return matriceGrille;
    }
    

    public static void main(String[] args){

        int[][] init = {
        //    0   1  2   3   4   5   6   7  8   9
            { 2, -1,-1, -1, -1,  2, -1, -1, -1,  2}, // 0
            {-1,-1, -1, -1, -1, -1, -1, -1, -1, -1}, // 1
            {-1,-1, -1, -1, -1, -1, -1, -1, -1, -1}, // 2
            {-1,-1, -1, -1, -1, -1, -1, -1, -1, -1}, // 3
            { 2,-1, -1, -1, -1, -1, -1, -1, -1,  2}, // 4
            {-1,-1, -1, -1, -1, -1, -1, -1, -1, -1}, // 5
            {-1,-1, -1, -1, -1, -1, -1, -1, -1, -1}, // 6
            {-1,-1, -1, -1, -1, -1, -1, -1, -1, -1}, // 7
            {-1,-1, -1, -1, -1, -1, -1, -1, -1, -1}, // 8 
            { 2,-1, -1, -1, -1,  2, -1, -1, -1,  2}  // 9

        };
        Grille grilleTest = new Grille(init);
        Color c = new Color(0, 0, 255);
        
        try {
            Ile ile1 = new Ile(1,2,4,0,c);
            Ile ile2 = new Ile(2,2,4,10,c);
            Ile ile3 = new Ile(3,2,0,5,c);
            Ile ile4 = new Ile(4,2,10,5,c);
            grilleTest.ajouterPont(ile3,ile4);
            grilleTest.ajouterPont(ile3,ile4);
            grilleTest.ajouterPont(ile3,ile4);
            grilleTest.ajouterPont(ile1,ile2);
            //grilleTest.ajouterPont(ile1,ile2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(grilleTest.toString());
        
    }
}

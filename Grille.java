import java.util.*;
import java.awt.Color;

public class Grille {

    Color c = new Color(0, 0, 255);

    Stack<Pont> pileSvg;
    Stack<Pont> pileRecup;
    private Element[][] matriceGrille;
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
            matriceGrille = new Element[10][10]; 
            int i, j;
            /** initialisation de la grille en dur TEMPORAIRE*/
            for(i = 0; i < 10; i++){
                for(j = 0; j < 10; j++){
                    matriceGrille[i][j] = new Element();
                }
            }   
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode d'ajout d'une nouvelle ile
     * @param num le numéro de l'île
     * @param abs l'abscisse de l'île
     * @param ord l'ordonnée de l'île
     */
    void ajouterIle(Ile ile){
        int abs = ile.getAbs();
        int ord = ile.getOrd();
        int num = ile.getNum();
        if(matriceGrille[abs][ord] instanceof Ile){
            //Cas ou on veut ajouter une ile sur une ile donc fichier corrompu
            return;
        }
        try {
            Ile ileTemp = new Ile(1, num, abs, ord, c);
            //Cas ou on veut ajouter une ile sur une intersection donc on crée 4 nouveaux ponts
            if(matriceGrille[abs][ord] instanceof Intersection){
                Pont temp = ((Intersection) matriceGrille[abs][ord]).getPont1();
                new Pont(temp.getIle1(),ileTemp,0);
                new Pont(ileTemp,temp.getIle2(),0);
                temp = ((Intersection) matriceGrille[abs][ord]).getPont2();
                new Pont(temp.getIle1(),ileTemp,0);
                new Pont(ileTemp,temp.getIle2(),0);
            }
            //Cas ou on veut ajouter une ile sur un pont déja existant
            else if(matriceGrille[abs][ord] instanceof Pont){
                Pont temp = (Pont) matriceGrille[abs][ord];
                new Pont(temp.getIle1(),ileTemp,0);
                new Pont(ileTemp,temp.getIle2(),0);
            }
            //Cas ou on créer un pont dans le vide
            else{
                matriceGrille[abs][ord] = ileTemp;
                int i;
                //On vérifie si il y a des îles à proximité et qu'on peut créer des ponts vides
                for(i=abs+1;i<10;i++){
                    if(matriceGrille[i][ord] instanceof Ile){
                        //new Pont((Ile)matriceGrille[i][ord],(Ile)matriceGrille[abs][ord],0);
                        System.out.println("pont vide");
                        ajouterPont(ile, (Ile) matriceGrille[i][ord], 0);
                    }
                }
                for(i=abs-1;i>0;i--){
                    if(matriceGrille[i][ord] instanceof Ile){
                        System.out.println("pont vide");
                        ajouterPont(ile, (Ile) matriceGrille[i][ord], 0);
                    }
                }
                for(i=ord+1;i<10;i++){
                    if(matriceGrille[abs][i] instanceof Ile){
                        System.out.println("pont vide");
                        ajouterPont(ile,(Ile) matriceGrille[abs][i], 0);
                    }
                }
                for(i=ord-1;i>0;i--){
                    if(matriceGrille[abs][i] instanceof Ile){
                        System.out.println("pont vide");
                        ajouterPont(ile,(Ile) matriceGrille[abs][i], 0);
                    }
                }
            }
        } catch (Exception e) {
            // Impossible de créer l'île
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
    public void ajouterPont(Ile ile1, Ile ile2,int nbPonts){
        int i;
            
        Pont pont;

        Element elem = verifCreationPont(ile1, ile2);
        
        //Si il y'a une autre île ou un pont entre les deux îles données on ne fait rien
        if(elem != null || elem instanceof Ile){
            return;
        }
        
        //si pont traitement 
        if(elem != null && (elem instanceof Pont)){
            pont = ((Pont)elem);
            pont.ajoutNombrePont();
            
        }else{
            //sinon le créer
            pont = new Pont(ile1, ile2, nbPonts);
            System.out.println("Création d'un pont");
        }
        //Si on peut on vérifie si le pont est horizontal ou vertical
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

    

    /**
     * Vérifie si la création d'un pont est possible
     * @param ile1 l'île de départ du pont
     * @param ile2 l'île d'arrivée du pont
     * @return Element si trouvé sur le chemin ( possiblement Ile, Pont, Intersection) sinon null
     */
    public Element verifCreationPont(Ile ile1, Ile ile2){
        int i;
        // si pont vertical
        if(ile1.getAbs() == ile2.getAbs()){
            if(ile1.getOrd() < ile2.getOrd()){ // si ile1 est en haut
                for(i = ile1.getOrd() + 1; i < ile2.getOrd() - 1; i++){
                    if(matriceGrille[ile1.getAbs()][i] != null)
                        if(matriceGrille[ile1.getAbs()][i] instanceof Intersection  ){
                        }else{
                            
                        }
                        return matriceGrille[ile1.getAbs()][i];
                }
                    
            }else{ // si ile2 est en haut
                for(i = ile2.getOrd() + 1; i < ile1.getOrd() - 1; i++){
                    if(matriceGrille[ile1.getAbs()][i] != null) 
                        return matriceGrille[ile1.getAbs()][i]; 
                }
            }
        // si horizontal
        } else if(ile1.getOrd() == ile2.getOrd()){
            if(ile1.getAbs() < ile2.getAbs()){ //  si ile1 est à gauche
                for(i = ile1.getAbs() + 1; i < ile2.getAbs() - 1; i++){
                    if(matriceGrille[ile1.getOrd()][i] != null) 
                        return matriceGrille[ile1.getOrd()][i]; 
                }

            }else{ // si ile2 est à gauche
                for(i = ile2.getAbs() + 1; i < ile1.getAbs() - 1; i++){
                    if(matriceGrille[ile1.getOrd()][i] != null) 
                        return matriceGrille[ile1.getOrd()][i]; 
                }
            }
        } // traitement si null
        System.out.println("pont possible à créer");
        return null;
    }
    
    /**
     * Retire un pont de la grille
     * @param pont le pont à retirer
     * @return void
     * 
     */
    public void retirerPont(Pont pont){
        pont.setNombrePont(0);
        this.pileSvg.push(pont);
        /* 
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
        */
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
                else if(matriceGrille[i][j] instanceof Pont){
                    if(((Pont) matriceGrille[i][j]).getNombrePont() == 0){
                        System.out.println("pont vide trouvé");
                        s += "│ ";
                    }                
                    else if(((Pont) matriceGrille[i][j]).getNombrePont() == 1)
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
            Ile ile2 = new Ile(2,2,4,9,c);
            Ile ile3 = new Ile(3,2,0,5,c);
            Ile ile4 = new Ile(4,2,9,5,c);
            grilleTest.ajouterIle(ile1);
            grilleTest.ajouterIle(ile2);
            grilleTest.ajouterIle(ile3);
            grilleTest.ajouterIle(ile4);
            grilleTest.ajouterPont(ile3,ile4,1);
            //grilleTest.ajouterPont(ile3,ile4);
            //grilleTest.ajouterPont(ile3,ile4);
            grilleTest.ajouterPont(ile1,ile2,1);
            //grilleTest.ajouterPont(ile1,ile2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(grilleTest.toString());
        
    }
}

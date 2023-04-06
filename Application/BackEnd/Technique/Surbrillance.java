import java.awt.Color;
import java.util.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public abstract class Surbrillance {

    Color c = new Color(255,200,200); /* Couleur de la surbrillance */
    Color couleurNormale = new Color(255,255,255); /* Couleur normale */
    Grille grille; /* Grille du jeu */
    Ile ile; /* Ile source de la surbrillance */

    /** 
     * Constructeur de Surbrillance
     */
    public Surbrillance(Grille grille, Ile i){
    	this.ile = i;
        this.grille = grille;
    }

    /** 
     * Active la surbrillance
     * @return void
     */ 
    public abstract void activer();
    
    /** 
     * Desactive la surbrillance
     * @return void
     */ 
    public abstract void desactiver();
    
}
